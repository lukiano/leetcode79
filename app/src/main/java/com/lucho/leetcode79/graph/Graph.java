package com.lucho.leetcode79.graph;

import java.util.Set;

import org.jspecify.annotations.Nullable;

public interface Graph<Id, Value> {
    Set<Node<Id, Value>> nodesWithValue(Value value);
    @Nullable Node<Id, Value> nodeAt(Id id);
    Set<Node<Id, Value>> adjacentsOf(Id id);
}