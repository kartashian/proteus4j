package org.proteus4j.factory.annotation;

import java.lang.annotation.*;

/**
 * Indicates that a type is super class in the factory types hierarchy.
 * In the traditional Factory Method pattern realisation, marked type would be a main factory creating type.
 * Type could be an interface, abstract class or regular class.
 * <p>
 * Parent class will be used for collecting child types info (implements or extend parent and market with {@link Child})
 * and for retrieving child instances from {@link org.proteus4j.factory.base.Factory}
 * <pre class="code">
 * &#064;Parent
 * public interface Figure {
 * }
 *
 * &#064;Child(value = "square")
 * public class Square implements Figure {
 * }
 *
 * public class Service {
 *    public Figure getFigure(String value) {
 *       return Factory.get(Figure.class, value);
 *    }
 * }
 * </pre>
 *
 * @author Avetik Kartashian
 * @see Child
 * @see org.proteus4j.factory.base.Factory
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parent {
}
