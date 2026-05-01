package com.lucho.leetcode79;

import java.util.ArrayList;
import java.util.List;

import com.lucho.leetcode79.board.Board;
import com.lucho.leetcode79.graph.Finder;

public class Solution {
    public static void main(String[] args) {
        System.exit(23);
    }

    public boolean exist(char[][] grid, String word) {
        Board<Character> board = new Board<>(toCharacterGrid(grid));
        return new Finder<>(board, toCharacterList(word)).containsWord();
    }

    private Character[][] toCharacterGrid(char[][] grid) {
        Character[][] characterGrid = new Character[grid.length][];
        for (int y = 0; y < grid.length; y++) {
            characterGrid[y] = new Character[grid[y].length];
            for (int x = 0; x < grid[y].length; x++) {
                characterGrid[y][x] = grid[y][x];
            }
        }
        return characterGrid;
    }

    private List<Character> toCharacterList(String word) {
        List<Character> characters = new ArrayList<>(word.length());
        for (int i = 0; i < word.length(); i++) {
            characters.add(word.charAt(i));
        }
        return characters;
    }
}
