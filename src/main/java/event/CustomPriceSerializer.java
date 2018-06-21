package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;


public class CustomPriceSerializer extends StdSerializer<Price> {
    private ObjectMapper mapper = new ObjectMapper();

    public CustomPriceSerializer(){ this( null); }
    protected CustomPriceSerializer(Class<Price> t) {
        super(t);
    }

    @Override
    public void serialize(Price price, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("@context");
        jsonGenerator.writeStringField("name", "http://schema.org/name");
        jsonGenerator.writeStringField("price", "http://schema.org/price");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("@type", "http://schema.org/PriceSpecification");
        jsonGenerator.writeStringField("name", price.getName());
        jsonGenerator.writeNumberField("price", price.getPrice());
        jsonGenerator.writeEndObject();

        String serialized = mapper.writeValueAsString(JsonldResource.Builder.create().build(price));
    }
}
