package org.proteus4j.base;

interface IChild<T> {

    Class<T> getType();

    String getName();

    T getInstance(Object... arguments);
}
