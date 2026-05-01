package com.lucho.leetcode79.graph;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinderTest {

    @Test
    void emptySequenceIsAlwaysFound() {
        Finder<String, Character> finder = new Finder<>(new FailingGraph<>(), List.of());

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
    void returnsFalseWhenGraphHasCandidateButNodeLookupIsMissing() {
        Graph<String, Character> graph = new Graph<>() {
            @Override
            public Set<Node<String, Character>> nodesWithValue(Character value) {
                return Set.of(new Node<>("missing", value));
            }

            @Override
            public Node<String, Character> nodeAt(String id) {
                return null;
            }

            @Override
            public Set<Node<String, Character>> adjacentsOf(String id) {
                return Set.of();
            }
        };

        Finder<String, Character> finder = new Finder<>(graph, List.of('A'));

        assertFalse(finder.containsWord());
    }

    @Test
    void cachesSearchResult() {
        DummyGraph graph = new DummyGraph()
            .node("a", 'A');
        Finder<String, Character> finder = new Finder<>(graph, List.of('A'));

        assertTrue(finder.containsWord());
        assertTrue(finder.containsWord());

        assertTrue(graph.nodesWithValueCalls == 1);
    }

    private static final class DummyGraph implements Graph<String, Character> {
        private final Map<String, Node<String, Character>> nodes = new HashMap<>();
        private final Map<String, Set<String>> edges = new HashMap<>();
        private int nodesWithValueCalls;

        private DummyGraph node(String id, Character value) {
            nodes.put(id, new Node<>(id, value));
            edges.putIfAbsent(id, new LinkedHashSet<>());
            return this;
        }

        private DummyGraph edge(String first, String second) {
            edges.computeIfAbsent(first, ignored -> new LinkedHashSet<>()).add(second);
            edges.computeIfAbsent(second, ignored -> new LinkedHashSet<>()).add(first);
            return this;
        }

        @Override
        public Set<Node<String, Character>> nodesWithValue(Character value) {
            nodesWithValueCalls++;
            Set<Node<String, Character>> matches = new LinkedHashSet<>();
            for (Node<String, Character> node : nodes.values()) {
                if (Objects.equals(value, node.value())) {
                    matches.add(node);
                }
            }
            return matches;
        }

        @Override
        public Node<String, Character> nodeAt(String id) {
            return nodes.get(id);
        }

        @Override
        public Set<Node<String, Character>> adjacentsOf(String id) {
            Set<Node<String, Character>> adjacents = new LinkedHashSet<>();
            for (String adjacentId : edges.getOrDefault(id, Set.of())) {
                Node<String, Character> node = nodes.get(adjacentId);
                if (node != null) {
                    adjacents.add(node);
                }
            }
            return adjacents;
        }
    }

    private static final class FailingGraph<Id, Value> implements Graph<Id, Value> {
        @Override
        public Set<Node<Id, Value>> nodesWithValue(Value value) {
            throw new AssertionError("Empty sequences should not read the graph");
        }

        @Override
        public Node<Id, Value> nodeAt(Id id) {
            throw new AssertionError("Empty sequences should not read the graph");
        }

        @Override
        public Set<Node<Id, Value>> adjacentsOf(Id id) {
            throw new AssertionError("Empty sequences should not read the graph");
        }
    }
}
