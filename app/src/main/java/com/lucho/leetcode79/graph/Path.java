package com.lucho.leetcode79.graph;

import java.util.ArrayList;
import java.util.Objects;

final class Path<Value> {
    private final ArrayList<Value> data;

    public Path() {
        this(new ArrayList<>());
    }

    private Path(ArrayList<Value> data) {
        this.data = data;
    }

    public boolean contains(Value t) {
        return this.data.contains(t);
    }

    public Path<Value> add(Value t) {
        ArrayList<Value> clone = new ArrayList<>(this.data);
        clone.add(t);
        return new Path<>(clone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Path<?> other)) {
            return false;
        }
        return Objects.equals(this.data, other.data);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

}
