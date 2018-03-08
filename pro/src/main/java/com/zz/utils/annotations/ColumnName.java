package com.zz.utils.annotations;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/7/6.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ColumnName {
    String columnName() default "";
    String fieldType() default "String";
}
