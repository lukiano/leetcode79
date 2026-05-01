package com.lucho.leetcode79;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Board {
  
    private Row[] board;
    private Map<Character, List<Cell>> letters = new HashMap<>();

    public Board(char[][] board) {
        this.board = new Row[board.length];
        for (int y = 0; y < board.length; y++) {
            char[] row = board[y];
            Row newRow = new Row(y, row.length);
            for (int x = 0; x < row.length; x++) {
                char letter = row[x];
                Coor coor = new Coor(x, y);
                Cell cell = new Cell(coor, letter);
                if (this.letters.containsKey(letter)) {
                    this.letters.get(letter).add(cell);
                } else {
                    List<Cell> list = new LinkedList<>();
                    list.add(cell);
                    this.letters.put(letter, list);
                }
                newRow.set(x, letter);
            }
            this.board[y] = newRow;
        }
    }

    public int height() {
        return this.board.length;
    }

    public int length() {
        return this.board[0].length();
    }

    public List<Cell> cellsOf(char letter) {
        if (this.letters.containsKey(letter)) {
            return unmodifiableList(this.letters.get(letter));
        }
        return emptyList();
    }

    public char letterAt(Coor coor) {
        return this.board[coor.y()].letterAt(coor.x());
    }

    public List<Cell> adjacentsOf(Coor coor) {
        int x = coor.x();
        int y = coor.y();
        List<Cell> list = new ArrayList<>(4);
        if (y > 0) {
            list.add(this.board[y - 1].at(x));
        }
        Row lane = this.board[y];
        if (x > 0) {
            list.add(lane.at(x - 1));
        }
        if (x < lane.length() - 1) {
            list.add(lane.at(x + 1));
        }
        if (y < this.board.length - 1) {
            list.add(this.board[y + 1].at(x));
        }
        return unmodifiableList(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Row row : board) {
            sb.append(row).append('\n');
        }
        return sb.toString();
    }

    public boolean containsWord(String word) {
        if (word.isEmpty()) {
            return true;
        }
        char firstLetter = word.charAt(0);
        List<Cell> candidates = this.cellsOf(firstLetter);
        return candidates.stream().anyMatch(cell -> this.wordAt(word, 0, cell.coor(), new Path<>()));
    }

    private boolean wordAt(String word, int index, Coor coor, Path<Coor> path) {
        System.out.println("WordAt: word is " + String.valueOf(word) + " - index is " + index + " - current coordinates are " + coor);
        if (path.contains(coor)) {
            System.out.println("Coordinates " + coor + " already in trail. Skipping");
            return false;
        }
        char letter = word.charAt(index);
        if (this.letterAt(coor) != letter) {
            System.out.println("Expected letter " + letter + " but " + this.letterAt(coor) + " was found at coordinates " + coor);
            return false;
        }
        if (index == word.length() - 1) {
            System.out.println("Word found!");
            return true;
        }
        Path<Coor> newPath = path.add(coor);
        int newIndex = index + 1;
  
        List<Cell> adjacents = this.adjacentsOf(coor);
        return adjacents.stream().anyMatch(cell -> this.wordAt(word, newIndex, cell.coor(), newPath));
    }

}
