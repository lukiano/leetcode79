package com.lucho.leetcode79.board;

import com.lucho.leetcode79.graph.Node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RowTest {

    @Test
    void createsRowWithRequestedLength() {
        Row<Character> row = new Row<>(2, 3);

        assertEquals(3, row.length());
        assertNull(row.at(0));
        assertNull(row.at(1));
        assertNull(row.at(2));
    }

    @Test
    void rejectsInvalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Row<Character>(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Row<Character>(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Row<Character>(-1, 1));
    }

    @Test
    void setStoresNodeWithColumnAndRowCoordinates() {
        Row<Character> row = new Row<>(4, 3);

        row.set(1, 'A');

        assertEquals(new Node<>(new Coor(1, 4), 'A'), row.at(1));
        assertEquals('A', row.valueAt(1));
    }

    @Test
    void setRejectsOutOfBoundsPositions() {
        Row<Character> row = new Row<>(0, 2);

        assertThrows(IllegalArgumentException.class, () -> row.set(-1, 'A'));
        assertThrows(IllegalArgumentException.class, () -> row.set(2, 'A'));
    }

    @Test
    void atRejectsOutOfBoundsPositions() {
        Row<Character> row = new Row<>(0, 2);

        assertThrows(IllegalArgumentException.class, () -> row.at(-1));
        assertThrows(IllegalArgumentException.class, () -> row.at(2));
    }

    @Test
    void toStringShowsRowIndexAndValues() {
        Row<Character> row = new Row<>(3, 4);

        row.set(0, 'A');
        row.set(2, 'B');

        assertEquals("Row[3]: A?B?", row.toString());
    }

    @Test
    void equalsAndHashCodeUseIndexAndData() {
        Row<Character> first = new Row<>(1, 2);
        Row<Character> second = new Row<>(1, 2);
        Row<Character> differentIndex = new Row<>(2, 2);
        Row<Character> differentData = new Row<>(1, 2);

        first.set(0, 'A');
        second.set(0, 'A');
        differentIndex.set(0, 'A');
        differentData.set(1, 'A');

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, differentIndex);
        assertNotEquals(first, differentData);
    }
}
