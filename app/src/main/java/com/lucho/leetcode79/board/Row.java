package com.lucho.leetcode79.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.NonNull;

import com.lucho.leetcode79.graph.Node;

final class Row<Value> {

    private final List<Node<Coor, Value>> data;
    private final int index;

    public Row(int index, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid row length");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Invalid row index");
        }
        this.index = index;
        this.data = new ArrayList<Node<Coor, Value>>(length);
        for (int i = 0; i < length; i++) {
            this.data.add(null);
        }
    }

    public void set(int pos, @NonNull Value value) {
        if (pos < 0 || pos >= this.data.size()) {
            throw new IllegalArgumentException("Invalid row position");
        }
        Coor coor = new Coor(pos, index);
        this.data.set(pos, new Node<Coor, Value>(coor, value));
    }
    
    public Node<Coor, Value> at(int pos) { 
        if (pos < 0 || pos >= this.data.size()) {
            throw new IllegalArgumentException("Invalid row position");
        }
        return this.data.get(pos);
    }

    public int length() {
        return this.data.size();
    }

    public Value valueAt(int pos) {
        return this.at(pos).value();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Row[").append(index).append("]: ");
        for (Node<Coor, Value> cell : data) {
            sb.append(cell != null ? cell.value().toString() : '?');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Row other)) return false;
        return index == other.index && data.equals(other.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, data.hashCode());
    }
}