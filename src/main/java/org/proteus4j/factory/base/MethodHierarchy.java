package org.proteus4j.factory.base;

import org.proteus4j.factory.annotation.Child;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MethodHierarchy implements Hierarchy {

    private final Class<?> parent;
    private Map<String, IChild> children;

    MethodHierarchy(Class<?> parent, Collection<ConfigMethod> children) {
        if (parent == null || children == null) {
            throw new IllegalArgumentException("Constructor arguments can't be null");
        }
        this.parent = parent;
        setChildren(children);
    }

    private void setChildren(Collection<ConfigMethod> children) {
        Map<String, IChild> childInfo = getChildInfo(children);
        this.children = Collections.unmodifiableMap(childInfo);
    }

    private Map<String, IChild> getChildInfo(Collection<ConfigMethod> children) {
        Map<String, IChild> childInfo = new HashMap<>();
        for (ConfigMethod childMethod : children) {
            IChild<?> child = createChild(childMethod);
            addChildTo(childInfo, child);
        }
        return childInfo;
    }

    private <T> IChild<T> createChild(ConfigMethod configMethod) {
        IChild<T> child = null;
        Method childMethod = configMethod.getMethod();
        Child annotation = childMethod.getAnnotation(Child.class);
        if (annotation != null && parent.isAssignableFrom(childMethod.getReturnType())) {
            String childName = annotation.value();
            InvokeChild<T> invokeChild = new InvokeChild<>(childMethod, childName, configMethod.getExecutor());
            child = annotation.singleton() ? new SingletonChild<>(invokeChild) : invokeChild;
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
}