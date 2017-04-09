package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {
    String access() default "";

    boolean allowMultiple() default false;

    String allowableValues() default "";

    String defaultValue() default "";

    String example() default "";

    Example examples() default @Example({@ExampleProperty(mediaType = "", value = "")});

    boolean hidden() default false;

    String name() default "";

    boolean required() default false;

    String value() default "";
}
