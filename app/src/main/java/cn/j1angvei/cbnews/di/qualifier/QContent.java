package cn.j1angvei.cbnews.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Wayne on 2016/9/9.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface QContent {
    String value() default "";
}