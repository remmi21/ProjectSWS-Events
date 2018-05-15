import com.fasterxml.jackson.databind.ObjectMapper;
import event.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

// TODO: nullpointer exception handling when parsing ints :
/*Integer parsed_int = 0;
try {
        if(string_to_parse != null) {
        parsed_int = Integer.parseInt(string_to_prase);
        }
        } catch (NumberFormatException formatException) {
        parsed_int = 0;
        }
*/
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
            List<Price> price = new ArrayList<>(); // price is then added by calling /events/pricing/add
            Properties prop = new Properties(); // properties are then added by calling /events/properties/add

            Event checkEvent = EventService.findById(Integer.parseInt(id));
            if(checkEvent != null) {
                response.status(403); // forbidden
                return om.writeValueAsString("event with id "+ id +" already exists");
            }

            Event event = EventService.add(Integer.parseInt(id), name, description, status, Integer.parseInt(limit), Integer.parseInt(tickets_left), venueList, date, categories, price, prop);

            if(event != null) {
                response.status(201); // 201 created
                return om.writeValueAsString(event);
            } else {
                response.status(500); // internal server error
                return om.writeValueAsString("event could not be created");
            }
        });

        // DELETE - delete event
        delete("/events/remove/:id", (request, response) -> { // TODO: ERROR says event was successfully deleted but is still in events/list and can be found when searching after
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
        // TODO: check where location add function takes venue from -> venue is in event list but is not found when adding location (404)

        // PUT - add venue to event
        put("/events/venue/add/:id", (request, response) -> { // TODO: add location to overall location list
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String venueId = request.queryParams("venue_id");
                String name = request.queryParams("name");
                String address = request.queryParams("address");
                String city = request.queryParams("city");
                String state = request.queryParams("state");
                String country = request.queryParams("country");
                String zipcode = request.queryParams("zipcode");
                Location loc= new Location(address,city,state,country, zipcode);

                Venue venue = VenueService.add(Integer.parseInt(id), Integer.parseInt(venueId), name,loc);

                if(venue != null) {
                    return om.writeValueAsString("added venue " + venue.getName() + "(" + venue.getId() +") to event with id " + id);
                } else {
                    response.status(500);
                    return om.writeValueAsString("venue could not be added");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - find venue by id
        get("/events/venue/search/:id", (request, response) -> {
            Venue venue = VenueService.findById(Integer.valueOf(request.params(":id")));
            if (venue != null) {
                return om.writeValueAsString(venue);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("venue not found");
            }
        });

        // GET - get venue list
        get("/events/venue/list", (request, response) -> {
            VenueService venueService = new VenueService();

            List allVenues = venueService.findAll();

            if(allVenues.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no venues found");
            } else {
                return om.writeValueAsString(allVenues);
            }
        });

        // DELETE - delete venue from event
        delete("/events/venue/remove/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String venueId = request.queryParams("venue_id");

                VenueService venueService = new VenueService();

            // TODO: java.lang.NumberFormatException: null here (should be solved when proper exception handling is implemented)
                venueService.remove(Integer.parseInt(id), Integer.parseInt(venueId));

                return om.writeValueAsString("venue was successfully removed from event with id " + id);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        /***************************************************** Dates *****************************************************/
        // PUT - add date to event
        put("/events/date/add/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String eventStart = request.queryParams("event_start");
                String eventEnd = request.queryParams("event_end");
                String registrationStart = request.queryParams("registration_start");
                String registrationEnd = request.queryParams("registration_end");

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

        // DELETE - delete date from event
        delete("/events/date/delete/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            DateEvService dateEvService = new DateEvService();

            if (event != null) {
                dateEvService.removeDate(Integer.parseInt(id));
                return om.writeValueAsString("date successfully removed from to event with id " + id);
            }else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        /***************************************************** Properties *****************************************************/
        //PUT - modify the properties
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

        // DELETE - remove the properties (resets properties to false)
        delete("/events/properties/remove/:id",(request, response)-> {
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

        // GET - search all events with the given properties
        get("/events/properties/search",(request, response)-> { // TODO: doesn't work properly -> not only events with the searched properties are found
            PropertiesService prop = new PropertiesService();
            String groups_allowed = request.queryParams("group_registrations_allowed");
            String groupsize = request.queryParams("group_size");
            String active = request.queryParams("active");
            String member = request.queryParams("members_only");

            Integer parsed_groupsize = 0;
            try {
                if(groupsize != null) {
                    parsed_groupsize = Integer.parseInt(groupsize);
                }
            } catch (NumberFormatException formatException) {
                parsed_groupsize = 0;
            }

            List allEvents = prop.searchEventbyProp(Boolean.parseBoolean(groups_allowed), parsed_groupsize,
                    Boolean.parseBoolean(active), Boolean.parseBoolean(member));

            if(allEvents.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                return om.writeValueAsString(allEvents);
            }
        });

        /***************************************************** Categories *****************************************************/
        // PUT - add category
        put("/events/category/add",(request, response)-> { // TODO: why do we need this ?
            CategoryService cat = new CategoryService();
            String catId = request.queryParams("cat_id");
            String name = request.queryParams("name");

            cat.add(Integer.parseInt(catId),name);

            if(cat.findById(Integer.parseInt(catId)) != null) { // check if category was successfully added to list
                response.status(201);
                return om.writeValueAsString("category successfully created");
            } else {
                response.status(403);
                return om.writeValueAsString("category creation failed");
            }
        });

        // PUT - add category to event
        put("/events/category/addto/:id",(request, response)-> {
            String eid = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eid));

            CategoryService cat = new CategoryService();
            String catId = request.queryParams("cat_id");
            String name = request.queryParams("name");

            cat.addTo(Integer.parseInt(eid),Integer.parseInt(catId),name);

            if(event != null) {
                return om.writeValueAsString("category successfully added to event with id " + eid);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - find category by id
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

        // DELETE - remove category
        delete("/events/category/remove/:id",(request, response)-> {
            String eventId = request.params(":id");
            EventService eventService = new EventService();
            Event event = eventService.findById(Integer.parseInt(eventId));

            if(event != null) {
                CategoryService categoryService = new CategoryService();
                String catId = request.queryParams("cat_id"); // TODO: nullpointer exception (should be solved after adding exceptin handling)

                event = categoryService.remove(Integer.parseInt(eventId), Integer.parseInt(catId));

                if (event != null) {
                    return om.writeValueAsString("category was successfully removed");
                } else {
                    response.status(404); // 404 not found
                    return om.writeValueAsString("category not found");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - get category list
        get("/events/categories/list", (request, response) -> { // TODO: when adding category to event, add category to overall catgory list
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
        // PUT - add location
        put("/events/location/add",(request, response)-> { // TODO: why do we need this ?
            LocationService locS = new LocationService();
            String address = request.queryParams("address");
            String city = request.queryParams("city");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String zipcode = request.queryParams("zipcode");

            locS.add(address,city,state,country,zipcode);

            if(locS.findByZip(zipcode) != null) {
                response.status(201);
                return om.writeValueAsString("location successfully created");
            } else {
                response.status(403);
                return om.writeValueAsString("location creation failed");
            }
        });

        // PUT - add location to event
        put("/events/location/addto/:id",(request, response)-> {
            String eid = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eid));

            if(event != null) {
                LocationService locS = new LocationService();
                String venueId = request.queryParams("venue_id");
                String address = request.queryParams("address");
                String city = request.queryParams("city");
                String state = request.queryParams("state");
                String country = request.queryParams("country");
                String zipcode = request.queryParams("zipcode");

                Location location = locS.addTo(Integer.parseInt(venueId), address, city, state, country, zipcode);

                if(location != null) {
                    return om.writeValueAsString("location successfully added to venue with id " + venueId + " in event with id " + eid);
                } else {
                    response.status(404);
                    return om.writeValueAsString("venue not found");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - find event in given location
        get("/events/location/find/:zip",(request, response)-> { // TODO: no locations found although valid zipcode was used
            String zip = request.params(":zip");
            LocationService locationService = new LocationService();
            ArrayList<Event> events = locationService.findByZip(zip);
            if (events.size()>0) {
                return om.writeValueAsString(events);
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("no events found for location with zip "+ zip);
            }
        });

        // DELETE - remove location
        delete("/events/location/remove/:id",(request, response)-> {
            String venueId = request.params(":id");
            VenueService venueService = new VenueService();
            Venue venue = venueService.findById(Integer.parseInt(venueId));

            if(venue != null) {
                String lid = request.queryParams("loc_id");
                String zipCode = request.queryParams("zipcode");
                LocationService locationService = new LocationService();
                Venue venueDelLoc = locationService.remove(Integer.parseInt(venueId), Integer.parseInt(lid), zipCode);
                if (venueDelLoc != null) {
                    return om.writeValueAsString("location was successfully removed from venue "+venueDelLoc);
                } else {
                    response.status(404); // 404 not found
                    return om.writeValueAsString("location not found");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("venue not found");
            }
        });

        // GET - get location list
        get("/events/location/list", (request, response) -> {
            LocationService locS =new LocationService();
            List allLocs = locS.findAll();
            if(allLocs.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no locations found");
            } else {
                return om.writeValueAsString(allLocs);
            }
        });

        /*****************************************************  Favourites *****************************************************/
        // GET - favourite events list
        get("/events/favourites/list", (request, response) -> {
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
                String priceValue = request.queryParams("price_value");

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
        post("/events/tickets/book/:id", (request, response) -> { // TODO: price not calculated properly
            String eventId = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eventId));

            if(event != null) {
                String amount = request.queryParams("amount");
                String userId = request.queryParams("user_id");

                Ticket ticket = TicketService.bookTicket(Integer.valueOf(eventId), Integer.valueOf(amount), Integer.valueOf(userId));

                response.status(201); // 201 created
                return om.writeValueAsString(ticket);
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
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
