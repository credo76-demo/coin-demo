package org.credo.labs.coindemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.springframework.data.util.Pair;

import static org.credo.labs.coindemo.core.exception.CoreErrorCodes.READ_JSON_FAILED;
import static org.credo.labs.coindemo.util.DateUtils.DATETIME_DB_PATTERN;
import static org.credo.labs.coindemo.util.DateUtils.DATETIME_ISO_PATTERN;
import static org.credo.labs.coindemo.util.DateUtils.DATE_ISO_PATTERN;

public class JsonUtils {
    public static ObjectMapper OBJECT_MAPPER = getObjectMapper();


    static {
        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(ObjectMapper.class, OBJECT_MAPPER);
        OBJECT_MAPPER.setInjectableValues(injectableValues);
    }


    public static ObjectMapper getObjectMapper() {
        return configObjectMapper(false, false, DATE_ISO_PATTERN, DATETIME_DB_PATTERN);
    }

    public static ObjectMapper configObjectMapper(Boolean excludeNull
            , Boolean isAdmin
            , String dateFormatPattern
            , String dateTimeFormatPattern
    ) {
        if (null == dateFormatPattern) dateFormatPattern = DATE_ISO_PATTERN;
        if (null == dateTimeFormatPattern) dateTimeFormatPattern = DATETIME_ISO_PATTERN;

        ObjectMapper objectMapper = new ObjectMapper();

        if (excludeNull) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        /* simple date format */
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormatPattern));

        /* Hibernate5 Module, for N+1 Query */
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        objectMapper.registerModule(hibernate5Module);

        /* JavaTimeModule()
            JavaTimeModule javaTimeModule = new JavaTimeModule();
        */

        /* Time Module */
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatPattern);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatPattern);

        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        module.addSerializer(LocalTime.class, new LocalTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(dateTimeFormatter));

        module.addSerializer(Period.class, new ToStringSerializer(Period.class));
        module.addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);

        module.addSerializer(Duration.class, DurationSerializer.INSTANCE);
        module.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);

        module.addSerializer(TemporalAmount.class, new ToStringSerializer(TemporalAmount.class));
        module.addDeserializer(TemporalAmount.class, TemporalAmountDeserializer.INSTANCE);
        objectMapper.registerModule(module);

        return objectMapper;
    }

    public static <T> T asObject(String src, Class<T> handledType) {
        try {
            return OBJECT_MAPPER.readValue(src, handledType);
        } catch (JsonProcessingException ex) {
            throw new ApiException(ex, READ_JSON_FAILED, Pair.of("reason", ex.getMessage()));
        }
    }

    /**
     * Deserialize to generic object.
     *
     * @param src
     * @param handledType
     */
    public static <T> T asObject(String src, TypeReference<T> handledType) {
        try {
            return OBJECT_MAPPER.readValue(src, handledType);
        } catch (JsonProcessingException ex) {
            throw new ApiException(ex, READ_JSON_FAILED, Pair.of("reason", ex.getMessage()));
        }
    }

    /**
     * Write object to json string;
     *
     * @param obj
     */
    public static String writeString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
