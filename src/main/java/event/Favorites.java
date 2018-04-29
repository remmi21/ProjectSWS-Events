package event;

import java.util.List;

public class Favorites {
    private List<Event> favorites;
    private User user;

    public Favorites(List<Event> favorites, User user) {
        this.favorites = favorites;
        this.user = user;
    }

    public List<Event> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Event> favorites) {
        this.favorites = favorites;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
