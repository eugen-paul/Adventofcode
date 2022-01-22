package net.eugenpaul.adventofcode.y2016.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    void testFromString() {
        Room room = Room.fromString("aaaaa-bbb-z-y-x-123[abxyz]");
        assertEquals("aaaaa-bbb-z-y-x", room.getEncryptedName());
        assertEquals(123, room.getId());
        assertEquals("abxyz", room.getChecksum());
    }

    @Test
    void testIsReal() {
        Room room = new Room("aaaaa-bbb-z-y-x", 123, "abxyz");
        assertTrue(room.isReal());
    }

    @Test
    void testDecrypt() {
        Room room = new Room("qzmt-zixmtkozy-ivhz", 343, "----");
        assertEquals("very encrypted name", room.getDecryptedName());
    }
}
