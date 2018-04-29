import com.fasterxml.jackson.databind.ObjectMapper;
import event.EventService;

import static spark.Spark.port;

public class Main {
    private static EventService userService = new EventService();
    private static ObjectMapper om = new ObjectMapper();


    public static void main(String [] args) {
        EventService e= new EventService();
        e.loadMongoEvents();

        // Start embedded server at this port
        port(8080);

        // Main Page, welcome
        //get("/", (request, response) -> "Welcome"); // this is not working ?


    }
}
