import com.fasterxml.jackson.databind.ObjectMapper;
import event.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

public class Server {
    private static EventService eventService = new EventService();
    private static ObjectMapper om = new ObjectMapper();


    public static void main(String [] args) {
        EventService e= new EventService();
        e.loadMongoEvents();

        // Start embedded server at this port
        port(8080);

        // Main Page, welcome
        get("/", (request, response) -> "Welcome");

        /** Events */

        // POST - add event
        post("/events/new", (request, response) -> {
            String id = request.queryParams("id");
            String name =request.queryParams("name");
            String description = request.queryParams("description");
            String status = request.queryParams("status");
            String limit = request.queryParams("limit");
            String tickets_left = request.queryParams("tickets_left");
            List<Venue> venueList = new ArrayList<>(); // empty venue list is created here, venues are then added by calling /events/venue/add
            DateEv date = new DateEv(); // empty event date object is created here, dates are then added by calling /events/date/add
            List<Category> categories = new ArrayList<>(); // categories then added by calling /events/categories/add
            List<Pricing> price = new ArrayList<>(); // price is then added by calling /events/pricing/add
            Properties prop = new Properties(); // properties are then added by calling /events/properties/add

            Event event = EventService.add(Integer.parseInt(id), name, description, status, Integer.parseInt(limit), Integer.parseInt(tickets_left), venueList, date, categories, price, prop);

            response.status(201); // 201 created

            return om.writeValueAsString(event);
        });

        // DELETE - delete event
        delete("/events/remove/:id", (request, response) -> {
           String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            if(event != null) {
                eventService.delete(id);
                return om.writeValueAsString("event with id " + id + "was deleted successfully");
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - get event list
        get("/events/list", (requet, response) -> {
            List allEvents = eventService.findAll();
            if(allEvents.isEmpty()) {
                return om.writeValueAsString("no events found");
            } else {
                return om.writeValueAsString(allEvents);
            }
        });

        // GET - find event by id
        get("/events/search/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            if(event != null) {
                return om.writeValueAsString(event);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        /** Venues */

        // POST - add venue
        post("/events/venue/add", (request, response) -> {
            String id = request.queryParams("id");
            String name = request.queryParams("name");
            String address = request.queryParams("address");
            String city = request.queryParams("city");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String zipcode = request.queryParams("zipcode");

            Venue venue = VenueService.add(Integer.parseInt(id), name, address, city, state, country, zipcode);

            response.status(201); // 201 created

            return om.writeValueAsString(venue);
        });

        // GET - venue by id
        get("/events/venue/search/:id", (request, response) -> {
            Venue venue = VenueService.findById(Integer.valueOf(request.params(":id")));
            if (venue != null) {
                return om.writeValueAsString(venue);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("venue not found");
            }
        });

        /** Dates */

        // POST - add date to event
        post("/events/date/add", (request, response) -> {
            String id = request.queryParams("id");
            String eventStart = request.queryParams("eventStart");
            String eventEnd = request.queryParams("eventEnd");
            String registrationStart = request.queryParams("registrationStart");
            String registrationEnd = request.queryParams("registationEnd");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date resultingEventStartDate = df.parse(eventStart);
            Date resultingEventEndDate = df.parse(eventEnd);
            Date resultingRegistrationStartDate = df.parse(registrationStart);
            Date resultingRegistrationEndDate = df.parse(registrationEnd);

            Event event = DateEvService.addDate(Integer.parseInt(id), resultingEventStartDate, resultingEventEndDate, resultingRegistrationStartDate, resultingRegistrationEndDate);

            response.status(201); // 201 created

            return om.writeValueAsString(event);
        });

        // GET - search event by date
        get("/events/venue/search/:start", (request, response) -> {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date resultingEventStartDate = df.parse(request.params(":start"));
            Event event = DateEvService.searchEventsByDate(resultingEventStartDate);
            if (event != null) {
                return om.writeValueAsString(event);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("event not found");
            }
        });
    }
}
