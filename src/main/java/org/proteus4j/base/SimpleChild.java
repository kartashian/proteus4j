package org.proteus4j.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class SimpleChild<T> implements IChild<T> {

    final Class<T> type;
    final String name;

    SimpleChild(Class<T> type, String name) {
        this.type = type;
        typeCheck();
        this.name = (name == null || name.isEmpty()) ? type.getName() : name;
    }

    private void typeCheck() {
        if (type == null) {
            throw new IllegalArgumentException("Constructor arguments can't be null");
        }

        if (Modifier.isAbstract(type.getModifiers())) {
            throw new IllegalArgumentException("Child " + type + " can't be abstract");
        }
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
        Class[] classes = getClasses(arguments);
        return instantiate(classes, arguments);
    }

    private T instantiate(Class[] classes, Object[] arguments) {
        T instance;
        try {
            instance = getConstructor(classes).newInstance(arguments);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new UnsupportedOperationException(String.format("Can't instantiate %s with specified arguments: %s",
                    type, Arrays.toString(classes)), e);
        }
        return instance;
    }

    private Constructor<T> getConstructor(Class[] classes) throws NoSuchMethodException {
        Constructor<T> constructor = type.getDeclaredConstructor(classes);
        if (!Modifier.isPublic(constructor.getModifiers())) {
            constructor.setAccessible(true);
        }
        return constructor;
    }

    private Class[] getClasses(Object... arguments) {
        Class[] classes = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            classes[i] = arguments[i].getClass();
        }
        return classes;
    }

    @Override
    public String toString() {
        return "SimpleChild{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
