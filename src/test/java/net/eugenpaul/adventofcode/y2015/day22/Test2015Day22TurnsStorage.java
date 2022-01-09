package net.eugenpaul.adventofcode.y2015.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day22TurnsStorage
 */
class Test2015Day22TurnsStorage {

    @Test
    void test2015Day22TurnsStorageAsc() {
        TurnsStorage storage = new TurnsStorage();

        TurnData data1 = mock(TurnData.class);
        when(data1.getTotalManaCost()).thenReturn(1);

        TurnData data2 = mock(TurnData.class);
        when(data2.getTotalManaCost()).thenReturn(2);

        TurnData data3 = mock(TurnData.class);
        when(data3.getTotalManaCost()).thenReturn(3);

        assertNull(storage.getLowestTurn());

        storage.addTurnData(data1);
        storage.addTurnData(data2);
        storage.addTurnData(data3);

        assertEquals(data1, storage.getLowestTurn());
        assertEquals(data2, storage.getLowestTurn());
        assertEquals(data3, storage.getLowestTurn());
        assertNull(storage.getLowestTurn());
    }

    @Test
    void test2015Day22TurnsStorageDesc() {
        TurnsStorage storage = new TurnsStorage();

        TurnData data1 = mock(TurnData.class);
        when(data1.getTotalManaCost()).thenReturn(1);

        TurnData data2 = mock(TurnData.class);
        when(data2.getTotalManaCost()).thenReturn(2);

        TurnData data3 = mock(TurnData.class);
        when(data3.getTotalManaCost()).thenReturn(3);

        assertNull(storage.getLowestTurn());

        storage.addTurnData(data3);
        storage.addTurnData(data2);
        storage.addTurnData(data1);

        assertEquals(data1, storage.getLowestTurn());
        assertEquals(data2, storage.getLowestTurn());
        assertEquals(data3, storage.getLowestTurn());
        assertNull(storage.getLowestTurn());
    }

}