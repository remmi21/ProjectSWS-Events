package event;

import java.util.List;

public class Favorites {
    private List<Event> favorites;

    public Favorites(List<Event> favorites) {
        this.favorites = favorites;
    }

    public List<Event> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Event> favorites) {
        this.favorites = favorites;
    }
}
