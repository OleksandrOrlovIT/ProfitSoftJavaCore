package orlov.profit.soft.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A supplier class that provides a stream of objects of a given type from a JSON array in an input stream.
 *
 * @param <T> The type of objects in the JSON array.
 */
public class InputStreamJsonArrayStreamDataSupplier<T> implements Supplier<Stream<T>> {

    private ObjectMapper mapper = new ObjectMapper();
    private JsonParser jsonParser;
    private Class<T> type;
    private InputStream data;

    /**
     * Constructs a new InputStreamJsonArrayStreamDataSupplier.
     *
     * @param type The class of objects in the JSON array.
     * @param data The input stream containing JSON data.
     * @throws IOException If an I/O error occurs while creating the JSON parser.
     */
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

    /**
     * Retrieves a stream of objects from the JSON array in the input stream.
     *
     * @return A stream of objects.
     */
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