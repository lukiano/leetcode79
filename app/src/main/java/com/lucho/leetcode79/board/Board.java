package com.lucho.leetcode79.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jspecify.annotations.NonNull;

import com.lucho.leetcode79.graph.Graph;
import com.lucho.leetcode79.graph.Node;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public final class Board<Value> implements Graph<Coor, Value> {
  
    private List<Row<Value>> board;
    private Map<Value, Set<Node<Coor, Value>>> values = new HashMap<>();

    public Board(@NonNull Value @NonNull [] @NonNull [] board) {
        this.board = new ArrayList<Row<Value>>(board.length);
        for (int y = 0; y < board.length; y++) {
            Value[] row = board[y];
            Row<Value> newRow = new Row<>(y, row.length);
            for (int x = 0; x < row.length; x++) {
                Value value = row[x];
                Coor coor = new Coor(x, y);
                Node<Coor, Value> cell = new Node<>(coor, value);
                if (this.values.containsKey(value)) {
                    this.values.get(value).add(cell);
                } else {
                    Set<Node<Coor, Value>> cells = new HashSet<>();
                    cells.add(cell);
                    this.values.put(value, cells);
                }
                newRow.set(x, value);
            }
            this.board.add(newRow);
        }
    }

    public int height() {
        return this.board.size();
    }

    public int length() {
        return this.board.get(0).length();
    }

    @Override
    public Set<Node<Coor, Value>> nodesWithValue(Value value) {
        if (this.values.containsKey(value)) {
            return unmodifiableSet(this.values.get(value));
        }
        return emptySet();
    }

    @Override
    public Node<Coor, Value> nodeAt(Coor coor) {
        return this.board.get(coor.y()).at(coor.x());
    }

    @Override
    public Set<Node<Coor, Value>> adjacentsOf(Coor coor) {
        int x = coor.x();
        int y = coor.y();
        Set<Node<Coor, Value>> cells = new HashSet<>(4);
        if (y > 0) {
            cells.add(this.board.get(y - 1).at(x));
        }
        Row<Value> lane = this.board.get(y);
        if (x > 0) {
            cells.add(lane.at(x - 1));
        }
        if (x < lane.length() - 1) {
            cells.add(lane.at(x + 1));
        }
        if (y < this.board.size() - 1) {
            cells.add(this.board.get(y + 1).at(x));
        }
        return unmodifiableSet(cells);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Row<Value> row : board) {
            sb.append(row).append('\n');
        }
        return sb.toString();
    }
}