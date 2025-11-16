package net.eugenpaul.adventofcode.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JacksonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode toTree(String data) {
        try {
            return mapper.readTree(data);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("can't read the tree.", e);
        }
    }
}
