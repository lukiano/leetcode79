package com.lucho.leetcode79.graph;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinderTest {

    @Test
    void emptySequenceIsAlwaysFound() {
        Finder<String, Character> finder = new Finder<>(new FailingGraph<>(), emptyList());

        assertTrue(finder.containsWord());
    }

    @Test
    void findsSingleValueNode() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A');

        Finder<String, Character> finder = new Finder<>(graph, List.of('A'));

        assertTrue(finder.containsWord());
    }

    @Test
    void returnsFalseWhenStartingValueIsMissing() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A');

        Finder<String, Character> finder = new Finder<>(graph, List.of('B'));

        assertFalse(finder.containsWord());
    }

    @Test
    void findsSequenceThroughAdjacentNodes() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A')
            .node("b", 'B')
            .node("c", 'C')
            .edge("a", "b")
            .edge("b", "c");

        Finder<String, Character> finder = new Finder<>(graph, List.of('A', 'B', 'C'));

        assertTrue(finder.containsWord());
    }

    @Test
    void returnsFalseWhenNextValueIsNotAdjacent() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A')
            .node("b", 'B');

        Finder<String, Character> finder = new Finder<>(graph, List.of('A', 'B'));

        assertFalse(finder.containsWord());
    }

    @Test
    void doesNotReuseSameNodeInOnePath() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A')
            .node("b", 'B')
            .edge("a", "b");

        Finder<String, Character> finder = new Finder<>(graph, List.of('A', 'B', 'A'));

        assertFalse(finder.containsWord());
    }

    @Test
    void canUseDifferentNodesWithSameValue() {
        DummyGraph graph = new DummyGraph()
            .node("a1", 'A')
            .node("b", 'B')
            .node("a2", 'A')
            .edge("a1", "b")
            .edge("b", "a2");

        Finder<String, Character> finder = new Finder<>(graph, List.of('A', 'B', 'A'));

        assertTrue(finder.containsWord());
    }

    @Test
    void returnsFalseWhenSequenceNeedsMoreValuesThanGraphContains() {
        Graph<String, Character> graph = new Graph<>() {
            @Override
            public Set<String> nodesWithValue(Character value) {
                if (Objects.equals(value, 'A')) {
                    return Set.of("a");
                }
                return emptySet();
            }

            @Override
            public Character get(String id) {
                throw new AssertionError("Impossible value counts should stop before traversal");
            }

            @Override
            public Map<String, Character> adjacentsOf(String id) {
                throw new AssertionError("Impossible value counts should stop before traversal");
            }
        };

        Finder<String, Character> finder = new Finder<>(graph, List.of('A', 'A'));

        assertFalse(finder.containsWord());
    }

    @Test
    void returnsFalseWhenGraphHasCandidateButNodeLookupIsMissing() {
        Graph<String, Character> graph = new Graph<>() {
            @Override
            public Set<String> nodesWithValue(Character value) {
                return Set.of("missing");
            }

            @Override
            public Character get(String id) {
                return null;
            }

            @Override
            public Map<String, Character> adjacentsOf(String id) {
                return emptyMap();
            }
        };

        Finder<String, Character> finder = new Finder<>(graph, List.of('A'));

        assertFalse(finder.containsWord());
    }

    private static final class DummyGraph implements Graph<String, Character> {
        private final Map<String, Character> nodes = new HashMap<>();
        private final Map<String, Set<String>> edges = new HashMap<>();

        private DummyGraph node(@NonNull String id, @NonNull Character value) {
            this.nodes.put(id, value);
            this.edges.putIfAbsent(id, new LinkedHashSet<>());
            return this;
        }

        private DummyGraph edge(String first, String second) {
            this.edges.computeIfAbsent(first, ignored -> new LinkedHashSet<>()).add(second);
            this.edges.computeIfAbsent(second, ignored -> new LinkedHashSet<>()).add(first);
            return this;
        }

        @Override
        public Set<String> nodesWithValue(Character value) {
            Set<String> matches = new LinkedHashSet<>();
            for (Map.Entry<String, Character> node : this.nodes.entrySet()) {
                if (Objects.equals(value, node.getValue())) {
                    matches.add(node.getKey());
                }
            }
            return matches;
        }

        @Override
        public Character get(String id) {
            return nodes.get(id);
        }

        @Override
        public Map<String, Character> adjacentsOf(String id) {
            Map<String, Character> adjacents = new LinkedHashMap<>();
            for (String adjacentId : edges.getOrDefault(id, emptySet())) {
                Character value = nodes.get(adjacentId);
                if (value != null) {
                    adjacents.put(adjacentId, value);
                }
            }
            return adjacents;
        }
    }

    private static final class FailingGraph<Id, Value> implements Graph<Id, Value> {
        @Override
        public Set<Id> nodesWithValue(Value value) {
            throw new AssertionError("Empty sequences should not read the graph");
        }

        @Override
        public Value get(Id id) {
            throw new AssertionError("Empty sequences should not read the graph");
        }

        @Override
        public Map<Id, Value> adjacentsOf(Id id) {
            throw new AssertionError("Empty sequences should not read the graph");
        }
    }
}
