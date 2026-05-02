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
            Set<Id> available = this.graph.nodesWithValue(required.getKey());
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
        Set<Id> candidates = this.graph.nodesWithValue(firstValue);
        List<Id> path = new ArrayList<>();
        return candidates.stream().anyMatch(id -> this.sequenceAt(searchSequence, 0, id, path));
    }

    private List<Value> sequenceWithFewestStartingCandidates() {
        Value firstValue = sequence.get(0);
        Value lastValue = sequence.get(sequence.size() - 1);
        int firstCandidates = this.graph.nodesWithValue(firstValue).size();
        int lastCandidates = this.graph.nodesWithValue(lastValue).size();
        if (lastCandidates >= firstCandidates) {
            return sequence;
        }

        List<Value> reversed = new ArrayList<>(sequence);
        Collections.reverse(reversed);
        return reversed;
    }

    private boolean sequenceAt(List<Value> sequence, int index, Id id, List<Id> path) {
        if (path.contains(id)) {
            return false;
        }
        Value expectedValue = sequence.get(index);
        Value actualValue = this.graph.get(id);
        if (actualValue == null || !Objects.equals(actualValue, expectedValue)) {
            return false;
        }
        if (index == sequence.size() - 1) {
            return true;
        }
        ArrayList<Id> newPath = new ArrayList<>(path);
        newPath.add(id);

        int newIndex = index + 1;
  
        Set<Id> adjacents = this.graph.adjacentsOf(id);
        return adjacents.stream().anyMatch(adj -> this.sequenceAt(sequence, newIndex, adj, newPath));
    }
}
