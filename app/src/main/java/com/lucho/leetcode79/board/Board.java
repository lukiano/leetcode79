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
  
    private List<List<Node<Coor, Value>>> board;
    private Map<Value, Set<Node<Coor, Value>>> values = new HashMap<>();

    public Board(@NonNull Value @NonNull [] @NonNull [] board) {
        this.board = new ArrayList<List<Node<Coor, Value>>>(board.length);
        for (int y = 0; y < board.length; y++) {
            Value[] row = board[y];
            List<Node<Coor, Value>> newRow = new ArrayList<>(row.length);
            for (int x = 0; x < row.length; x++) {
                Value value = row[x];
                Node<Coor, Value> node = new Node<>(new Coor(x, y), value);
                Set<Node<Coor, Value>> nodes = this.values.computeIfAbsent(value, (val) -> new HashSet<>());
                nodes.add(node);
                newRow.add(node);
            }
            this.board.add(newRow);
        }
    }

    public int height() {
        return this.board.size();
    }

    public int length() {
        return this.board.get(0).size();
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
        return this.board.get(coor.y()).get(coor.x());
    }

    @Override
    public Set<Node<Coor, Value>> adjacentsOf(Coor coor) {
        int x = coor.x();
        int y = coor.y();
        Set<Node<Coor, Value>> nodes = new HashSet<>(4);
        if (y > 0) {
            nodes.add(this.board.get(y - 1).get(x));
        }
        List<Node<Coor, Value>> lane = this.board.get(y);
        if (x > 0) {
            nodes.add(lane.get(x - 1));
        }
        if (x < lane.size() - 1) {
            nodes.add(lane.get(x + 1));
        }
        if (y < this.board.size() - 1) {
            nodes.add(this.board.get(y + 1).get(x));
        }
        return unmodifiableSet(nodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (List<Node<Coor, Value>> row : board) {
            sb.append("Row[").append(index).append("]: ");
            for (Node<Coor, Value> node : row) {
                sb.append(node != null ? node.value().toString() : '?');
            }
            sb.append('\n');
            index++;
        }
        return sb.toString();
    }
}