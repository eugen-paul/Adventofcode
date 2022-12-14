package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;

class JacksonHelperTest {

    @Test
    void testToTree() {
        var root = JacksonHelper.toTree("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        assertNotNull(root);
        System.out.println(root);

        assertTrue(root instanceof ArrayNode);

        var childs = (ArrayNode) root;
        for (var child : childs) {
            System.out.println(child);
        }

        assertTrue(childs.get(0) instanceof IntNode);
        assertTrue(childs.get(1) instanceof ArrayNode);
        assertTrue(childs.get(2) instanceof IntNode);
        assertTrue(childs.get(3) instanceof IntNode);
    }

    @Test
    void testToTree2() {
        var root = JacksonHelper.toTree("[1,[2,[3,[4,[5,6,7]]]],\"text\",true,2.5]");

        assertNotNull(root);
        System.out.println(root);

        assertTrue(root instanceof ArrayNode);

        var childs = (ArrayNode) root;
        for (var child : childs) {
            System.out.println(child);
        }

        assertTrue(childs.get(0) instanceof IntNode);
        assertTrue(childs.get(1) instanceof ArrayNode);
        assertTrue(childs.get(2) instanceof TextNode);
        assertTrue(childs.get(3) instanceof BooleanNode);
        assertTrue(childs.get(4) instanceof DoubleNode);
    }
}
