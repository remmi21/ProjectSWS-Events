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

public class EventService {
    public static Map events = new HashMap();

    public EventService(){

    }

    public Event findById(String id) {
        return (Event) events.get(id);
    }

    public Event add(Integer id, String name, String description, String status, Integer limit, Integer tickets_left,
                     List<Venue> venueList, DateEv date, List<Category> categories, List<Pricing> price, Properties prop) {
        Event event = new Event(id,name,description,status,limit,tickets_left,venueList,date,categories,price,prop);
        events.put(id.toString(), event);
        return event;
    }

    public void delete(String id) {
            events.remove(id);
    }

    public List findAll() {
        return new ArrayList<>(events.values());
    }

    public void loadMongoEvents(){
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer ticketLeft=0;
        Integer ticketLimit=0;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database = mongoClient.getDatabase("EventsSWS");
            MongoCollection<Document> collection = database.getCollection("eventsCollection");
            DateEv d=null;
            Properties properties=null;
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    List<Venue> venueList = new ArrayList<>();
                    List<Category> cateList = new ArrayList<>();
                    List<Pricing> priceList = new ArrayList<>();

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
                        venueList.add(new Venue((Integer) venueAsDoc.get("id"),
                                (String)venueAsDoc.get("name"),
                                (String)venueAsDoc.get("address"),
                                (String)venueAsDoc.get("city"),
                                (String)venueAsDoc.get("state"),
                                (String)venueAsDoc.get("country"),
                                (String)venueAsDoc.get("zip")));
                    }

                    for (Document pricesAsDoc : priceAsDocuments){
                        priceList.add(new Pricing((String)pricesAsDoc.get("name"),
                                (Integer) pricesAsDoc.get("amount")));
                    }
                    for (Document catAsDoc : categoryAsDocuments){
                        cateList.add(new Category((Integer) catAsDoc.get("id"),
                                (String)catAsDoc.get("name")));
                    }
                    properties=new Properties((Boolean) doc.get("group_registrations_allowed"),
                            (Integer) doc.get("group_registrations_max"),
                            (Boolean) doc.get("active"),
                            (Boolean) doc.get("member_only"));

                    Event e=new Event((Integer) doc.get("id"),
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            ticketLimit,
                            ticketLeft,
                            venueList,
                            d,
                            cateList,
                            priceList,
                            properties);

                    events.put((Integer) doc.get("id"),e);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Event e2= (Event)events.get(120);       //Testing
           // System.out.println("Id: "+e2.getId());
           // System.out.println("Place of venues: "+e2.getVenueList().get(0).getAddress());
           // System.out.println("Category: "+e2.getCategories().get(0).getName());
        } catch (MongoException e) {
                e.printStackTrace();
        }
    }
}
