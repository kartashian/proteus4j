package org.proteus4j.base;

interface Hierarchy {

    Class getParent();

    IChild getChild(String name);
}
