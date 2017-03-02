package org.proteus4j.factory.model;

import org.proteus4j.factory.annotation.Child;

@Child(name = Shape.Value.SQUARE, singleton = true)
public class Square extends Figure {

    Square() {
    }

    @Override
    public void draw() {
        System.out.println("Draw square!");
    }
}
