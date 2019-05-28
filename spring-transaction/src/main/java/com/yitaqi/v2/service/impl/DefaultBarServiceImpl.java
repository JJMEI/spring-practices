package com.yitaqi.v2.service.impl;

import com.yitaqi.v2.model.to.Foo;
import com.yitaqi.v2.service.BarService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public class DefaultBarServiceImpl implements BarService{


    /**
     *  fooService.insertFoo() 方法事务传播行为 PROPAGATION_REQUIRED
        加入当前正要执行的事务不在另一个事务里，就起一个新事务，如果已经存在事务则加入该事务
        barService.update() PROPAGATION_REQUIRED      insertFoo() 中调用 updateFoo() 此时insertFoo()已经创建了一个事务 因此 updateFoo() 不需要创建新事务 直接加入即可
     20:04:18.416 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
     20:04:18.487 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
     20:04:18.489 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
     20:04:18.492 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#0'
     20:04:18.515 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.interceptor.TransactionInterceptor#0'
     20:04:18.523 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txManager'
     20:04:18.526 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'
     20:04:18.566 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'fooService'
     20:04:18.566 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'barService'
     20:04:18.621 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Creating new transaction with name [com.yitaqi.v2.service.impl.DefaultFooService.insertFoo]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.RuntimeException
     20:04:18.918 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] for JDBC transaction
     20:04:18.923 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] to manual commit

     &&&&&&&&&&&&&&
     & 加入现有事务 &
     &&&&&&&&&&&&&&
     20:04:18.924 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Participating in existing transaction
     20:04:18.924 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction rollback
     20:04:18.925 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a]
     20:04:18.925 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] after transaction
     * @param foo
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public int updateBar(Foo foo) {
        return 0;
    }

    /**
     * 传播行为 Propagation.MANDATORY 只能被一个父事务调用否则就会抛出异常  ServiceA.methodA() 非事务方法 其调用传播行为是Propagation.MANADATORY的ServiceB.methodB()会直接抛出异常
     *
     20:00:26.071 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
     20:00:26.148 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
     20:00:26.151 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
     20:00:26.153 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#0'
     20:00:26.177 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.interceptor.TransactionInterceptor#0'
     20:00:26.185 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txManager'
     20:00:26.188 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'
     20:00:26.228 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'fooService'
     20:00:26.228 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'barService'

     $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     $ ServiceA.methodA()并不是事务方法，而ServiceB.methodB() 传播行为Propagation.MANDATORY 要求它只能被一个父事务调用否则会抛出异常 IllegalTransactionStateException $
     $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

     Exception in thread "main" org.springframework.transaction.IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'
     at org.springframework.transaction.support.AbstractPlatformTransactionManager.getTransaction(AbstractPlatformTransactionManager.java:364)
     at org.springframework.transaction.interceptor.TransactionAspectSupport.createTransactionIfNecessary(TransactionAspectSupport.java:475)
     at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:289)
     at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:98)
     at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
     at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)
     at com.sun.proxy.$Proxy6.insertBarWithPropagationMandatory(Unknown Source)
     at com.yitaqi.v2.service.impl.DefaultFooService.updateFoo(DefaultFooService.java:49)
     at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     at java.lang.reflect.Method.invoke(Method.java:498)
     at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:343)
     at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:206)
     at com.sun.proxy.$Proxy7.updateFoo(Unknown Source)
     at com.yitaqi.v2.Boot.main(Boot.java:28)
     *
     *
     * @param fo
     * @return
     */
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {RuntimeException.class})
    @Override
    public int insertBarWithPropagationMandatory(Foo fo) {
        return 0;
    }



    /**
     * ServiceA.methodA() 事务 调用ServiceB.methodB() methodB()的事务传播行为 是 Propagation.REQUIRES_NEW
     * 这时ServiceA.methodA()的事务就会被挂起，ServiceB.methodB()则会新起一个事务
     * 等ServiceB.methodB()的事务运行完毕后，ServiceA.methodA()事务才得以运行
     *
     * --------------------------
     * 如果ServiceB.methodB()已提交成功，ServiceA.methodA()失败，执行回滚操作，此时
     * ServiceB.methodB()不会回滚
     *
     * 如果ServiceB.methodB()回滚 如果他的异常被ServiceA.methodA()捕获，ServiceA.methodA()事务仍可以提交
     *
     *
     * --------------------------------------------------------------------------------------------------------------------
     19:46:10.423 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
     19:46:10.504 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
     19:46:10.506 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
     19:46:10.509 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#0'
     19:46:10.534 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.interceptor.TransactionInterceptor#0'
     19:46:10.544 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txManager'
     19:46:10.547 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'
     19:46:10.593 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'fooService'
     19:46:10.594 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'barService'

     ***********************************************************
     * 创建ServiceA.methodA() 事务 传播行为 PROPAGATION_REQUIRED *
     ***********************************************************

     19:46:10.643 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Creating new transaction with name [com.yitaqi.v2.service.impl.DefaultFooService.getFoo]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.RuntimeException

     19:46:10.948 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] for JDBC transaction
     19:46:10.952 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] to manual commit

     ##################################################################################
     # 暂停当前事务，为ServiceB.methodB()创建一个新事务  传播行为 PROPAGATION_REQUIRES_NEW #
     ##################################################################################

     19:46:10.955 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Suspending current transaction, creating new transaction with name [com.yitaqi.v2.service.impl.DefaultBarServiceImpl.insertBarWithPropagationRequiresNew]
     19:46:10.968 [HikariPool-1 connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@21c6286e
     19:46:10.968 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [HikariProxyConnection@330739404 wrapping com.mysql.cj.jdbc.ConnectionImpl@21c6286e] for JDBC transaction
     19:46:10.968 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@330739404 wrapping com.mysql.cj.jdbc.ConnectionImpl@21c6286e] to manual commit
     19:46:10.968 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction commit
     19:46:10.968 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Committing JDBC transaction on Connection [HikariProxyConnection@330739404 wrapping com.mysql.cj.jdbc.ConnectionImpl@21c6286e]
     19:46:10.969 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@330739404 wrapping com.mysql.cj.jdbc.ConnectionImpl@21c6286e] after transaction

     ################################################################
     # 完成ServiceB.methodB()的事务，重新启用 ServiceA.methodA()的事务  #
     ################################################################
     19:46:10.977 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Resuming suspended transaction after completion of inner transaction


     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     ~ ServiceA.methodA() 异常 触发回滚 但此时ServiceB.methodB() 已经commit, 不会与ServiceA.methodA()一到回滚~
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     19:46:10.978 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction rollback
     19:46:10.978 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a]
     19:46:10.981 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] after transaction
     *
     *
     * @param fo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class})
    @Override
    public int insertBarWithPropagationRequiresNew(Foo fo) {
        return 0;
    }


    /**
     *
     20:25:29.726 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
     20:25:29.801 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
     20:25:29.803 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
     20:25:29.806 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#0'
     20:25:29.830 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.interceptor.TransactionInterceptor#0'
     20:25:29.839 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'txManager'
     20:25:29.842 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'
     20:25:29.882 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'fooService'
     20:25:29.882 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'barService'

     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     % 为ServiceA.methodA() 创建事务 %
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     20:25:29.942 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Creating new transaction with name [com.yitaqi.v2.service.impl.DefaultFooService.getFoo]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.RuntimeException

     20:25:30.257 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] for JDBC transaction
     20:25:30.261 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] to manual commit

     ###############################################################################################
     # ServiceB.methodB() 传播行为 Propagation.NOT_SUPPORTED 不知事务 它会挂起ServiceA.methodA()的事务 #
     ###############################################################################################
     20:25:30.263 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Suspending current transaction

     ########################################################################
     # ServiceB.methodB() 以事务模式执行完毕后，重新执行ServiceA.methodA()的事务 #
     ########################################################################

     20:25:30.264 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Resuming suspended transaction after completion of inner transaction
     20:25:30.264 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction rollback
     20:25:30.264 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a]
     20:25:30.266 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@20049680 wrapping com.mysql.cj.jdbc.ConnectionImpl@8297b3a] after transaction
     * @param fo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = {RuntimeException.class})
    @Override
    public int insertBarWithPropagationNotSuuported(Foo fo) {
        return 0;
    }


    @Transactional(propagation = Propagation.NEVER, rollbackFor = {RuntimeException.class})
    @Override
    public int insertBarWithPropagationNever(Foo fo) {
        return 0;
    }


}
