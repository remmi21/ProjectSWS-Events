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

        /***************************************************** Events *****************************************************/

        // POST - add event
        post("/events/new", (request, response) -> { // TODO: reject if given event id is already in db
            String id = request.queryParams("id");
            String name =request.queryParams("name");
            String description = request.queryParams("description");
            String status = request.queryParams("status");
            String limit = request.queryParams("limit");
            String tickets_left = request.queryParams("tickets_left");
            List<Venue> venueList = new ArrayList<>(); // empty venue list is created here, venues are then added by calling /events/venue/add
            DateEv date = new DateEv(); // empty event date object is created here, dates are then added by calling /events/date/add
            List<Category> categories = new ArrayList<>(); // categories then added by calling /events/categories/add
            List<Price> price = new ArrayList<>(); // price is then added by calling /events/pricing/add
            Properties prop = new Properties(); // properties are then added by calling /events/properties/add

            Event event = EventService.add(Integer.parseInt(id), name, description, status, Integer.parseInt(limit), Integer.parseInt(tickets_left), venueList, date, categories, price, prop);

            if(event != null) {
                response.status(201); // 201 created
                return om.writeValueAsString(event);
            } else {
                response.status(405); // method not allowed
                return om.writeValueAsString("an event with id "+id+" is already existing");
            }
        });

        // DELETE - delete event
        delete("/events/remove/:id", (request, response) -> { // TODO: ERROR 404 not found
           String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            if(event != null) {
                eventService.delete(id);
                return om.writeValueAsString("event with id " + id + " was deleted successfully");
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - get event list
        get("/events/list", (requet, response) -> {
            List allEvents = eventService.findAll();
            if(allEvents.isEmpty()) {
                response.status(404);
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

        /***************************************************** Venues *****************************************************/

        // PUT - add venue to event
        put("/events/venue/add/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String name = request.queryParams("name");
                String address = request.queryParams("address");
                String city = request.queryParams("city");
                String state = request.queryParams("state");
                String country = request.queryParams("country");
                String zipcode = request.queryParams("zipcode");
                Location loc= new Location(address,city,state,country, zipcode);
                Venue venue = VenueService.add(Integer.parseInt(id), name,loc); // TODO: venue id is set to event id here

                return om.writeValueAsString("added venue " + venue + " to event with id " + id);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
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

        /***************************************************** Dates *****************************************************/

        // PUT - add date to event
        put("/events/date/add/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String eventStart = request.queryParams("eventStart");
                String eventEnd = request.queryParams("eventEnd");
                String registrationStart = request.queryParams("registrationStart");
                String registrationEnd = request.queryParams("registrationEnd");

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date resultingEventStartDate = df.parse(eventStart);
                Date resultingEventEndDate = df.parse(eventEnd);
                Date resultingRegistrationStartDate = df.parse(registrationStart);
                Date resultingRegistrationEndDate = df.parse(registrationEnd);

                DateEvService.addDate(Integer.parseInt(id), resultingEventStartDate, resultingEventEndDate, resultingRegistrationStartDate, resultingRegistrationEndDate);

                return om.writeValueAsString("dates successfully added to event with id " + id);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - search event by date
        get("/events/date/search/:start", (request, response) -> {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date resultingEventStartDate = df.parse(request.params(":start"));
            ArrayList event = DateEvService.searchEventsByDate(resultingEventStartDate);
            if (event != null) {
                return om.writeValueAsString(event);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("event not found");
            }
        });

        // TODO: test from here on
        /***************************************************** Properties *****************************************************/
        //modify the properties
        put("/events/properties/add/:id",(request, response)-> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            PropertiesService prop = new PropertiesService();
            if (event != null) {
                String groups_allowed = request.queryParams("group_registrations_allowed");
                String groupsize = request.queryParams("group_size");
                String active = request.queryParams("active");
                String member = request.queryParams("members_only");

                prop.add(event.getId(), Boolean.parseBoolean(groups_allowed), Integer.parseInt(groupsize),
                        Boolean.parseBoolean(active), Boolean.parseBoolean(member));
                return om.writeValueAsString("properties successfully added to event with id " + id);
            }else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });
        //remove the properties
        get("/events/properties/remove/:id",(request, response)-> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            PropertiesService prop = new PropertiesService();
            if (event != null) {
                prop.remove(event.getId());
                return om.writeValueAsString("properties successfully removed from to event with id " + id);
            }else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        //search all events with properties
        get("/events/properties/search",(request, response)-> {
            PropertiesService prop = new PropertiesService();
            String groups_allowed = request.queryParams("group_registrations_allowed");
            String groupsize = request.queryParams("group_size");
            String active = request.queryParams("active");
            String member = request.queryParams("members_only");

            List allEvents = prop.searchEventbyProp(Boolean.parseBoolean(groups_allowed), Integer.parseInt(groupsize),
                        Boolean.parseBoolean(active), Boolean.parseBoolean(member));
            if(allEvents.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                return om.writeValueAsString(allEvents);
            }
        });

        /***************************************************** Categories *****************************************************/
        put("/events/category/add",(request, response)-> {
            CategoryService cat = new CategoryService();
            String id = request.queryParams("id");
            String name = request.queryParams("name");
            cat.add(Integer.parseInt(id),name);

            return om.writeValueAsString("category successfully added");
        });

        put("/events/category/addto/:id",(request, response)-> {
            String eid = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eid));
            CategoryService cat = new CategoryService();
            String id = request.queryParams("id");
            String name = request.queryParams("name");
            cat.addTo(Integer.parseInt(eid),Integer.parseInt(id),name);

            return om.writeValueAsString("category successfully added to event with id "+id);
        });

        get("/events/category/find/:id",(request, response)-> {
            String id = request.params(":id");
            CategoryService cat=new CategoryService();
            Category cate=cat.findById(Integer.parseInt(id));
            if (cate != null) {
                return om.writeValueAsString(cate);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("category not found");
            }
        });

        get("/events/category/remove/:id",(request, response)-> {
            String id = request.params(":id");
            CategoryService cat=new CategoryService();
            Category cate=cat.remove(Integer.parseInt(id));
            if (cate != null) {
                return om.writeValueAsString("category was successfully removed");
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("category not found");
            }
        });
        // GET - get category list
        get("/events/categories/list", (requet, response) -> {
            CategoryService cate =new CategoryService();
            List allCats = cate.findAll();
            if(allCats.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                return om.writeValueAsString(allCats);
            }
        });

        /***************************************************** Locations *****************************************************/
        put("/events/location/add",(request, response)-> {
            LocationService locS = new LocationService();
            String address = request.queryParams("address");
            String city = request.queryParams("city");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String zipcode = request.queryParams("zipcode");
            locS.add(address,city,state,country,zipcode);

            return om.writeValueAsString("location successfully added");
        });

        put("/events/location/addto/:id",(request, response)-> {
            String eid = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eid));
            LocationService locS = new LocationService();
            String address = request.queryParams("address");
            String city = request.queryParams("city");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String zipcode = request.queryParams("zipcode");
            locS.addTo(Integer.parseInt(eid),address,city,state,country,zipcode);

            return om.writeValueAsString("location successfully added to event with id " + eid);
        });

        get("/events/location/find/:zip",(request, response)-> {
            String zip = request.params(":zip");
            LocationService locS=new LocationService();
            ArrayList<Location> loc=locS.findByZip(zip);
            if (loc.size()>0) {
                return om.writeValueAsString(loc);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("location with zip "+ zip +" not found");
            }
        });

        get("/events/location/remove/:id",(request, response)-> {
            String id = request.params(":id");
            LocationService locS=new LocationService();
            Location loc=locS.remove(Integer.parseInt(id));
            if (loc != null) {
                return om.writeValueAsString("location was successfully removed");
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("location not found");
            }
        });
        // GET - get category list
        get("/events/location/list", (requet, response) -> {
            LocationService locS =new LocationService();
            List allLocs = locS.findAll();
            if(allLocs.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no locations found");
            } else {
                return om.writeValueAsString(allLocs);
            }
        });
        /** Favorites */
        get("/events/fav/list", (requet, response) -> {
            FavoritesService fav =new FavoritesService();
            List allFav = fav.getFavorites();
            if(allFav.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                return om.writeValueAsString(allFav);
            }
        });

        /***************************************************** Price *****************************************************/

        // PUT - add price to event
        put("/events/pricing/add/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String name = request.queryParams("name");
                String priceValue = request.queryParams("priceValue");

                PriceService.addPrice(Integer.parseInt(id), name, Integer.parseInt(priceValue));

                return om.writeValueAsString("price successfully added to event with id " + id);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - get price of event
        get("/events/pricing/get/:id", (request, response) -> {
            String id = request.params(":id");

            List<Price> eventPrices = PriceService.findEventPrice(Integer.parseInt(id));

            if(!eventPrices.isEmpty()) {
                return om.writeValueAsString(eventPrices);
            } else {
                response.status(404);
                return om.writeValueAsString("no prices found for the given event");
            }
        });

        /***************************************************** Ticket *****************************************************/

        // POST - book ticket for event
        post("/events/tickets/book", (request, response) -> {
            String eventId = request.queryParams("eventId");
            String amount =request.queryParams("amount");
            String userId = request.queryParams("userId");

            Ticket ticket = TicketService.bookTicket(Integer.valueOf(eventId), Integer.valueOf(amount), Integer.valueOf(userId));

            response.status(201); // 201 created

            return om.writeValueAsString(ticket);
        });

        /***************************************************** Order *****************************************************/

        // GET - get order list of users
        get("/events/orders/list/:id", (request, response) -> {
            String userId = request.params(":id");

            List<Order> orders = OrderService.getOrders(Integer.valueOf(userId));

            if(orders != null) {
                if (!orders.isEmpty()) {
                    return om.writeValueAsString(orders);
                } else {
                    response.status(404);
                    return om.writeValueAsString("no orders found for the given user");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("user has not ordered any ticket yet");
            }
        });
    }
}
