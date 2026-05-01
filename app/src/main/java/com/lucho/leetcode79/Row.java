package com.lucho.leetcode79;

import java.util.Arrays;
import java.util.Objects;

public class Row {

    private Cell[] data;
    private int index;

    public Row(int index, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid row length");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Invalid row index");
        }
        this.index = index;
        this.data = new Cell[length];
    }

    public void set(int pos, char letter) {
        if (pos < 0 || pos >= this.data.length) {
            throw new IllegalArgumentException("Invalid row position");
        }
        Coor coor = new Coor(pos, index);
        this.data[pos] = new Cell(coor, letter);
    }
    
    public Cell at(int pos) { 
        if (pos < 0 || pos >= this.data.length) {
            throw new IllegalArgumentException("Invalid row position");
        }
        return this.data[pos];
    }

    public int length() {
        return this.data.length;
    }

    public char letterAt(int pos) {
        return this.at(pos).letter();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Row[").append(index).append("]: ");
        for (Cell cell : data) {
            sb.append(cell != null ? cell.letter() : '?');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Row other)) return false;
        return index == other.index && Arrays.equals(data, other.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, Arrays.hashCode(data));
    }

}
