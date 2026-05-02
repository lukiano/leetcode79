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

    private List<List<Character>> toCharacterGrid(char[][] grid) {
        List<List<Character>> characterGrid = new ArrayList<List<Character>>(grid.length);
        for (char[] row : grid) {
            List<Character> newRow = new ArrayList<>(row.length);
            for (char value : row) {
                newRow.add(value);
            }
            characterGrid.add(newRow);
        }
        return characterGrid;
    }

    private List<Character> toCharacterList(CharSequence word) {
        List<Character> characters = new ArrayList<>(word.length());
        for (int i = 0; i < word.length(); i++) {
            characters.add(word.charAt(i));
        }
        return characters;
    }
}
