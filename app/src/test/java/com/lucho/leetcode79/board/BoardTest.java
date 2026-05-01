package com.lucho.leetcode79.board;

import com.lucho.leetcode79.graph.Node;

import org.junit.jupiter.api.Test;

import java.util.Set;

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
    void returnsNodeAtCoordinate() {
        Board<Character> board = sampleBoard();

        assertEquals(new Node<>(new Coor(0, 0), 'A'), board.nodeAt(new Coor(0, 0)));
        assertEquals(new Node<>(new Coor(2, 1), 'C'), board.nodeAt(new Coor(2, 1)));
    }

    @Test
    void returnsAllNodesWithMatchingValue() {
        Board<Character> board = sampleBoard();

        assertEquals(Set.of(
            new Node<>(new Coor(0, 0), 'A'),
            new Node<>(new Coor(1, 1), 'A')
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
        Set<Node<Coor, Character>> nodes = board.nodesWithValue('A');

        assertThrows(UnsupportedOperationException.class, () -> nodes.add(new Node<>(new Coor(2, 0), 'A')));
    }

    @Test
    void returnsAdjacentNodesForMiddleCoordinate() {
        Board<Character> board = new Board<>(new Character[][] {
            { 'A', 'B', 'C' },
            { 'D', 'E', 'F' },
            { 'G', 'H', 'I' }
        });

        assertEquals(Set.of(
            new Node<>(new Coor(1, 0), 'B'),
            new Node<>(new Coor(0, 1), 'D'),
            new Node<>(new Coor(2, 1), 'F'),
            new Node<>(new Coor(1, 2), 'H')
        ), board.adjacentsOf(new Coor(1, 1)));
    }

    @Test
    void returnsAdjacentNodesForCornerCoordinate() {
        Board<Character> board = sampleBoard();

        assertEquals(Set.of(
            new Node<>(new Coor(1, 0), 'B'),
            new Node<>(new Coor(0, 1), 'D')
        ), board.adjacentsOf(new Coor(0, 0)));
    }

    @Test
    void adjacentsOfIsUnmodifiable() {
        Board<Character> board = sampleBoard();
        Set<Node<Coor, Character>> adjacents = board.adjacentsOf(new Coor(0, 0));

        assertThrows(UnsupportedOperationException.class, () -> adjacents.clear());
    }

    @Test
    void toStringShowsEachRow() {
        Board<Character> board = sampleBoard();

        assertEquals("Row[0]: ABC\nRow[1]: DAC\n", board.toString());
    }

    private Board<Character> sampleBoard() {
        return new Board<>(new Character[][] {
            { 'A', 'B', 'C' },
            { 'D', 'A', 'C' }
        });
    }
}
