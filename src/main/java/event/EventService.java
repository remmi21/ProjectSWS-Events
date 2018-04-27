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
                    e = new Event(id,
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            (Integer) doc.get("limit"),
                            (Integer) doc.get("tickets_left"),
                            (Venue) doc.get("venue"),
                            (Date) doc.get("date"),
                            //  (Category)event.get("categories"),
                            (Integer) doc.get("price"));
                    events.put(id, e);

                }
            }
        } catch (MongoException e) {
                e.printStackTrace();
        }
    }
}
