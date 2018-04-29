package event;

public class Properties {
    private Event event;
    private boolean group_registrations_allowed;
    private Integer groupe_size;
    private boolean active;
    private boolean members_only;

    public Properties(Event event, boolean group_registrations_allowed, Integer groupe_size, boolean active, boolean members_only) {
        this.event = event;
        this.group_registrations_allowed = group_registrations_allowed;
        this.groupe_size = groupe_size;
        this.active = active;
        this.members_only = members_only;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isGroup_registrations_allowed() {
        return group_registrations_allowed;
    }

    public void setGroup_registrations_allowed(boolean group_registrations_allowed) {
        this.group_registrations_allowed = group_registrations_allowed;
    }

    public Integer getGroupe_size() {
        return groupe_size;
    }

    public void setGroupe_size(Integer groupe_size) {
        this.groupe_size = groupe_size;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isMembers_only() {
        return members_only;
    }

    public void setMembers_only(boolean members_only) {
        this.members_only = members_only;
    }
}
