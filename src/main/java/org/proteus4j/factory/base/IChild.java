package org.proteus4j.factory.base;

interface IChild<T> {

    Class<T> getType();

    String getName();

    T getInstance(Object... arguments);
}
