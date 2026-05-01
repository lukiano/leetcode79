package com.lucho.leetcode79.graph;

import java.util.List;
import java.util.Set;

public final class Finder<Id, Value> {
    private final Graph<Id, Value> graph;
    private final List<Value> sequence;
    private Boolean found = null;

    public Finder(Graph<Id, Value> graph, List<Value> sequence) {
        this.graph = graph;
        this.sequence = sequence;
    }

    public boolean containsWord() {
        if (found == null) {
            found = this.findWord();
        }
        return found;
    }

    private boolean findWord() {
        if (sequence.isEmpty()) {
            return true;
        }
        Value firstValue = sequence.get(0);
        Set<Node<Id, Value>> candidates = this.graph.nodesWithValue(firstValue);
        Path<Id> path = new Path<>();
        return candidates.stream().anyMatch(node -> this.sequenceAt(sequence, 0, node.id(), path));
    }

    private boolean sequenceAt(List<Value> sequence, int index, Id id, Path<Id> path) {
        System.out.println("WordAt: word is " + String.valueOf(sequence) + " - index is " + index + " - current coordinates are " + id);
        if (path.contains(id)) {
            // System.out.println("Coordinates " + coor + " already in trail. Skipping");
            return false;
        }
        Value value = sequence.get(index);
        Node<Id, Value> node = this.graph.nodeAt(id);
        if (node == null || !node.value().equals(value)) {
            // System.out.println("Expected letter " + letter + " but " + this.board.letterAt(coor) + " was found at coordinates " + coor);
            return false;
        }
        if (index == sequence.size() - 1) {
            // System.out.println("Word found!");
            return true;
        }
        Path<Id> newPath = path.add(id);
        int newIndex = index + 1;
  
        Set<Node<Id, Value>> adjacents = this.graph.adjacentsOf(id);
        return adjacents.stream().anyMatch(adj -> this.sequenceAt(sequence, newIndex, adj.id(), newPath));
    }
}
