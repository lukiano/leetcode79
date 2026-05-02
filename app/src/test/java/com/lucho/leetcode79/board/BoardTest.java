package com.lucho.leetcode79.board;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void exposesDimensions() {
        Board<Character> board = sampleBoard();

        assertEquals(2, board.height());
        assertEquals(3, board.length());
    }

    @Test
    void returnsValueAtCoordinate() {
        Board<Character> board = sampleBoard();

        assertEquals('A', board.get(new Coor(0, 0)));
        assertEquals('C', board.get(new Coor(2, 1)));
    }

    @Test
    void returnsAllNodesWithMatchingValue() {
        Board<Character> board = sampleBoard();

        assertEquals(Set.of(
            new Coor(0, 0),
            new Coor(1, 1)
        ), board.nodesWithValue('A'));
    }

    @Test
    void returnsEmptySetWhenValueIsMissing() {
        Board<Character> board = sampleBoard();

        assertTrue(board.nodesWithValue('Z').isEmpty());
    }

    @Test
    void nodesWithValueIsUnmodifiable() {
        Board<Character> board = sampleBoard();
        Set<Coor> nodes = board.nodesWithValue('A');

        assertThrows(UnsupportedOperationException.class, () -> nodes.add(new Coor(2, 0)));
    }

    @Test
    void returnsAdjacentNodesForMiddleCoordinate() {
        Board<Character> board = new Board<>(List.of(
            List.of('A', 'B', 'C'),
            List.of('D', 'E', 'F'),
            List.of('G', 'H', 'I')
        ));

        assertEquals(Map.of(
            new Coor(1, 0), 'B',
            new Coor(0, 1), 'D',
            new Coor(2, 1), 'F',
            new Coor(1, 2), 'H'
        ), board.adjacentsOf(new Coor(1, 1)));
    }

    @Test
    void returnsAdjacentNodesForCornerCoordinate() {
        Board<Character> board = sampleBoard();

        assertEquals(Map.of(
            new Coor(1, 0), 'B',
            new Coor(0, 1), 'D'
        ), board.adjacentsOf(new Coor(0, 0)));
    }

    @Test
    void adjacentsOfIsUnmodifiable() {
        Board<Character> board = sampleBoard();
        Map<Coor, Character> adjacents = board.adjacentsOf(new Coor(0, 0));

        assertThrows(UnsupportedOperationException.class, () -> adjacents.clear());
    }

    @Test
    void toStringShowsEachRow() {
        Board<Character> board = sampleBoard();

        assertEquals("Row[0]: ABC\nRow[1]: DAC\n", board.toString());
    }

    private Board<Character> sampleBoard() {
        List.of('A');
        return new Board<>(List.of(
            List.of('A', 'B', 'C'),
            List.of('D', 'A', 'C')
        ));
    }
}
