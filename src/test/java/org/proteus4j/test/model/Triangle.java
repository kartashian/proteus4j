package org.proteus4j.test.model;

import org.proteus4j.annotation.Child;

@Child(name = Shape.Value.TRIANGLE)
public class Triangle extends Figure {

    @Override
    public void draw() {
        System.out.println("Draw triangle!");
    }
}
