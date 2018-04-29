package event;

import java.util.Date;

public class DateEv {
    private Date event_start ;
    private Date event_end;
    private Date registration_start;
    private Date registration_end;


    public DateEv(Date event_start, Date event_end, Date registration_start, Date registration_end) {
        this.event_start = event_start;
        this.event_end = event_end;
        this.registration_start = registration_start;
        this.registration_end = registration_end;
    }

    public Date getEvent_start() {
        return event_start;
    }

    public void setEvent_start(Date event_start) {
        this.event_start = event_start;
    }

    public Date getEvent_end() {
        return event_end;
    }

    public void setEvent_end(Date event_end) {
        this.event_end = event_end;
    }

    public Date getRegistration_start() {
        return registration_start;
    }

    public void setRegistration_start(Date registration_start) {
        this.registration_start = registration_start;
    }

    public Date getRegistration_end() {
        return registration_end;
    }

    public void setRegistration_end(Date registration_end) {
        this.registration_end = registration_end;
    }
}
