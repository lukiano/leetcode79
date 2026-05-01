package com.lucho.leetcode79;

public class Solution {
    public static void main(String[] args) {
        System.exit(23);
    }

    public boolean exist(char[][] grid, String word) {
        Board board = new Board(grid);
        return board.containsWord(word);
    }
}