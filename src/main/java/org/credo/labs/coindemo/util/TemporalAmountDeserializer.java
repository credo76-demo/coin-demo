package org.credo.labs.coindemo.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import org.apache.commons.lang3.StringUtils;

public class TemporalAmountDeserializer extends JsonDeserializer<TemporalAmount> {
    static TemporalAmountDeserializer INSTANCE = new TemporalAmountDeserializer();

    @Override
    public TemporalAmount deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        String trimToNull = StringUtils.trimToNull(parser.getText());

        if (null == trimToNull) return null;

        try {
            return Period.parse(trimToNull);
        } catch (DateTimeParseException ex) {
            return Duration.parse(trimToNull);
        }
    }
}
