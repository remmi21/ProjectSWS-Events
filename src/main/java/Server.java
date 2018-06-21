import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import event.*;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

public class Server {
    private static EventService eventService = new EventService();
    private static ObjectMapper om = new ObjectMapper();
    private static Gson gson = new Gson();
    private static List<ActionAPI> actionAPI=new ArrayList<>();

    public static void main(String [] args) {
        EventService e= new EventService();
        e.loadMongoEvents();
        om.registerModule(new JsonldModule(() -> om.createObjectNode()));

        // Fill parameter lists
        ArrayList<String> params_event_list = new ArrayList<String>();
        ArrayList<String> params_event = new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_event_new= new ArrayList<String>(){{
            add("p1");
            add("p2");
            add("p3");
            add("p4");
            add("p5");
            add("p6");
        }};
        ArrayList<String> params_event_remove = new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_venue_list = new ArrayList<String>();
        ArrayList<String> params_venue = new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_venue_new = new ArrayList<String>(){{
            add("id");
            add("venue_id");
            add("name");
            add("address");
            add("city");
            add("state");
            add("country");
            add("zipcode");
        }};
        ArrayList<String> params_venue_remove = new ArrayList<String>(){{
            add("id");
            add("venue_id");
        }};
        ArrayList<String> params_date = new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_date_new = new ArrayList<String>(){{
            add("id");
            add("event_start");
            add("event_end");
            add("registration_start");
            add("registration_end");
        }};
        ArrayList<String> params_date_delete = new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_properties_list = new ArrayList<String>(){{
            add("group_registrations_allowed");
            add("group_size");
            add("active");
            add("members_only");
        }};
        ArrayList<String> params_properties_new = new ArrayList<String>(){{
            add("id");
            add("group_registrations_allowed");
            add("group_size");
            add("active");
            add("members_only");
        }};
        ArrayList<String> params_properties_remove= new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_cat_list = new ArrayList<String>();
        ArrayList<String> params_cat= new ArrayList<String>(){{
            add("id");
        }};
        ArrayList<String> params_cat_new= new ArrayList<String>(){{
            add("cat_id");
            add("name");
        }};
        ArrayList<String> params_cat_new_event = new ArrayList<String>(){{
            add("id");
            add("cat_id");
            add("name");
        }};
        ArrayList<String> params_cat_remove = new ArrayList<String>(){{
            add("id");
            add("cat_id");
        }};
        ArrayList<String> params_location_list = new ArrayList<String>();
        ArrayList<String> params_location = new ArrayList<String>(){{
            add("zipcode");
        }};
        ArrayList<String> params_location_new = new ArrayList<String>(){{
            add("address");
            add("city");
            add("state");
            add("country");
            add("zipcode");
        }};
        ArrayList<String> params_location_event = new ArrayList<String>(){{
            add("id"); // event id
            add("venue_id");
            add("address");
            add("city");
            add("state");
            add("country");
            add("zipcode");
        }};
        ArrayList<String> params_location_remove = new ArrayList<String>(){{
            add("id"); // venue id
            add("zipcode");
        }};
        ArrayList<String> params_favourites = new ArrayList<String>();
        ArrayList<String> params_price = new ArrayList<String>(){{
            add("id"); // event id
        }};
        ArrayList<String> params_price_new = new ArrayList<String>(){{
            add("id"); // event id
            add("name");
            add("price_value");
        }};
        ArrayList<String> params_ticket = new ArrayList<String>(){{
            add("id"); // event id
            add("amount");
            add("user_id");
        }};
        ArrayList<String> params_orders = new ArrayList<String>(){{
            add("id"); // user id
        }};

        // Fill array for client orientation
        // events
        actionAPI.add(new ActionAPI("GET","/kangarooEvents",0, params_event_list));
        actionAPI.add(new ActionAPI("GET","/kangarooEvents/",1, params_event));
        actionAPI.add(new ActionAPI("POST","/kangarooEvents/new", 6, params_event_new)); // note: params in total 11, to add in request 6
        actionAPI.add(new ActionAPI("DELETE","/kangarooEvents/remove/",1, params_event_remove));
        // venues
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/venue", 0, params_venue_list));
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/venue/", 1, params_venue));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/venue/new", 8, params_venue_new));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/venue/remove", 2, params_venue_remove));
        // dates
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/date/", 1, params_date));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/date/new", 5, params_date_new));
        actionAPI.add(new ActionAPI("DELETE", "/kangarooEvents/date/delete/", 1, params_date_delete));
        // properties
        actionAPI.add(new ActionAPI("POST", "/kangarooEvents/properties", 4, params_properties_list));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/properties/new", 5, params_properties_new));
        actionAPI.add(new ActionAPI("DELETE", "/kangarooEvents/events/properties/remove/", 1, params_properties_remove));
        // categories
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/categories", 0, params_cat_list));
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/category/", 1, params_cat));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/category/new", 2, params_cat_new));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/category/new", 3, params_cat_new_event));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/category/remove/:id:", 2, params_cat_remove));
        // locations
        actionAPI.add(new ActionAPI("GET", "/kangarooEvent/location", 0, params_location_list));
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/locatoin/", 1, params_location));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/location/new", 5, params_location_new));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/location", 7, params_location_event));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/venues/location/remove/", 2, params_location_remove));
        // favourites
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/events/favourites", 0, params_favourites));
        // price
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/pricing/", 1, params_price));
        actionAPI.add(new ActionAPI("PUT", "/kangarooEvents/events/:id:/pricing/new", 3, params_price_new));
        // ticket
        actionAPI.add(new ActionAPI("POST", "/kangarooEvents/events/:id:/tickets", 3, params_ticket));
        // order
        actionAPI.add(new ActionAPI("GET", "/kangarooEvents/orders/user/", 1, params_orders));

        // Start embedded server at this port
        port(8090);

        // Main Page, welcome
        get("/", (requet, response) -> {            // get orientation json back to client
            return om.writer().writeValueAsString(actionAPI);
        });

        /***************************************************** Events *****************************************************/

        // POST - add event
        post("/kangarooEvents/new", (request, response) -> {
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
        delete("/kangarooEvents/remove/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));

            if(event != null) {
                eventService.delete(Integer.parseInt(id));
                return om.writeValueAsString("event with id " + id + " was deleted successfully");
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        // GET - get event list
        get("/kangarooEvents", (requet, response) -> {
            List allEvents = eventService.findAll();
            if(allEvents.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                String json="";
                for(Object event: allEvents){
                    json=json+om.writer().writeValueAsString(JsonldResource.Builder.create().build(event));
                }
                return json;
            }
        });

        // GET - find event by id
        get("/kangarooEvents/:id", (request, response) -> {
            String id = request.params(":id");
            Event event = eventService.findById(Integer.parseInt(id));
            if(event != null) {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(event));
                return json;
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        /***************************************************** Venues *****************************************************/
        // PUT - add venue to event
        put("/kangarooEvents/events/:id/venue/new", (request, response) -> {
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

                int parsed_venueId = 0;
                try {
                    if (venueId != null) {
                        parsed_venueId = Integer.parseInt(venueId);
                    }
                } catch (NumberFormatException formatException) {

                }

                if(parsed_venueId == 0) {
                    response.status(500);
                    return om.writeValueAsString("invalid venue id");
                }

                Location loc= new Location(address, city, state, country, zipcode);

                Venue venue = VenueService.add(Integer.parseInt(id), parsed_venueId, "http://localhost:8080/kangarooEvents/venue/"+venueId, name, loc);

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
        get("/kangarooEvents/venue/:id", (request, response) -> {
            Venue venue = VenueService.findById(Integer.valueOf(request.params(":id")));
            if (venue != null) {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(venue));
                return json;
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("venue not found");
            }
        });

        // GET - get venue list
        get("/kangarooEvents/venue", (request, response) -> {
            VenueService venueService = new VenueService();

            List allVenues = venueService.findAll();

            if(allVenues.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no venues found");
            } else {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(allVenues));
                return json;
            }
        });

        // PUT - delete venue from event
        put("/kangarooEvents/events/:id/venue/remove", (request, response) -> {
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
        put("/kangarooEvents/events/:id/date/new", (request, response) -> {
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
        get("/kangarooEvents/date/:start", (request, response) -> {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date resultingEventStartDate = df.parse(request.params(":start"));
            ArrayList event = DateEvService.searchEventsByDate(resultingEventStartDate);
            if (event != null) {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(event));
                return json;
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("event not found");
            }
        });

        // DELETE - delete date from event
        delete("/kangarooEvents/date/delete/:id", (request, response) -> {
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
        put("/kangarooEvents/events/:id/properties/new",(request, response)-> {
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
        delete("/kangarooEvents/events/properties/remove/:id",(request, response)-> {
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

        // POST - search all events with the given properties
        post("/kangarooEvents/properties",(request, response)-> {
            PropertiesService prop = new PropertiesService();
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

            List allEvents = prop.searchEventbyProp(Boolean.parseBoolean(groups_allowed),parsed_groupsize,
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
        put("/kangarooEvents/category/new",(request, response)-> {
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
        put("/kangarooEvents/events/:id/category/new",(request, response)-> {
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
        get("/kangarooEvents/category/:id",(request, response)-> {
            String id = request.params(":id");
            CategoryService cat=new CategoryService();
            Category cate=cat.findById(Integer.parseInt(id));
            if (cate != null) {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(cate));
                return json;
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("category not found");
            }
        });

        // PUT - delete category from event
        put("/kangarooEvents/events/category/remove/:id",(request, response)-> {
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
        get("/kangarooEvents/categories", (request, response) -> {
            CategoryService cate =new CategoryService();
            List allCats = cate.findAll();
            if(allCats.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(allCats));
                return json;
            }
        });

        /***************************************************** Locations *****************************************************/
        // PUT - add location
        put("/kangarooEvents/location/new",(request, response)-> {
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
        put("/kangarooEvents/events/:id/location",(request, response)-> {
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
        get("/kangarooEvents/location/:zip",(request, response)-> { // TODO: data which is already in database is not found here since we do not load it to gloabal location list
            String zip = request.params(":zip");
            LocationService locationService = new LocationService();
            ArrayList<Event> events = locationService.findByZip(zip);
            if (events.size()>0) {
                // Convert object to JSON string and pretty print
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(events));
                return json;
            } else {
                response.status(404); // 404 not found
                return om.writeValueAsString("no events found for location with zip "+ zip);
            }
        });

        // PUT - delete location from venue
        put("/kangarooEvents/venues/location/remove/:id",(request, response)-> {
            String venueId = request.params(":id");
            VenueService venueService = new VenueService();
            Venue venue = venueService.findById(Integer.parseInt(venueId));

            if(venue != null) {
                String zipCode = request.queryParams("zipcode");
                LocationService locationService = new LocationService();

                Venue venueDelLoc = locationService.remove(Integer.parseInt(venueId), zipCode);
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
        get("/kangarooEvents/location", (request, response) -> {
            LocationService locS =new LocationService();
            List allLocs = locS.findAll();
            if(allLocs.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no locations found");
            } else {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(allLocs));
                return json;
            }
        });

        /*****************************************************  Favourites *****************************************************/
        // GET - favourite events list
        get("/kangarooEvents/events/favourites", (request, response) -> {
            FavoritesService fav =new FavoritesService();
            List allFav = fav.getFavorites();
            if(allFav.isEmpty()) {
                response.status(404);
                return om.writeValueAsString("no events found");
            } else {
                String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(allFav));
                return json;
            }
        });

        /***************************************************** Price *****************************************************/

        // PUT - add price to event
        put("/kangarooEvents/events/:id/pricing/new", (request, response) -> {
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
        get("/kangarooEvents/pricing/:id", (request, response) -> {
            String id = request.params(":id");

            List <Price> eventPrices = PriceService.findEventPrice(Integer.parseInt(id));

            if(eventPrices != null) {
                if (!eventPrices.isEmpty()) {
                    String json = om.writer().writeValueAsString(JsonldResource.Builder.create().build(eventPrices));
                    return json;
                } else {
                    response.status(404);
                    return om.writeValueAsString("no prices found for the given event");
                }
            } else {
                response.status(404);
                return om.writeValueAsString("event not found");
            }
        });

        /***************************************************** Ticket *****************************************************/

        // POST - book ticket for event
        post("/kangarooEvents/events/:id/tickets", (request, response) -> {
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
        get("/kangarooEvents/orders/user/:id", (request, response) -> {
            String userId = request.params(":id");

            List<Order> orders = OrderService.getOrders(Integer.parseInt(userId));

            if(orders != null) {
                if (!orders.isEmpty()) {
                    String json=om.writer().writeValueAsString(JsonldResource.Builder.create().build(orders));
                    return json;
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
