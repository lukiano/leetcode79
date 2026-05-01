package com.lucho.leetcode79;

import java.util.ArrayList;
import java.util.Objects;

public class Path<T> {
    private ArrayList<T> data;

    public Path() {
        this(new ArrayList<>());
    }

    private Path(ArrayList<T> data) {
        this.data = data;
    }

    public boolean contains(T t) {
        return this.data.contains(t);
    }

    public Path<T> add(T t) {
        ArrayList<T> clone = new ArrayList<>(this.data);
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
