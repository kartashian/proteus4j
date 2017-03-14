package org.proteus4j.factory.base;

import java.util.*;

enum HierarchyHandlerImpl implements HierarchyHandler {

    INSTANCE;

    private final Map<Class<?>, Hierarchy> hierarchies;

    HierarchyHandlerImpl() {
        this.hierarchies = HierarchyContainer.INSTANCE.getHierarchies();
    }

    @Override
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
            throw new NoSuchElementException(String.format("Can't find child with value \"%s\" of %s", name, type));
        }
        return child;
    }
}
