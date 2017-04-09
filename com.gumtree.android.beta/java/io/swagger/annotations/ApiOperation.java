package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {
    Authorization[] authorizations() default {@Authorization("")};

    int code() default 200;

    String consumes() default "";

    Extension[] extensions() default {@Extension(properties = {@ExtensionProperty(name = "", value = "")})};

    boolean hidden() default false;

    String httpMethod() default "";

    String nickname() default "";

    String notes() default "";

    @Deprecated
    int position() default 0;

    String produces() default "";

    String protocols() default "";

    Class<?> response() default Void.class;

    String responseContainer() default "";

    ResponseHeader[] responseHeaders() default {@ResponseHeader(name = "", response = Void.class)};

    String responseReference() default "";

    String[] tags() default {""};

    String value();
}
