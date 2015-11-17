
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasonclawson.jackson.dataformat.hocon.HoconTreeTraversingParser;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.configuration.ConfigurationValidationException;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * Extend ConfigurationFactory to speak Hocon instead of Yaml.
 */
public class HoconConfigurationFactory<T> extends ConfigurationFactory<T> {
    // everything in super class is private! thanks.
    protected final Class<T> klass;
    protected final String propertyPrefix;
    protected final ObjectMapper mapper;
    protected final Validator validator;

    /**
     * Creates a new configuration factory for the given class.
     *
     * @param klass          the configuration class
     * @param validator      the validator to use
     * @param objectMapper   the Jackson {@link ObjectMapper} to use
     * @param propertyPrefix the system property name prefix used by overrides
     */
    public HoconConfigurationFactory(Class<T> klass,
                                     Validator validator,
                                     ObjectMapper objectMapper,
                                     String propertyPrefix) {
        super(klass, validator, objectMapper, propertyPrefix);
        this.klass = klass;
        this.propertyPrefix = propertyPrefix;
        this.mapper = objectMapper.copy();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.validator = validator;
    }

    /**
     * Loads, parses, binds, and validates a configuration object.
     *
     * @param provider the provider to to use for reading configuration files
     * @param path     the path of the configuration file
     * @return a validated configuration object
     * @throws IOException            if there is an error reading the file
     * @throws ConfigurationException if there is an error parsing or validating the file
     */
    @Override
    public T build(ConfigurationSourceProvider provider, String path) throws IOException, ConfigurationException {
        throw new UnsupportedOperationException(
                "HoconConfigurationFactory does not support loading config from a command line path");
    }

    /**
     * Loads, parses, binds, and validates a configuration object from a file.
     *
     * @param file the path of the configuration file
     * @return a validated configuration object
     * @throws IOException            if there is an error reading the file
     * @throws ConfigurationException if there is an error parsing or validating the file
     */
    @Override
    public T build(File file) throws IOException, ConfigurationException {
        throw new UnsupportedOperationException(
                "HoconConfigurationFactory does not support loading config from a file");
    }

    /**
     * Loads, parses, binds, and validates a configuration object from an empty document.
     *
     * @return a validated configuration object
     * @throws IOException            if there is an error reading the file
     * @throws ConfigurationException if there is an error parsing or validating the file
     */
    @Override
    public T build() throws IOException, ConfigurationException {
        Config conf = ConfigFactory.load();
        final T config = mapper.readValue(new HoconTreeTraversingParser(conf.root()), klass);
        final Set<ConstraintViolation<T>> violations = validator.validate(config);
        if (!violations.isEmpty()) {
            throw new ConfigurationValidationException("", violations);
        }
        return config;
    }
}
