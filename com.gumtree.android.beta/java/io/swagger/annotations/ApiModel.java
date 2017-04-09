package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiModel {
    String description() default "";

    String discriminator() default "";

    Class<?> parent() default Void.class;

    String reference() default "";

    Class<?>[] subTypes() default {};

    String value() default "";
}
