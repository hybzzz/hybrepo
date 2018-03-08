package com.zz.utils.annotations;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/7/6.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IdColumn {
    String idName() default "ID";
}
