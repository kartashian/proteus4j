package org.proteus4j.factory.base;

import org.proteus4j.factory.annotation.Parent;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.*;

enum HierarchyHandlerImpl implements HierarchyHandler {

    INSTANCE;

    private final Reflections reflections;
    private final Map<Class<?>, Hierarchy> hierarchies;

    HierarchyHandlerImpl() {
        this.reflections = new Reflections("", new TypeAnnotationsScanner(), new SubTypesScanner());
        this.hierarchies = Collections.unmodifiableMap(collect());
    }

    private Map<Class<?>, Hierarchy> collect() {
        Set<Class<?>> parents = reflections.getTypesAnnotatedWith(Parent.class);
        Map<Class<?>, Hierarchy> hierarchies = new HashMap<>();
        for (Class<?> parent : parents) {
            Set<Class<?>> children = (Set<Class<?>>) reflections.getSubTypesOf(parent);
            SimpleHierarchy hierarchy = new SimpleHierarchy(parent, children);
            hierarchies.put(hierarchy.getParent(), hierarchy);
        }
        return hierarchies;
    }

    public <T> T getSubInstance(Class<T> type, String name, Object... arguments) {
        IChild<T> child = retrieveChild(type, name);
        return child.getInstance(arguments);
    }

    private <T> IChild<T> retrieveChild(Class<T> type, String name) {
        Hierarchy hierarchy = hierarchies.get(type);
        if (hierarchy == null) {
            throw new UnsupportedOperationException("Unsupported factory object instantiation operation for " + type);
        }

        IChild<T> child = hierarchy.getChild(name);
        if (child == null) {
            throw new NoSuchElementException(String.format("Can't find child with name \"%s\" of %s", name, type));
        }
        return child;
    }
}
