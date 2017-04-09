package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Api {
    Authorization[] authorizations() default {@Authorization("")};

    @Deprecated
    String basePath() default "";

    String consumes() default "";

    @Deprecated
    String description() default "";

    boolean hidden() default false;

    @Deprecated
    int position() default 0;

    String produces() default "";

    String protocols() default "";

    String[] tags() default {""};

    String value() default "";
}
