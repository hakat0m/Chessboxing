package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {
    String access() default "";

    String allowableValues() default "";

    String dataType() default "";

    String example() default "";

    boolean hidden() default false;

    String name() default "";

    String notes() default "";

    int position() default 0;

    boolean readOnly() default false;

    String reference() default "";

    boolean required() default false;

    String value() default "";
}
