package com.gumtree.android.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

@Retention(RetentionPolicy.RUNTIME)
@Scope
@Documented
public @interface ApplicationScope {
}
