package event;

import java.util.Date;

public class DateEv {
    private String uri;
    private Date eventStart ;
    private Date eventEnd;
    private Date registrationStart;
    private Date registrationEnd;

    public DateEv() {

    }

    public DateEv(String uri, Date eventStart, Date eventEnd, Date registrationStart, Date registrationEnd) {
        this.uri = uri;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
    }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }

    public Date getEventStart()  {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Date eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Date getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(Date registrationStart) {
        this.registrationStart = registrationStart;
    }

    public Date getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(Date registrationEnd) {
        this.registrationEnd = registrationEnd;
    }
}
