package org.proteus4j.test.model;

import org.proteus4j.annotation.Parent;

@Parent
public abstract class Figure {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void draw();
}
