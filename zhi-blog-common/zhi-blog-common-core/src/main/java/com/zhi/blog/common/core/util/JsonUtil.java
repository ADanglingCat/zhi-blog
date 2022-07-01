package com.zhi.blog.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Ted
 * @date 2022/6/16
 **/
public class JsonUtil {
    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    private JsonUtil() {
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JSON_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JSON_MAPPER.registerModule(new Jdk8Module());
        JSON_MAPPER.registerModule(new JavaTimeModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        );
    }

    public static JsonMapper getObjectMapper() {
        return JSON_MAPPER;
    }

    public static String toJsonString(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            CoreUtil.error(e);
        }
        return getJsonObject().toString();
    }

    public static <T> T toJavaObject(String jsonString, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public static <T> T toJavaObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public static <T> List<T> toJavaList(String jsonString, Class<T> clazz) {
        try {
            return getObjectMapper().readerForListOf(clazz).readValue(jsonString);
        } catch (JsonProcessingException e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public static ObjectNode toJsonObject(String jsonString) {
        if (!CoreUtil.isEmpty(jsonString)) {
            try {
                return ((ObjectNode) getObjectMapper().readTree(jsonString));
            } catch (JsonProcessingException e) {
                CoreUtil.error(e);
            }
        }
        return getJsonObject();
    }

    public static ObjectNode put(String key, Object value) {
        ObjectNode objectNode = getJsonObject();
        if (!CoreUtil.isEmpty(value)) {
            objectNode.putPOJO(key, value);
        }
        return objectNode;
    }

    public static ObjectNode put(ObjectNode objectNode, String key, Object value) {
        if (!CoreUtil.isEmpty(value)) {
            objectNode.putPOJO(key, value);
        }
        return objectNode;
    }

    public static String getString(ObjectNode objectNode, String key) {
        try {
            return objectNode.get(key).asText();
        } catch (Exception e) {
            return "";
        }
    }

    public static ObjectNode getJsonObject() {
        return getObjectMapper().createObjectNode();
    }

    public static ArrayNode getJsonArray() {
        return getObjectMapper().createArrayNode();
    }

}
