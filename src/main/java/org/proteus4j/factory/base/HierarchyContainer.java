package org.proteus4j.factory.base;

import org.proteus4j.factory.annotation.Parent;
import org.proteus4j.factory.annotation.ProteusConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum HierarchyContainer {

    INSTANCE;

    private final Reflections reflections;
    private final Map<Class<?>, Hierarchy> hierarchies;

    HierarchyContainer() {
        this.reflections = new Reflections("", new TypeAnnotationsScanner(), new SubTypesScanner());
        this.hierarchies = Collections.unmodifiableMap(collect());
    }

    public Map<Class<?>, Hierarchy> getHierarchies() {
        return hierarchies;
    }

    private Map<Class<?>, Hierarchy> collect() {
        Map<Class<?>, Hierarchy> typeHierarchy = collectFromType();
        Map<Class<?>, Hierarchy> configHierarchy = collectFromConfiguration();
        return mergeHierarchies(typeHierarchy, configHierarchy);
    }

    private Map<Class<?>, Hierarchy> mergeHierarchies(Map<Class<?>, Hierarchy> first, Map<Class<?>, Hierarchy> second) {
        Map<Class<?>, Hierarchy> hierarchy = new HashMap<>();
        hierarchy.putAll(first);
        for (Map.Entry<Class<?>, Hierarchy> entry : second.entrySet()) {
            Class<?> type = entry.getKey();
            if (hierarchy.containsKey(type)) {
                throw new IllegalArgumentException(type.getName() + " - duplicated Parent declaration");
            }
            hierarchy.put(type, entry.getValue());
        }
        return hierarchy;
    }

    private Map<Class<?>, Hierarchy> collectFromType() {
        Set<Class<?>> parents = reflections.getTypesAnnotatedWith(Parent.class);
        Map<Class<?>, Hierarchy> hierarchies = new HashMap<>();
        for (Class<?> parent : parents) {
            Set<Class<?>> children = (Set<Class<?>>) reflections.getSubTypesOf(parent);
            Hierarchy hierarchy = new SimpleHierarchy(parent, children);
            hierarchies.put(hierarchy.getParent(), hierarchy);
        }
        return hierarchies;
    }

    private Map<Class<?>, Hierarchy> collectFromConfiguration() {
        Map<Class<?>, ConfigMethod> methods = getConfigMethods();
        Set<Class<?>> parents = getParents(methods);
        return parents.stream()
                .map(parent -> {
                    List<ConfigMethod> children = getChildren(parent, methods);
                    return new SimpleHierarchy(parent, children);
                })
                .collect(Collectors.toMap(Hierarchy::getParent, hierarchy -> hierarchy));
    }

    private Map<Class<?>, ConfigMethod> getConfigMethods() {
        Map<Class<?>, ConfigMethod> methods = new HashMap<>();
        Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(ProteusConfiguration.class);
        configurations.forEach(configuration -> retrieveMethods(configuration, methods));
        return methods;
    }

    private void retrieveMethods(Class<?> configuration,Map<Class<?>, ConfigMethod> methods) {
        try {
            Object executor = configuration.newInstance();
            Map<Class<?>, ConfigMethod> configMethods = Stream.of(configuration)
                    .flatMap(c -> Stream.of(c.getDeclaredMethods()))
                    .map(m -> new ConfigMethod(executor, m))
                    .collect(Collectors.toMap(cm -> cm.getMethod().getReturnType(), cm -> cm));
            methods.putAll(configMethods);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Can't instantiate " + configuration, e);
        }
    }

    private Set<Class<?>> getParents(Map<? extends Class<?>, ConfigMethod> methods) {
        return methods.values()
                .stream()
                .map(ConfigMethod::getMethod)
                .filter(method -> method.getAnnotation(Parent.class) != null)
                .map(Method::getReturnType)
                .collect(Collectors.toSet());
    }

    private List<ConfigMethod> getChildren(Class<?> parent, Map<Class<?>, ConfigMethod> methods) {
        List<ConfigMethod> children = new ArrayList<>();
        for (Map.Entry<Class<?>, ConfigMethod> entry : methods.entrySet()) {
            Class<?> type = entry.getKey();
            ConfigMethod method = entry.getValue();
            if (parent.isAssignableFrom(type)) {
                children.add(method);
            }
        }
        return children;
    }
}