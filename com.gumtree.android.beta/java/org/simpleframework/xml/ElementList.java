package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ElementList {
    boolean data() default false;

    boolean empty() default true;

    String entry() default "";

    boolean inline() default false;

    String name() default "";

    boolean required() default true;

    Class type() default void.class;
}
