package org.proteus4j.test.model;

public enum Shape {
    SQUARE(Value.SQUARE),
    TRIANGLE(Value.TRIANGLE);

    private String name;

    Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Value {
        public static final String SQUARE = "SQUARE";
        public static final String TRIANGLE = "TRIANGLE";
    }
}
