import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Java8Application extends Application<Configuration> {
    static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        //bootstrap.setObjectMapper(mapper);
        bootstrap.addBundle(new Java8Bundle());
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        if (false) {
            JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
            provider.setMapper(mapper);
            environment.jersey().register(provider);
        }
        environment.jersey().register(Java8Resource.class);
    }
}
