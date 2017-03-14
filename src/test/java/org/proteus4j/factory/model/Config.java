package org.proteus4j.factory.model;

import org.proteus4j.factory.annotation.Child;
import org.proteus4j.factory.annotation.Parent;
import org.proteus4j.factory.annotation.ProteusConfiguration;

@ProteusConfiguration
public class Config {

    @Parent
    public Number number() {
        return 0;
    }

    @Child
    public Integer integer() {
        return 1;
    }
}