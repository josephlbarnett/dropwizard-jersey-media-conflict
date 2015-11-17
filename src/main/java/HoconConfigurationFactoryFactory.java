import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationFactoryFactory;

import javax.validation.Validator;

/**
 * Use a Hocon ConfigurationFactory instead of the default Yaml one.
 */
public class HoconConfigurationFactoryFactory<T> implements ConfigurationFactoryFactory<T> {

    @Override
    public ConfigurationFactory<T> create(Class<T> klass,
                                          Validator validator,
                                          ObjectMapper objectMapper,
                                          String propertyPrefix) {
        return new HoconConfigurationFactory<>(klass, validator, objectMapper, propertyPrefix);
    }
}
