package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponse {
    int code();

    String message();

    String reference() default "";

    Class<?> response() default Void.class;

    String responseContainer() default "";

    ResponseHeader[] responseHeaders() default {@ResponseHeader(name = "", response = Void.class)};
}
