package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;

public class CustomDateEvSerializer extends StdSerializer<DateEv> {
    private ObjectMapper mapper = new ObjectMapper();

    public CustomDateEvSerializer(){ this( null); }
    protected CustomDateEvSerializer(Class<DateEv> t) {
        super(t);
    }

    @Override
    public void serialize(DateEv eventDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("@context");
            jsonGenerator.writeStringField("eventStart", "http://schema.org/startDate");
            jsonGenerator.writeStringField("eventEnd", "http://schema.org/endDate");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("@type", "http://schema.org/Date");
        jsonGenerator.writeStringField("eventStart", String.valueOf(eventDate.getEventStart()));
        jsonGenerator.writeStringField("eventEnd", String.valueOf(eventDate.getEventEnd()));
        jsonGenerator.writeStringField("registrationStart", String.valueOf(eventDate.getRegistrationStart())); // TODO: check if this is working properly
        jsonGenerator.writeStringField("registrationEnd", String.valueOf(eventDate.getRegistrationEnd()));
        jsonGenerator.writeEndObject();

        String serialized = mapper.writeValueAsString(JsonldResource.Builder.create().build(eventDate));
    }
}
