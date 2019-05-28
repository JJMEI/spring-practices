#### spring 事务

```text
// 
16:44:36.861 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
16:44:36.940 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'fooService'
16:44:36.940 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor#0'

//  实例化切面
16:44:37.108 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txAdvice'
//  实例化事务管理器 DataSourceTransactionManager
16:44:37.111 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txManager'
//  实例化数据源
16:44:37.114 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'


//  配置事务拦截器 添加事务方法  get*  [传播方式、隔离级别、是否只读]
16:44:37.188 [main] DEBUG org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource - Adding transactional method [get*] with attribute [PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly]
16:44:37.188 [main] DEBUG org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource - Adding transactional method [*] with attribute [PROPAGATION_REQUIRED,ISOLATION_DEFAULT]
//  为指定方法创建事务 
16:44:37.249 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Creating new transaction with name [com.yitaqi.v1.service.impl.DefaultFooService.insertFoo]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT



16:44:37.556 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [HikariProxyConnection@424398527 wrapping com.mysql.cj.jdbc.ConnectionImpl@5d0a1059] for JDBC transaction
16:44:37.561 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@424398527 wrapping com.mysql.cj.jdbc.ConnectionImpl@5d0a1059] to manual commit
//  事务回滚
16:44:37.562 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction rollback
16:44:37.562 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [HikariProxyConnection@424398527 wrapping com.mysql.cj.jdbc.ConnectionImpl@5d0a1059]
16:44:37.563 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@424398527 wrapping com.mysql.cj.jdbc.ConnectionImpl@5d0a1059] after transaction
// 事务处理过程中抛出异常
Exception in thread "main" java.lang.UnsupportedOperationException

```




|属性|是否需要|默认值|描述|
| :---|:---|:---|:---|
name|yes| |与事务关联的方法名
propagation|no|REQUIRED|事务传播行为|
isolation|no|DEFAULT|事务隔离级别
timeout|no|-1|事务超时时间（以秒为单位）
read-only|no|false|事务是否只读
rollback-for|no| | 将被触发进行回滚的异常
no-rollback-for|no| | 不被触发进行回滚的异常


##### 注解事务 @Transactional

``` xml

<tx:annotation-driven transaction-manager="txManager"/>
```

        
#### SPRING中如何定义事务
> 事务属性设置完全由开发者控制

```java
    public interface TransactionDefinition{
        // 获取事务隔离级别
        int getIsolationLevel();
        // 获取事务传播行为
        int getPropagationBehavior();
        // 获取事务超时时间
        int getTimeout();
        // 是否只读事务
        boolean isReadOnly();
    }

```

|  事务隔离级别 | 描述
|:---|:---
| TransactionDefinition.ISOLATION_DEFAULT  | 
| TransactionDefinition.ISOLATION_READ_UNCOMMITTED|





