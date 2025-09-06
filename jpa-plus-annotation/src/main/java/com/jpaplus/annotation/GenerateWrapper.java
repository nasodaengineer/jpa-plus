package com.jpaplus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成Wrapper的注解
 * 标注在实体类上，编译时会自动生成对应的Wrapper类
 * 
 * @author JPA-Plus
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface GenerateWrapper {
    
    /**
     * 生成的Wrapper类名后缀
     */
    String suffix() default "Wrapper";
    
    /**
     * 是否生成TypedWrapper
     */
    boolean generateTyped() default true;
    
    /**
     * 是否生成元数据类
     */
    boolean generateMetadata() default true;
    
    /**
     * 包含的字段
     */
    String[] includeFields() default {};
    
    /**
     * 排除的字段
     */
    String[] excludeFields() default {};
    
    /**
     * 是否生成Lambda查询方法
     */
    boolean generateLambda() default true;
}