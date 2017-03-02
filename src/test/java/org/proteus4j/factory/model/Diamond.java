package org.proteus4j.factory.model;

import org.proteus4j.factory.annotation.Child;

@Child
public class Diamond extends Figure {

    private Double area;
    private String value;

    public Diamond() {
    }

    public Diamond(Double area, String value) {
        this.area = area;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Double getArea() {
        return area;
    }

    @Override
    public void draw() {
        System.out.println("Draw diamond!");
    }
}
