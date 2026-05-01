package com.lucho.leetcode79.board;

record Coor(int x, int y) {
    public Coor {
        if (x < 0) {
            throw new IllegalArgumentException("X cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("Y cannot be negative");
        }
    }
}
