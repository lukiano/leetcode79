package com.lucho.leetcode79.graph;

import org.jspecify.annotations.NonNull;

public record Node<Id, Value>(@NonNull Id id, @NonNull Value value) {}