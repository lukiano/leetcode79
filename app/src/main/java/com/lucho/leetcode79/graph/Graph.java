package com.lucho.leetcode79.graph;

import java.util.Set;

public interface Graph<Id, Value> {
    Set<Id> nodesWithValue(Value value);
    Value get(Id id);
    Set<Id> adjacentsOf(Id id);
}