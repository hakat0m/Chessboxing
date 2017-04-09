package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SwaggerDefinition {

    public enum Scheme {
        DEFAULT,
        HTTP,
        HTTPS,
        WS,
        WSS
    }

    String basePath() default "";

    String[] consumes() default {""};

    ExternalDocs externalDocs() default @ExternalDocs(url = "");

    String host() default "";

    Info info() default @Info(title = "", version = "");

    String[] produces() default {""};

    Scheme[] schemes() default {Scheme.DEFAULT};

    Tag[] tags() default {@Tag(name = "")};
}
