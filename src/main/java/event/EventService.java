package event;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class EventService {
    public static Map events = new HashMap();

    public Event findById(String id) {
        return (Event) events.get(id);
    }

    public void loadMongoEvents(){
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database = mongoClient.getDatabase("EventsSWS");
            MongoCollection<Document> collection = database.getCollection("eventsCollection");
            Date d=null;
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    List<Venue> venueList = new ArrayList<>();
                    List<Category> cateList = new ArrayList<>();

                    Document doc = cur.next();
                    List<Document> venuesAsDocuments = (ArrayList)doc.get("Venues");
                    List<Document> categoryAsDocuments = (ArrayList)doc.get("Categories");
                    List<Document> dateAsDocuments= (ArrayList)doc.get("Datetimes") ;

                    for (Document dateAsDoc : dateAsDocuments){
                        d=new Date((Date)dateAsDoc.get("event_start"),
                                (Date)dateAsDoc.get("event_end"),
                                (Date)dateAsDoc.get("registration_start"),
                                (Date)dateAsDoc.get("registration_end"));
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
                    for (Document catAsDoc : categoryAsDocuments){
                        cateList.add(new Category((Integer) catAsDoc.get("id"),
                                (String)catAsDoc.get("name")));
                    }

                    events.put((Integer) doc.get("id"), new Event((Integer) doc.get("id"),
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            (Integer) doc.get("limit"),
                            (Integer) doc.get("tickets_left"),
                            venueList,
                            d,
                            cateList,
                            (Integer) doc.get("price")));
                }
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
