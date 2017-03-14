package org.proteus4j.factory.annotation;

import java.lang.annotation.*;

/**
 * Indicates that a class declares one or more {@link Parent @Parent} and {@link Child @Child}
 * methods and may be processed by the {@link org.proteus4j.factory.base.Factory} to create
 * type instances and service requests for those instances at runtime, for example:
 *
 *<pre class="code">
 * &#064;ProteusConfiguration
 * public class FactoryConfig {
 *
 *     &#064;Parent
 *     public MyParent myParent() {
 *          ...
 *     }
 *     &#064;Child("myChild")
 *     public MyChild myChild() {
 *          ...
 *     }
 * }</pre>
 *
 * @author Avetik Kartashian
 * @see Child
 * @see Parent
 * @see org.proteus4j.factory.base.Factory
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProteusConfiguration {
}