# JPA-Plus 模块化架构重构

## 🎯 重构目标

您提出的问题非常正确！APT处理器确实应该单独在一个模块中，这样可以：

1. **独立测试**：APT处理器可以独立进行单元测试
2. **依赖隔离**：避免运行时依赖编译时工具
3. **模块化发布**：可以独立发布和版本管理
4. **更好的测试覆盖**：使用Google的compile-testing库进行完整的APT测试

## 🏗️ 新的模块结构

```
jpa-plus/
├── jpa-plus-parent/              # 父模块（依赖管理）
├── jpa-plus-annotation/          # 注解定义模块
├── jpa-plus-processor/           # APT处理器模块（独立）
├── jpa-plus-core/               # 核心Wrapper实现
├── jpa-plus-spring-boot-starter/ # Spring Boot集成
└── jpa-plus-example/            # 示例项目
```

### 模块职责划分

#### 1. jpa-plus-annotation
- **职责**：纯注解定义
- **依赖**：无外部依赖
- **内容**：`@GenerateWrapper`、`@WrapperField`、`OperationType`等

#### 2. jpa-plus-processor  
- **职责**：APT编译时代码生成
- **依赖**：`jpa-plus-annotation`、`javapoet`、`auto-service`
- **测试**：使用`compile-testing`库进行APT测试
- **内容**：`WrapperProcessor`、代码生成逻辑

#### 3. jpa-plus-core
- **职责**：运行时Wrapper核心功能
- **依赖**：`jpa-plus-annotation`、JPA API、可选的Spring支持
- **内容**：`AbstractWrapper`、`QueryWrapper`、条件类、元数据类

#### 4. jpa-plus-spring-boot-starter
- **职责**：Spring Boot自动配置
- **依赖**：`jpa-plus-core`、Spring Boot
- **内容**：自动配置类、配置属性

#### 5. jpa-plus-example
- **职责**：使用示例和功能演示
- **依赖**：`jpa-plus-spring-boot-starter`、`jpa-plus-processor`（编译时）
- **内容**：实体类、Controller、配置文件

## 🔧 APT模块测试策略

### 独立APT测试

在`jpa-plus-processor`模块中，我们使用Google的`compile-testing`库：

```java
@Test
void testGenerateWrapperForSimpleEntity() {
    Compilation compilation = javac()
        .withProcessors(new WrapperProcessor())
        .compile(JavaFileObjects.forSourceString("test.User", """
            @Entity
            @GenerateWrapper
            public class User {
                @Id private Long id;
                private String name;
                private Integer age;
            }
            """));
    
    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("test.UserWrapper")
        .contentsAsUtf8String()
        .contains("public final class UserWrapper extends QueryWrapper<User>");
}
```

### 测试覆盖的场景

1. **基本代码生成**：验证为简单实体生成正确的Wrapper类
2. **字段类型处理**：验证不同字段类型生成对应的方法
3. **注解参数处理**：验证`includeFields`、`excludeFields`等参数
4. **错误处理**：验证处理器对错误输入的处理
5. **元数据生成**：验证生成的元数据类的正确性

## 🚀 使用方式

### 对于库开发者

```bash
# 构建所有模块
cd jpa-plus-parent
mvn clean install

# 只测试APT处理器
cd ../jpa-plus-processor
mvn test
```

### 对于库使用者

只需要在项目中添加starter依赖和处理器：

```xml
<dependency>
    <groupId>com.jpaplus</groupId>
    <artifactId>jpa-plus-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

<!-- 编译时APT处理器 -->
<dependency>
    <groupId>com.jpaplus</groupId>
    <artifactId>jpa-plus-processor</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

## 📦 发布策略

### 版本管理
- 所有模块使用统一版本号
- 通过父模块进行版本管理
- 独立模块可以单独发布

### Maven配置
- APT处理器配置在编译插件中：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>com.jpaplus</groupId>
                <artifactId>jpa-plus-processor</artifactId>
                <version>${jpa-plus.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## ✅ 优势总结

1. **清晰的职责分离**：每个模块都有明确的职责
2. **独立的测试能力**：APT处理器可以完全独立测试
3. **更好的依赖管理**：避免运行时依赖编译时工具
4. **灵活的发布策略**：可以独立发布各个模块
5. **更容易维护**：模块化使代码更容易理解和维护

## 🎯 下一步

1. 完善APT处理器的测试覆盖
2. 实现更复杂的代码生成逻辑（如TypedWrapper）
3. 添加更多的字段类型支持
4. 优化生成代码的性能
5. 完善Spring Boot集成

这种模块化架构完全解决了您提出的APT测试问题，使整个项目更加健壮和可维护！