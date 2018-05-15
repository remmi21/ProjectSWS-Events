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

            int parsed_id = 0;
            try {
                if (id != null) {
                    parsed_id = Integer.parseInt(id);
                }
            } catch (NumberFormatException formatException) {
                parsed_id = 0;
            }

            if(parsed_id == 0) {
                response.status(500);
                return om.writeValueAsString("invalid event id");
            }

            Event checkEvent = EventService.findById(parsed_id);
            if(checkEvent != null) {
                response.status(403); // forbidden
                return om.writeValueAsString("event with id "+ id +" already exists");
            }

            int parsed_limit=0, parsed_tickets_left=0;
            try {
                if (limit != null) {
                    parsed_limit = Integer.parseInt(limit);
                }
                if (tickets_left != null) {
                    parsed_tickets_left = Integer.parseInt(tickets_left);
                }
            } catch (NumberFormatException formatException) {

            }

            Event event = EventService.add(parsed_id, name, description, status, parsed_limit, parsed_tickets_left, venueList, date, categories, price, prop);

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

                int parsed_venueId = 0;
                try {
                    if (venueId != null) {
                        parsed_venueId = Integer.parseInt(venueId);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_venueId = 0;
                }

                if(parsed_venueId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid venue id");
                }

                Venue venue = VenueService.add(Integer.parseInt(id), parsed_venueId, name,loc);

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

        // PUT - delete venue from event
        put("/events/venue/remove/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                String venueId = request.queryParams("venue_id");

                VenueService venueService = new VenueService();

                int parsed_venueId = 0;
                try {
                    if (venueId != null) {
                        parsed_venueId = Integer.parseInt(venueId);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_venueId = 0;
                }

                if(parsed_venueId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid venue id");
                }

                venueService.remove(Integer.parseInt(id), parsed_venueId);

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

                int parsed_groupsize = 0;
                try {
                    if (groupsize != null) {
                        parsed_groupsize = Integer.parseInt(groupsize);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_groupsize = 0;
                }

                prop.add(event.getId(), Boolean.parseBoolean(groups_allowed), parsed_groupsize,
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

            int parsed_groupsize = 0;
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
        put("/events/category/add",(request, response)-> { // TODO: add categories to overall category list when adding category to event (function is not needed then)
            CategoryService cat = new CategoryService();
            String catId = request.queryParams("cat_id");
            String name = request.queryParams("name");

            int parsed_catId = 0;
            try {
                if (catId != null) {
                    parsed_catId = Integer.parseInt(catId);
                }
            } catch (NumberFormatException formatException) {
                parsed_catId = 0;
            }

            if(parsed_catId == 0) {
                response.status(500);
                return om.writeValueAsString("invalid category id");
            }

            cat.add(parsed_catId,name);

            if(cat.findById(parsed_catId) != null) { // check if category was successfully added to list
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

            int parsed_catId = 0;
            try {
                if (catId != null) {
                    parsed_catId = Integer.parseInt(catId);
                }
            } catch (NumberFormatException formatException) {
                parsed_catId = 0;
            }

            if(parsed_catId == 0) {
                response.status(500);
                return om.writeValueAsString("invalid category id");
            }

            cat.addTo(Integer.parseInt(eid),parsed_catId,name);

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

        // PUT - delete category from event
        put("/events/category/remove/:id",(request, response)-> { // TODO: category not found -> maybe due to wrong search because of overall category list -> may be solved when categories are added to event, then add them to overall category list
            String eventId = request.params(":id");
            EventService eventService = new EventService();
            Event event = eventService.findById(Integer.parseInt(eventId));

            if(event != null) {
                CategoryService categoryService = new CategoryService();
                String catId = request.queryParams("cat_id");

                int parsed_catId = 0;
                try {
                    if (catId != null) {
                        parsed_catId = Integer.parseInt(catId);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_catId = 0;
                }

                if(parsed_catId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid category id");
                }

                event = categoryService.remove(Integer.parseInt(eventId), parsed_catId);

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
        put("/events/location/add",(request, response)-> { // TODO: same as with category: when adding location to venue, add location to overall location list (this functions is not needed then)
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
        put("/events/location/addto/:id",(request, response)-> { // TODO: add location to overall locations list when adding it to venue
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

                int parsed_venueId = 0;
                try {
                    if (venueId != null) {
                        parsed_venueId = Integer.parseInt(venueId);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_venueId = 0;
                }

                if(parsed_venueId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid venue id");
                }

                Location location = locS.addTo(parsed_venueId, address, city, state, country, zipcode);

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
        get("/events/location/find/:zip",(request, response)-> { // TODO: no locations found although valid zipcode was used -> this may be due to not solvig locations in overall location list when they are added to a venue
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

        // PUT - delete location from venue
        put("/events/location/remove/:id",(request, response)-> {
            String venueId = request.params(":id");
            VenueService venueService = new VenueService();
            Venue venue = venueService.findById(Integer.parseInt(venueId));

            if(venue != null) {
                String lid = request.queryParams("loc_id"); // TODO: what is location id ??
                String zipCode = request.queryParams("zipcode");
                LocationService locationService = new LocationService();

                int parsed_locId = 0;
                try {
                    if (lid != null) {
                        parsed_locId = Integer.parseInt(lid);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_locId = 0;
                }

                if(parsed_locId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid location id");
                }

                Venue venueDelLoc = locationService.remove(Integer.parseInt(venueId), parsed_locId, zipCode);
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

                int parsed_price = 0;
                try {
                    if (priceValue != null) {
                        parsed_price = Integer.parseInt(priceValue);
                    }
                } catch (NumberFormatException formatException) {
                    parsed_price = 0;
                }

                PriceService.addPrice(Integer.parseInt(id), name, parsed_price);

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
        post("/events/tickets/book/:id", (request, response) -> {
            String eventId = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(eventId));

            if(event != null) {
                String amount = request.queryParams("amount");
                String userId = request.queryParams("user_id");

                int parsed_amount=0, parsed_user_id=0;
                try {
                    if (amount != null) {
                        parsed_amount = Integer.parseInt(amount);
                    }
                    if(userId != null) {
                        parsed_user_id = Integer.parseInt(userId);
                    }
                } catch (NumberFormatException formatException) {

                }

                Ticket ticket = TicketService.bookTicket(Integer.parseInt(eventId), parsed_amount, parsed_user_id);

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

            List<Order> orders = OrderService.getOrders(Integer.parseInt(userId));

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
