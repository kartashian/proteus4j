package org.proteus4j.factory.base;

class SingletonChild<T> implements IChild<T> {

    private T instance;
    private IChild<T> child;

    SingletonChild(IChild<T> child) {
        this.child = child;
    }

    @Override
    public Class<T> getType() {
        return child.getType();
    }

    @Override
    public String getName() {
        return child.getName();
    }

    @Override
    public T getInstance(Object... arguments) {
        if (instance == null) {
            instance = child.getInstance(arguments);
        }
        return instance;
    }

    @Override
    public String toString() {
        return "SingletonChild{" +
                "instance=" + instance +
                ", child=" + child +
                '}';
    }
}
