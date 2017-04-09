package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Info {
    Contact contact() default @Contact(name = "");

    String description() default "";

    Extension[] extensions() default {@Extension(properties = {@ExtensionProperty(name = "", value = "")})};

    License license() default @License(name = "");

    String termsOfService() default "";

    String title();

    String version();
}
