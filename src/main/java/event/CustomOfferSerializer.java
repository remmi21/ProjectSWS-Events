package event;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;
import java.util.List;


public class CustomOfferSerializer extends StdSerializer<List<Offer>> {
    private ObjectMapper mapper = new ObjectMapper();

    public CustomOfferSerializer(){ this( null); }
    protected CustomOfferSerializer(Class<List<Offer>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Offer> prices, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        String serialized="";
        jsonGenerator.writeStartArray();
        for(int i =0; i<prices.size();i++) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("@context");
            jsonGenerator.writeStringField("priceSpecification","http://schema.org/priceSpecification");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeStringField("@type", "http://schema.org/Offer");
            jsonGenerator.writeObjectFieldStart("priceSpecification");
                jsonGenerator.writeObjectFieldStart("@context");
                jsonGenerator.writeStringField("name", "http://schema.org/name");
                jsonGenerator.writeStringField("price", "http://schema.org/price");
                jsonGenerator.writeEndObject();
                jsonGenerator.writeStringField("@type", "http://schema.org/PriceSpecification");
                jsonGenerator.writeStringField("name", prices.get(i).getEventPrice().getName());
                jsonGenerator.writeNumberField("price", prices.get(i).getEventPrice().getPrice());
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();

            serialized = serialized + mapper.writeValueAsString(JsonldResource.Builder.create().build(prices));
        }
        jsonGenerator.writeEndArray();
    }
}
