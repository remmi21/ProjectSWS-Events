import event.EventService;

public class Main {

    public static void main(String [] args) {
        EventService e= new EventService();
        e.loadMongoEvents();
    }
}
