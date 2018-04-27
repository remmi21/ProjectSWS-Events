package event;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EventService {
    public static Map events = new HashMap();
    private static final AtomicInteger count = new AtomicInteger(0);

    public Event findById(String id) {
        return (Event) events.get(id);
    }

    public void loadMongoEvents(){
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database = mongoClient.getDatabase("EventsSWS");
            MongoCollection<Document> collection = database.getCollection("eventsCollection");
            List<Document> documents = (List<Document>) collection.find().into(
                    new ArrayList<Document>());
            Event e;
            Integer id;
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {

                    Document doc = cur.next();
                    List list = new ArrayList(doc.values());
                    id = (Integer) doc.get("id");
                    //List v=(List)doc.get("Venues");
                    //Document d= (Document)v.get(0);
                   // List venue=(List)doc.get(0);
                  //  System.out.println(venue.get(0));
                    e = new Event(id,
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            (Integer) doc.get("limit"),
                            (Integer) doc.get("tickets_left"),
                            //(Venue) doc.get("Venues"),
                            (Date) doc.get("date"),
                            //  (Category)event.get("categories"),
                            (Integer) doc.get("price"));
                    events.put(id, e);

                }
            }
            Event e2= (Event)events.get(120);       //Testing
            System.out.println(e2.getId());
        } catch (MongoException e) {
                e.printStackTrace();
        }
    }
}
