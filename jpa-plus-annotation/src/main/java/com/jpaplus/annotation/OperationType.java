package com.jpaplus.annotation;

/**
 * 支持的操作类型枚举
 * 
 * @author JPA-Plus
 * @since 1.0.0
 */
public enum OperationType {
    
    /** 等于 */
    EQ,
    
    /** 不等于 */
    NE,
    
    /** 大于 */
    GT,
    
    /** 大于等于 */
    GE,
    
    /** 小于 */
    LT,
    
    /** 小于等于 */
    LE,
    
    /** 模糊查询 */
    LIKE,
    
    /** 左模糊查询 */
    LIKE_LEFT,
    
    /** 右模糊查询 */
    LIKE_RIGHT,
    
    /** 不模糊查询 */
    NOT_LIKE,
    
    /** 在...中 */
    IN,
    
    /** 不在...中 */
    NOT_IN,
    
    /** 区间查询 */
    BETWEEN,
    
    /** 不在区间 */
    NOT_BETWEEN,
    
    /** 为空 */
    IS_NULL,
    
    /** 不为空 */
    IS_NOT_NULL,
    
    /** 存在子查询 */
    EXISTS,
    
    /** 不存在子查询 */
    NOT_EXISTS,
    
    /** JSON字段操作 */
    JSON_EXTRACT,
    
    /** 地理空间操作 */
    GEOSPATIAL,
    
    /** 全文搜索 */
    FULL_TEXT_SEARCH
}