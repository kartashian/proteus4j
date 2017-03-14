package org.proteus4j.factory.base;

import java.lang.reflect.Method;

class ConfigMethod {

    private final Object executor;
    private final Method method;

    ConfigMethod(Object executor, Method method) {
        if (method == null || executor == null) {
            throw new IllegalArgumentException("Constructor arguments can't be null");
        }
        this.executor = executor;
        this.method = method;
        validateMethod();
    }

    private void validateMethod() {
        if (method.getReturnType().equals(Void.TYPE)) {
            throw new IllegalArgumentException("Method \"" + method + "\" can't be void");
        }
        Class<?> executorClass = executor.getClass();
        if (!method.getDeclaringClass().equals(executorClass)) {
            throw new IllegalArgumentException("Method \"" + method + "\" doesn't belong to " + executorClass);
        }
    }

    Object getExecutor() {
        return executor;
    }

    Method getMethod() {
        return method;
    }
}