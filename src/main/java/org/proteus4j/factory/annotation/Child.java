package org.proteus4j.factory.annotation;

import java.lang.annotation.*;

/**
 * Indicates that a type is subclass (extension or implementation) of some class marked as {@link Parent}.
 * Will be used by {@link org.proteus4j.factory.base.Factory} for creating new object by its parameters.
 * Child type couldn't be abstract.
 *
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
 * @see Parent
 * @see org.proteus4j.factory.base.Factory
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Child {

    /**
     * Child type value which use for creating its instances from {@link org.proteus4j.factory.base.Factory}
     * Default value is type class value {@link Class#getName()}
     */
    String value() default "";

    /**
     * Determine if the child should be a singleton (has only one instance)
     */
    boolean singleton() default false;
}