package org.proteus4j.factory.model;

import org.proteus4j.factory.annotation.Child;

@Child(Shape.Value.TRIANGLE)
public class Triangle extends Figure {

    @Override
    public void draw() {
        System.out.println("Draw triangle!");
    }
}
