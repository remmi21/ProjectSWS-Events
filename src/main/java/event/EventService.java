package event;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// TODO: newly created stuff is not stored in DB !!!!

public class EventService {
    public static Map<Integer, Event> events = new HashMap();
    public static Map<Integer,Venue> venueList = new HashMap();
    public static Map<Integer,Category> catList = new HashMap();
    public static Map<Integer,Location> locationList = new HashMap();
    public static Integer loc_count;
    public static Map<Integer,User> userList=new HashMap<>();

    public static ArrayList<Order> orderList = new ArrayList<>();

    public EventService(){
        User julia = new User("Julia","Wanker",23,7343, orderList);
        User ramona = new User("Ramona","Huber",23,7344, orderList);
        userList.put(julia.getId(),julia);
        userList.put(ramona.getId(),ramona);
    }

    public static Event findById(Integer id) {
        return (Event) events.get(id);
    }

    public static Event add(Integer id, String name, String description, String status, Integer limit, Integer tickets_left,
                            List<Venue> venueList, DateEv date, List<Category> categories, List<Price> price, Properties prop) {

        Event event = new Event(id,"http://localhost:8080/kangarooEvents/"+id,name,description,status,limit,tickets_left,venueList,date,categories,price,prop);
        events.put(id, event);

        return event;
    }

    public static void delete(Integer id) {
            events.remove(id);
    }

    public static List findAll() {
        return new ArrayList<>(events.values());
    }

    // helper function
    public static Object getKeyFromValue(Map map, Object value) {
        for (Object o : map.keySet()) {
            if (map.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public void loadMongoEvents(){
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Integer ticketLeft=0;
        Integer ticketLimit=0;
        loc_count=0;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database = mongoClient.getDatabase("EventsSWS");
            MongoCollection<Document> collection = database.getCollection("eventsCollection");
            DateEv d=null;
            Properties properties=null;
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    List<Category> cateList = new ArrayList<>();
                    List<Price> priceList = new ArrayList<>();
                    List<Venue> venuesList = new ArrayList<>();

                    Document doc = cur.next();
                    List<Document> venuesAsDocuments = (ArrayList)doc.get("Venues");
                    List<Document> categoryAsDocuments = (ArrayList)doc.get("Categories");
                    List<Document> dateAsDocuments= (ArrayList)doc.get("Datetimes") ;
                    List<Document> priceAsDocuments= (ArrayList)doc.get("Prices") ;

                    for (Document dateAsDoc : dateAsDocuments){
                        d=new DateEv((Date)simpleDateFormat.parse((String)dateAsDoc.get("event_start")),
                                (Date)simpleDateFormat.parse((String)dateAsDoc.get("event_end")),
                                (Date)simpleDateFormat.parse((String)dateAsDoc.get("registration_start")),
                                (Date)simpleDateFormat.parse((String)dateAsDoc.get("registration_end")));
                        ticketLeft=(Integer)dateAsDoc.get("tickets_left");
                        ticketLimit=(Integer)dateAsDoc.get("limit");
                    }

                    for (Document venueAsDoc : venuesAsDocuments){
                        Location loc=new Location((String)venueAsDoc.get("address"),
                                (String)venueAsDoc.get("city"),
                                (String)venueAsDoc.get("state"),
                                (String)venueAsDoc.get("country"),
                                (String)venueAsDoc.get("zip"));
                        locationList.put(loc_count,loc);
                        loc_count++;
                        venuesList.add(new Venue((Integer) venueAsDoc.get("id"),
                                "http://localhost:8080/kangarooEvents/venue/"+venueAsDoc.get("id"),
                                (String)venueAsDoc.get("name"),loc));
                        venueList.put((Integer) venueAsDoc.get("id"),new Venue((Integer) venueAsDoc.get("id"),
                                "http://localhost:8080/kangarooEvents/venue/"+venueAsDoc.get("id"),
                                (String)venueAsDoc.get("name"),loc));
                    }

                    for (Document pricesAsDoc : priceAsDocuments){
                        priceList.add(new Price((String)pricesAsDoc.get("name"),
                                (Integer) pricesAsDoc.get("amount")));
                    }
                    for (Document catAsDoc : categoryAsDocuments){
                        cateList.add(new Category((Integer) catAsDoc.get("id"),
                                "/kangarooEvents/category/"+catAsDoc.get("id"),
                                (String)catAsDoc.get("name")));
                        catList.put((Integer) catAsDoc.get("id"),new Category((Integer) catAsDoc.get("id"),
                                "/kangarooEvents/category/"+catAsDoc.get("id"),
                                (String)catAsDoc.get("name")));
                    }

                    properties=new Properties((Boolean) doc.get("group_registrations_allowed"),
                            (Integer) doc.get("group_registrations_max"),
                            (Boolean) doc.get("active"),
                            (Boolean) doc.get("member_only"));
                    Event e=new Event((Integer) doc.get("id"),
                            "http://localhost:8080/kangarooEvents/"+doc.get("id"),
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            ticketLimit,
                            ticketLeft,
                            venuesList,
                            d,
                            cateList,
                            priceList,
                            properties);

                    events.put((Integer) doc.get("id"),e);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*
            Event e2= (Event)events.get(120);       //Testing
            System.out.println("Id: "+e2.getId());
            System.out.println("Place of venues: "+e2.getVenueList().get(0).getLocation().getAddress());
            System.out.println("Category: "+e2.getCategories().get(0).getName());
            */
        } catch (MongoException e) {
                e.printStackTrace();
        }
    }
}
