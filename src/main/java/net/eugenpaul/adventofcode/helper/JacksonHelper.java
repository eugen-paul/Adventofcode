package net.eugenpaul.adventofcode.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JacksonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode toTree(String data) {
        try {
            return mapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("can't read the tree.", e);
        }
    }
}
