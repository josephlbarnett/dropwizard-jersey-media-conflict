import java.util.Optional;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Singleton
public class Java8Resource {

    public static class OptionalThing {
        private String stuff;

        public Optional<String> getThing() {
            return thing;
        }

        public String getStuff() {
            return stuff;
        }

        public Optional<String> getNothing() {
            return nothing;
        }

        private Optional<String> thing;
        private Optional<String> nothing;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public OptionalThing getThing() {
        OptionalThing ot = new OptionalThing();
        ot.stuff="stuff";
        ot.thing=Optional.of("thing");
        ot.nothing=Optional.empty();
        return ot;
    }

}
