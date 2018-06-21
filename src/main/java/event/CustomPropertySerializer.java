package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

import java.io.IOException;

public class CustomPropertySerializer extends StdSerializer<Properties> {
    private ObjectMapper mapper = new ObjectMapper();

public CustomPropertySerializer(){ this( null); }
protected CustomPropertySerializer(Class<Properties> t) {
        super(t);
        }

@Override
public void serialize(Properties prop, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("@context");
        jsonGenerator.writeStringField("group_registrations_allowed", "http://schema.org/group_registrations_allowed ");
        jsonGenerator.writeStringField("groupe_size", "http://schema.org/groupe_size");
        jsonGenerator.writeStringField("active", "http://schema.org/active");
        jsonGenerator.writeStringField("members_only", "http://schema.org/members_only");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("@type", "http://schema.org/Properties");
        jsonGenerator.writeBooleanField("group_registrations_allowed", prop.isGroup_registrations_allowed());
        jsonGenerator.writeNumberField("groupe_size", prop.getGroupe_size());
        jsonGenerator.writeBooleanField("active", prop.isActive());
        jsonGenerator.writeBooleanField("members_only", prop.isMembers_only());
        jsonGenerator.writeEndObject();
        String serialized = mapper.writeValueAsString(JsonldResource.Builder.create().build(prop));

        }
}
