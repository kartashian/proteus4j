package org.proteus4j.base;

interface HierarchyHandler {

    <T> T getSubInstance(Class<T> type, String name, Object... arguments);
}
