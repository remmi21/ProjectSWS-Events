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

            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    Event e;
                    List<Venue> venueList = new ArrayList<>();

                    Document doc = cur.next();
                    List<Document> venuesAsDocuments = (ArrayList)doc.get("Venues");

                    for (Document venueAsDoc : venuesAsDocuments){
                        venueList.add(new Venue((Integer) venueAsDoc.get("id"),
                                (String)venueAsDoc.get("name"),
                                (String)venueAsDoc.get("address"),
                                (String)venueAsDoc.get("city"),
                                (String)venueAsDoc.get("state"),
                                (String)venueAsDoc.get("country"),
                                (String)venueAsDoc.get("zip")));
                    }

                   e = new Event((Integer) doc.get("id"),
                            (String) doc.get("name"),
                            (String) doc.get("description"),
                            (String) doc.get("status"),
                            (Integer) doc.get("limit"),
                            (Integer) doc.get("tickets_left"),
                            venueList,
                            (Date) doc.get("date"),
                            //  (Category)event.get("categories"),
                            (Integer) doc.get("price"));
                    events.put(e.getId(), e);
                }
            }
            Event e2= (Event)events.get(120);       //Testing
            System.out.println("Id: "+e2.getId());
            System.out.println("Place of venues: "+e2.getVenueList().get(0).getAddress());
        } catch (MongoException e) {
                e.printStackTrace();
        }
    }
}
