package org.proteus4j.factory.base;

import java.util.NoSuchElementException;

/**
 * The root factory class for accessing marked Java class hierarchies.
 * <p>
 * <p> This class is implementation of Factory Method pattern, which contain
 * classes parent and child relation and create objects without having to specify
 * the exact class of the object that will be created.
 * <p>
 * <p>This class is implemented by handler that hold a number of parent-children definitions(hierarchies),
 * each children identified by a parent type and String value. Depending on the child configuration,
 * the factory will return either an independent instance of an object or a single shared instance .
 * <p>
 * <p>The point of this approach is that the Factory is a central registry
 * of application components, and centralizes creation of polymorphic
 * components (no more do individual factory for every hierarchy type).
 *
 * @author Avetik Kartashian
 * @see org.proteus4j.factory.annotation.Parent
 * @see org.proteus4j.factory.annotation.Child
 */
public class Factory {

    private static HierarchyHandler handler = HierarchyHandlerImpl.INSTANCE;

    private Factory() {
    }

    /**
     * Return an instance by parent type and value, which may be shared or independent,
     * of the specified child.
     *
     * @param type the type the child must be match (parent type)
     * @param name the value of the class to created
     * @return an instance of the child
     * @throws UnsupportedOperationException if parent class not marked as parent or empty constructor not found
     * @throws NoSuchElementException        if there is no child of type with specified value
     */
    public static <T> T get(Class<T> type, String name) {
        return handler.getSubInstance(type, name);
    }

    /**
     * Return an instance of a child class, constructing with given arguments.
     * Arguments order must be the same as a child constructor's order. Behaves
     * the same as {@link #get(Class, String)}
     *
     * @param type      the type the child must be match (parent type)
     * @param name      the value of the class to created
     * @param arguments target object constructor arguments
     * @return an instance of the child
     * @throws UnsupportedOperationException if parent class not marked as parent or suited constructor not found
     * @throws NoSuchElementException        if there is no child of type with specified value
     */
    public static <T> T get(Class<T> type, String name, Object... arguments) {
        return handler.getSubInstance(type, name, arguments);
    }
}
