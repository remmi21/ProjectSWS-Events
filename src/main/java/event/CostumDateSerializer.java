package event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CostumDateSerializer extends StdSerializer<DateEv> {
    private ObjectMapper mapper = new ObjectMapper();

    public CostumDateSerializer(){
        this( null);

    }
    protected CostumDateSerializer(Class<DateEv> t) {
        super(t);
    }

    @Override
    public void serialize(DateEv dateEv, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
