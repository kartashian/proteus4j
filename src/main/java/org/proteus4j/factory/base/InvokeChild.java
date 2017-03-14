package org.proteus4j.factory.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class InvokeChild<T> implements IChild<T>{

    private String name;
    private Class<T> type;
    private Method method;
    private Object executor;

    InvokeChild(Method method, String name, Object executor) {
        if (method == null || executor == null) {
            throw new IllegalArgumentException("Constructor arguments can't be null");
        }
        this.method = method;
        this.executor = executor;
        this.type = (Class<T>) method.getReturnType();
        this.name = (name == null || name.isEmpty()) ? type.getName() : name;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getInstance(Object... arguments) {
        Object instance;
        try {
             instance = method.invoke(executor, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException(String.format("Can't instantiate %s with specified arguments: %s",
                    type, Arrays.toString(arguments)), e);
        }
        return type.cast(instance);
    }
}
