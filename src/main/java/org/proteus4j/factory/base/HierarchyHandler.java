package org.proteus4j.factory.base;

interface HierarchyHandler {

    <T> T getSubInstance(Class<T> type, String name, Object... arguments);
}
