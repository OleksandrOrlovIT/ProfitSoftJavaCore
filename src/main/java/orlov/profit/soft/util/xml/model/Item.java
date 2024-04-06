package orlov.profit.soft.util.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JacksonXmlRootElement(localName = "item")
@Setter
@Getter
@AllArgsConstructor
public class Item {

    @JacksonXmlProperty(localName = "value")
    private String value;

    @JacksonXmlProperty(localName = "count")
    private int count;
}
