package com.lucho.leetcode79.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lucho.leetcode79.graph.Graph;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

public final class Board<Value> implements Graph<Coor, Value> {
  
    private final List<List<Value>> board;
    private final Map<Value, Set<Coor>> values = new HashMap<>();

    public Board(List<List<Value>> board) {
        this.board = board;
        this.prefillValues();
    }

    private void prefillValues() {
        for (int y = 0; y < this.board.size(); y++) {
            List<Value> row = this.board.get(y);
            for (int x = 0; x < row.size(); x++) {
                Value value = row.get(x);
                Set<Coor> nodes = this.values.computeIfAbsent(value, (val) -> new HashSet<>());
                nodes.add(new Coor(x, y));
            }
        }
    }

    public int height() {
        return this.board.size();
    }

    public int length() {
        return this.board.get(0).size();
    }

    @Override
    public Set<Coor> nodesWithValue(Value value) {
        if (this.values.containsKey(value)) {
            return unmodifiableSet(this.values.get(value));
        }
        return emptySet();
    }

    @Override
    public Value get(Coor coor) {
        return this.board.get(coor.y()).get(coor.x());
    }

    @Override
    public Map<Coor, Value> adjacentsOf(Coor coor) {
        int x = coor.x();
        int y = coor.y();
        Map<Coor, Value> nodes = new LinkedHashMap<>(4);
        if (y > 0) {
            nodes.put(new Coor(x, y - 1), this.board.get(y - 1).get(x));
        }
        List<Value> lane = this.board.get(y);
        if (x > 0) {
            nodes.put(new Coor(x - 1, y), lane.get(x - 1));
        }
        if (x < lane.size() - 1) {
            nodes.put(new Coor(x + 1, y), lane.get(x + 1));
        }
        if (y < this.board.size() - 1) {
            nodes.put(new Coor(x, y + 1), this.board.get(y + 1).get(x));
        }
        return unmodifiableMap(nodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < board.size(); y++) {
            List<Value> row = board.get(y);
            sb.append("Row[").append(y).append("]: ");
            for (int x = 0; x < row.size(); x++) {
                Value value = row.get(x);
                sb.append(value != null ? value.toString() : '?');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}