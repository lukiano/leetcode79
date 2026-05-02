package com.lucho.leetcode79.graph;

import java.util.Map;
import java.util.Set;

public interface Graph<Id, Value> {
    Set<Id> nodesWithValue(Value value);
    Value get(Id id);
    Map<Id, Value> adjacentsOf(Id id);
}