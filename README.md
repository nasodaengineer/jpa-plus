# JPA-Plus Wrapper DSL

一个类似于MyBatis-Plus的Spring Data JPA查询构建器，提供流畅、类型安全的查询DSL。

## 🚀 快速开始

### 构建项目

```bash
# 构建所有模块
mvn clean install

# 运行示例应用
cd jpa-plus-example
mvn spring-boot:run
```

## 🎯 核心特性

### 1. 流畅的查询API

```java
QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
wrapper.eq("status", "active")
       .like("name", "张")
       .between("age", 25, 60)
       .in("department", "技术部", "产品部")
       .isNull("deletedAt")
       .orderByDesc("createTime");
```

### 2. 动态条件构建

```java
QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
wrapper.like(StringUtils.hasText(name), "name", name)
       .ge(minAge != null, "age", minAge)
       .eq(department != null, "department", department);
```

## 📁 模块化项目结构

```
jpa-plus/
├── jpa-plus-parent/              # 父模块（依赖管理）
├── jpa-plus-annotation/          # 注解定义模块
├── jpa-plus-processor/           # APT处理器模块
├── jpa-plus-core/               # 核心Wrapper实现
├── jpa-plus-spring-boot-starter/ # Spring Boot集成
└── jpa-plus-example/            # 示例项目
```

## 🤝 贡献

欢迎提交Issue和Pull Request来改进这个项目！

## 📄 许可证

本项目采用 MIT 许可证。