package org.proteus4j.test.model;

import org.proteus4j.annotation.Child;

@Child(name = Shape.Value.SQUARE, singleton = true)
public class Square extends Figure {

    Square() {
    }

    @Override
    public void draw() {
        System.out.println("Draw square!");
    }
}
