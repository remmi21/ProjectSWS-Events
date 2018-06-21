package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/Properties")
public class Properties {
    @JsonldProperty("http://schema.org/groupe_registration_allowed")
    private boolean group_registrations_allowed;
    @JsonldProperty("http://schema.org/groupe_size")
    private Integer groupe_size;
    @JsonldProperty("http://schema.org/active")
    private boolean active;
    @JsonldProperty("http://schema.org/members_only")
    private boolean members_only;

    public Properties() {

    }

    public Properties(boolean group_registrations_allowed, Integer groupe_size, boolean active, boolean members_only) {
        this.group_registrations_allowed = group_registrations_allowed;
        this.groupe_size = groupe_size;
        this.active = active;
        this.members_only = members_only;
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
