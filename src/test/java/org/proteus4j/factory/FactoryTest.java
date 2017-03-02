package org.proteus4j.factory;

import org.junit.Test;
import org.proteus4j.factory.base.Factory;
import org.proteus4j.factory.model.Diamond;
import org.proteus4j.factory.model.Figure;
import org.proteus4j.factory.model.Shape;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FactoryTest {

    private static final String NAME = UUID.randomUUID().toString();

    @Test
    public void getInstance() {
        for (Shape shape : Shape.values()) {
            Figure figure = Factory.get(Figure.class, shape.getName());
            assertNotNull(figure);
            figure.draw();
        }
    }

    @Test
    public void getWithArguments() {
        double area = 4.0;
        Figure figure = Factory.get(Figure.class, Diamond.class.getName(), area, NAME);
        assertNotNull(figure);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getWithArgumentsOrderFail() {
        double area = 4.0;
        Factory.get(Figure.class, Diamond.class.getName(), NAME, area);
    }

    @Test
    public void getWithPackageAccess() {
        Figure figure = Factory.get(Figure.class, Shape.SQUARE.getName());
        assertNotNull(figure);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNoteExistedParent() {
        Factory.get(Integer.class, NAME);
    }

    @Test(expected = NoSuchElementException.class)
    public void getNotExistedChild() {
        Factory.get(Figure.class, NAME);
    }

    @Test
    public void singletonChild() {
        String shape = Shape.SQUARE.getName();

        Figure firstFigure = Factory.get(Figure.class, shape);
        firstFigure.setName(NAME);

        Figure secondFigure = Factory.get(Figure.class, shape);

        assertEquals(firstFigure.getName(), secondFigure.getName());
    }

    @Test
    public void getByDefaultName() {
        Figure figure = Factory.get(Figure.class, Diamond.class.getName());
        assertNotNull(figure);
    }
}