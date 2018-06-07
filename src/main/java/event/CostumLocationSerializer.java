package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;

public class CostumLocationSerializer extends StdSerializer<Location> {
    private ObjectMapper mapper = new ObjectMapper();

    public CostumLocationSerializer(){
        this( null);

    }
    protected CostumLocationSerializer(Class<Location> t) {
        super(t);
    }

    @Override
    public void serialize(Location location, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("@context");
            jsonGenerator.writeStringField("address", "http://schema.org/streetAddress ");
            jsonGenerator.writeStringField("city", "http://schema.org/addressLocality");
            jsonGenerator.writeStringField("state", "http://schema.org/addressRegion");
            jsonGenerator.writeStringField("country", "http://schema.org/addressRegion");
            jsonGenerator.writeStringField("zip", "http://schema.org/postalCode");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("@type", "http://schema.org/PostalAddress");
        jsonGenerator.writeStringField("address", location.getAddress());
        jsonGenerator.writeStringField("city", location.getCity());
        jsonGenerator.writeStringField("zip", location.getZipcode());
        jsonGenerator.writeStringField("country", location.getCountry());
        jsonGenerator.writeStringField("state", location.getState());
        jsonGenerator.writeEndObject();

        String serialized = mapper.writeValueAsString(JsonldResource.Builder.create().build(location));
    }
}
