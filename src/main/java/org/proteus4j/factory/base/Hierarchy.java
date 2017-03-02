package org.proteus4j.factory.base;

interface Hierarchy {

    Class getParent();

    IChild getChild(String name);
}
