package org.proteus4j.base;

class SingletonChild<T> extends SimpleChild<T> {

    private T instance;

    SingletonChild(Class<T> type, String name) {
        super(type, name);
    }

    @Override
    public T getInstance(Object... arguments) {
        if (instance == null) {
            instance = super.getInstance(arguments);
        }
        return instance;
    }

    @Override
    public String toString() {
        return "SingletonChild{" +
                "instance=" + instance +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
