package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;
import java.util.List;

public class CustomVenueSerializer extends StdSerializer<List<Venue>> {
    private ObjectMapper mapper = new ObjectMapper();

    public CustomVenueSerializer(){ this( null); }
    protected CustomVenueSerializer(Class<List<Venue>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Venue> venue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String serialized="";
        for(int i =0; i<venue.size();i++) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("@context");
            jsonGenerator.writeStringField("id","http://schema.org/identifier");
            jsonGenerator.writeStringField("uri", "http://schema.org/url");
            jsonGenerator.writeStringField("name", "http://schema.org/name");
            jsonGenerator.writeStringField("location", "http://schema.org/location");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeStringField("@type", "http://schema.org/Organization");
            jsonGenerator.writeNumberField("id",venue.get(i).getId());
            jsonGenerator.writeStringField("uri", venue.get(i).getUri());
            jsonGenerator.writeStringField("name", venue.get(i).getName());
            jsonGenerator.writeObjectFieldStart("location");
                jsonGenerator.writeObjectFieldStart("@context");
                jsonGenerator.writeStringField("address", "http://schema.org/streetAddress ");
                jsonGenerator.writeStringField("city", "http://schema.org/addressLocality");
                jsonGenerator.writeStringField("state", "http://schema.org/addressRegion");
                jsonGenerator.writeStringField("country", "http://schema.org/addressRegion");
                jsonGenerator.writeStringField("zip", "http://schema.org/postalCode");
                jsonGenerator.writeEndObject();
                jsonGenerator.writeStringField("@type", "http://schema.org/PostalAddress");
                jsonGenerator.writeStringField("address", venue.get(i).getLocation().getAddress());
                jsonGenerator.writeStringField("city", venue.get(i).getLocation().getCity());
                jsonGenerator.writeStringField("zip", venue.get(i).getLocation().getZipcode());
                jsonGenerator.writeStringField("country", venue.get(i).getLocation().getCountry());
                jsonGenerator.writeStringField("state", venue.get(i).getLocation().getState());
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();

            serialized = serialized + mapper.writeValueAsString(JsonldResource.Builder.create().build(venue));
        }

    }
}
