package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;
import java.util.List;

public class CustomCategorySerializer extends StdSerializer<List<Category>> {
    private ObjectMapper mapper = new ObjectMapper();

    public CustomCategorySerializer(){ this( null); }
    protected CustomCategorySerializer(Class<List<Category>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Category> cat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String serialized="";
        jsonGenerator.writeStartArray();
        for(int i =0; i<cat.size();i++) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("@context");
            jsonGenerator.writeStringField("id", "http://schema.org/identifier");
            jsonGenerator.writeStringField("Name", "http://schema.org/name");
            jsonGenerator.writeStringField("uri", "http://schema.org/url");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeStringField("@type", "http://schema.org/Category");
            jsonGenerator.writeNumberField("id",cat.get(i).getId());
            jsonGenerator.writeStringField("uri", cat.get(i).getUri());
            jsonGenerator.writeStringField("name", cat.get(i).getName());
            jsonGenerator.writeEndObject();
            serialized = serialized + mapper.writeValueAsString(JsonldResource.Builder.create().build(cat));
        }
        jsonGenerator.writeEndArray();
    }
}
