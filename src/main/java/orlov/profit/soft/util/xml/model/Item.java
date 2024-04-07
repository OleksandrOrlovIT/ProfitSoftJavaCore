package orlov.profit.soft.util.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an item in XML format, with a value and a count.
 */
@JacksonXmlRootElement(localName = "item")
@Setter
@Getter
@AllArgsConstructor
public class Item {

    /**
     * The value of the item.
     */
    @JacksonXmlProperty(localName = "value")
    private String value;

    /**
     * The count of the item.
     */
    @JacksonXmlProperty(localName = "count")
    private int count;
}
