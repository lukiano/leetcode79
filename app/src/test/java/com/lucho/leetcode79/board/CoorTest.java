package com.lucho.leetcode79.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoorTest {

    @Test
    void exposesXAndY() {
        Coor coor = new Coor(2, 3);

        assertEquals(2, coor.x());
        assertEquals(3, coor.y());
    }

    @Test
    void rejectsNegativeX() {
        assertThrows(IllegalArgumentException.class, () -> new Coor(-1, 0));
    }

    @Test
    void rejectsNegativeY() {
        assertThrows(IllegalArgumentException.class, () -> new Coor(0, -1));
    }

    @Test
    void identifiesCoordinatesInsideBoardBounds() {
        Board<Character> board = new Board<>(new Character[][] {
            { 'A', 'B', 'C' },
            { 'D', 'E', 'F' }
        });

        assertTrue(new Coor(0, 0).inBoundsFor(board));
        assertTrue(new Coor(2, 1).inBoundsFor(board));
    }

    @Test
    void identifiesCoordinatesOutsideBoardBounds() {
        Board<Character> board = new Board<>(new Character[][] {
            { 'A', 'B', 'C' },
            { 'D', 'E', 'F' }
        });

        assertFalse(new Coor(3, 0).inBoundsFor(board));
        assertFalse(new Coor(0, 2).inBoundsFor(board));
    }

    @Test
    void usesRecordValueSemantics() {
        Coor first = new Coor(1, 2);
        Coor second = new Coor(1, 2);
        Coor different = new Coor(2, 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, different);
        assertEquals("Coor[x=1, y=2]", first.toString());
    }
}
