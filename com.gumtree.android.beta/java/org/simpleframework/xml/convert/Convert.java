package org.simpleframework.xml.convert;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Convert {
    Class<? extends Converter> value();
}
