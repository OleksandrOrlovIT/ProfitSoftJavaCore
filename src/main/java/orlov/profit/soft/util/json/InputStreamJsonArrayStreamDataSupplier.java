package orlov.profit.soft.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InputStreamJsonArrayStreamDataSupplier<T> implements Supplier<Stream<T>> {

    private ObjectMapper mapper = new ObjectMapper();
    private JsonParser jsonParser;
    private Class<T> type;
    private InputStream data;

    public InputStreamJsonArrayStreamDataSupplier(Class<T> type, InputStream data) throws IOException {
        this.type = type;
        this.data = data;

        jsonParser = mapper.getFactory().createParser(data);
        jsonParser.setCodec(mapper);
        JsonToken token = jsonParser.nextToken();
        if (JsonToken.START_ARRAY.equals(token)) {
            token = jsonParser.nextToken();
        }
        if (!JsonToken.START_OBJECT.equals(token)) {
            throw new RuntimeException("Can't get any JSON object from input " + data);
        }
    }

    @Override
    public Stream<T> get() {
        try {
            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(jsonParser.readValuesAs(type), 0), true)
                    .onClose(() -> {
                        try {
                            data.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}