package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {
    String description() default "";

    Extension[] extensions() default {@Extension(properties = {@ExtensionProperty(name = "", value = "")})};

    ExternalDocs externalDocs() default @ExternalDocs(url = "");

    String name();
}
