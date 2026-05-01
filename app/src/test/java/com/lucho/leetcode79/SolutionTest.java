package com.lucho.leetcode79;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    @Test void example1() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'} };
        assertTrue(classUnderTest.exist(board, "ABCCED"));
    }

    @Test void example2() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'} };
        assertTrue(classUnderTest.exist(board, "SEE"));
    }

    @Test void example3() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'} };
        assertFalse(classUnderTest.exist(board, "ABCB"));
    }

    @Test void example4() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'a','b'}, {'c','d'} };
        assertFalse(classUnderTest.exist(board, "abcd"));
    }

    @Test void example5() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'a'} };
        assertTrue(classUnderTest.exist(board, "a"));
    }

    @Test void example6() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'a'} };
        assertFalse(classUnderTest.exist(board, "ab"));
    }

    @Test void example7() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'a','b'}, {'c','d'} };
        assertTrue(classUnderTest.exist(board, "acdb"));
    }

     @Test void example8() {
        Solution classUnderTest = new Solution();
        char[][] board = { {'A','A','A','A','A','A'}, {'A','A','A','A','A','A'}, {'A','A','A','A','A','A'}, {'A','A','A','A','A','A'}, {'A','A','A','A','A','B'}, {'A','A','A','A','B','A'} };
        assertFalse(classUnderTest.exist(board, "AAAAAAAAAAAAABB"));
    }
}
