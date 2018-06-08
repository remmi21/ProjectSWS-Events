package event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.Date;

@JsonldType("http://schema.org/Date")
public class DateEv {
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date eventStart;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date eventEnd;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date registrationStart;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date registrationEnd;

    public DateEv() {

    }

    public DateEv(Date eventStart, Date eventEnd, Date registrationStart, Date registrationEnd) {
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
    }

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
