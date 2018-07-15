package org.smartweb.Annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    //指定切面拦截的类的注解，哪种类被拦截
    Class<? extends Annotation> value();
}
