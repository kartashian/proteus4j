package org.proteus4j.factory.base;

import org.proteus4j.factory.annotation.Child;

import java.util.*;

class TypeHierarchy implements Hierarchy {

    private final Class<?> parent;
    private Map<String, IChild> children;

    TypeHierarchy(Class<?> parent, Set<Class<?>> children) {
        if (parent == null || children == null) {
            throw new IllegalArgumentException("Constructor arguments can't be null");
        }
        this.parent = parent;
        setChildren(children);
    }

    private void setChildren(Set<Class<?>> children) {
        Map<String, IChild> childInfo = getChildInfo(children);
        this.children = Collections.unmodifiableMap(childInfo);
    }

    private Map<String, IChild> getChildInfo(Set<Class<?>> children) {
        Map<String, IChild> childInfo = new HashMap<>();
        for (Class<?> childClass : children) {
            IChild<?> child = createChild(childClass);
            addChildTo(childInfo, child);
        }
        return childInfo;
    }

    private <T> IChild<T> createChild(Class<T> childClass) {
        IChild<T> child = null;
        Child annotation = childClass.getAnnotation(Child.class);
        if (annotation != null && parent.isAssignableFrom(childClass)) {
            String childName = annotation.value();
            SimpleChild<T> simpleChild = new SimpleChild<>(childClass, childName);
            child = annotation.singleton() ? new SingletonChild<>(simpleChild) : simpleChild;
        }
        return child;
    }

    private void addChildTo(Map<String, IChild> children, IChild<?> child) {
        if (child == null) return;

        String name = child.getName();
        if (children.containsKey(name)) {
            throw new IllegalArgumentException("Name \"" + name + "\" of " + child.getType() + " child is duplicated");
        }

        children.put(name, child);
    }

    @Override
    public Class<?> getParent() {
        return parent;
    }

    @Override
    public IChild getChild(String name) {
        return children.get(name);
    }

    @Override
    public String toString() {
        return "TypeHierarchy{" +
                "parent=" + parent +
                ", children=" + children +
                '}';
    }
}
