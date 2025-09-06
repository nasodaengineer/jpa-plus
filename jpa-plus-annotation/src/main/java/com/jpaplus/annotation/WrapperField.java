package com.jpaplus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wrapper字段注解
 * 用于精细控制字段的查询和更新行为
 * 
 * @author JPA-Plus
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface WrapperField {
    
    /**
     * 字段别名
     */
    String alias() default "";
    
    /**
     * 是否可查询
     */
    boolean queryable() default true;
    
    /**
     * 是否可更新
     */
    boolean updatable() default true;
    
    /**
     * 支持的操作类型
     */
    OperationType[] operations() default {};
    
    /**
     * 字段描述
     */
    String description() default "";
}