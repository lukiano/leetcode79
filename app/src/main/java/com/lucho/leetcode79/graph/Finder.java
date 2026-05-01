package com.lucho.leetcode79.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Finder<Id, Value> {
    private final Graph<Id, Value> graph;
    private final List<Value> sequence;
    private Boolean found = null;

    // Cache of nodes that contain a given value.
    private final Map<Value, Set<Node<Id, Value>>> nodesByValue = new HashMap<>();

    public Finder(Graph<Id, Value> graph, List<Value> sequence) {
        this.graph = graph;
        this.sequence = sequence;
    }

    public boolean containsWord() {
        if (found == null) {
            if (sequence.isEmpty()) {
                found = true;
            } else if (!this.assessWord()) {
                found = false;
            } else {
                found = this.findWord();
            }
        }
        return found;
    }

    private boolean assessWord() {
        // Before walking the graph, reject words that require more instances of
        // a value than the graph contains. This avoids expensive DFS on boards
        // where the answer is impossible from the start.
        Map<Value, Integer> requiredValues = new HashMap<>();
        for (Value value : this.sequence) {
            requiredValues.merge(value, 1, Integer::sum);
        }

        for (Map.Entry<Value, Integer> required : requiredValues.entrySet()) {
            Set<Node<Id, Value>> available = this.nodesWithValue(required.getKey());
            if (available.size() < required.getValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean findWord() {
        // A path can be matched forward or backward. Starting from the rarer
        // endpoint usually reduces the number of DFS branches dramatically.
        List<Value> searchSequence = this.sequenceWithFewestStartingCandidates();
        Value firstValue = searchSequence.get(0);
        Set<Node<Id, Value>> candidates = this.nodesWithValue(firstValue);
        Path<Id> path = new Path<>();
        return candidates.stream().anyMatch(node -> this.sequenceAt(searchSequence, 0, node.id(), path));
    }

    private List<Value> sequenceWithFewestStartingCandidates() {
        Value firstValue = sequence.get(0);
        Value lastValue = sequence.get(sequence.size() - 1);
        int firstCandidates = this.nodesWithValue(firstValue).size();
        int lastCandidates = this.nodesWithValue(lastValue).size();
        if (lastCandidates >= firstCandidates) {
            return sequence;
        }

        List<Value> reversed = new ArrayList<>(sequence);
        Collections.reverse(reversed);
        return reversed;
    }

    private Set<Node<Id, Value>> nodesWithValue(Value value) {
        return this.nodesByValue.computeIfAbsent(value, this.graph::nodesWithValue);
    }

    private boolean sequenceAt(List<Value> sequence, int index, Id id, Path<Id> path) {
        if (path.contains(id)) {
            return false;
        }
        Value value = sequence.get(index);
        Node<Id, Value> node = this.graph.nodeAt(id);
        if (node == null || !Objects.equals(node.value(), value)) {
            return false;
        }
        if (index == sequence.size() - 1) {
            return true;
        }
        Path<Id> newPath = path.add(id);
        int newIndex = index + 1;
  
        Set<Node<Id, Value>> adjacents = this.graph.adjacentsOf(id);
        return adjacents.stream().anyMatch(adj -> this.sequenceAt(sequence, newIndex, adj.id(), newPath));
    }
}
