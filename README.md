使用filter拦截器和memcached解决集群环境下java web容器session共享
========================
解决集群环境下java web容器session共享,使用filter拦截器和memcached实现。在tomcat 6和websphere 8测试通过，现网并发2000，日PV量1100万。 暂不支持session event包括create destory 和 attribute change

#使用指南.

配置web.xml 过滤器
<filter>
        <filter-name>springFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetBeanName</param-name>
            <param-value>memcachedSessionFilter</param-value>
        </init-param>
    </filter>

    <filter-mapping>
        <filter-name>springFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
在spring中配置memcached filter
 <bean name="memcachedClient" class="net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean"
              destroy-method="shutdown">
            <property name="servers" value="${memcached.servers}"/>
            <property name="weights">
                <list>
                    <value>1</value>
                    <value>1</value>
                    <value>1</value>
                    <value>1</value>
                </list>
            </property>
            <property name="connectionPoolSize" value="2"/>
            <property name="commandFactory">
                <bean class="net.rubyeye.xmemcached.command.BinaryCommandFactory"/>
            </property>
            <!-- 客户端分布策略(一致性哈希算法),Distributed strategy -->
            <property name="sessionLocator">
                <bean class="net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator"/>
            </property>
            <property name="opTimeout" value="2000"/>
        </bean>
    
   <bean id="memcachedSessionManager" class="com.emagsoftware.web.session.MemcachedSessionManager">
          <property name="memcachedClient" ref="memcachedClient" />
   </bean>
    
    <bean id="memcachedSessionFilter" class="com.emagsoftware.web.session.MemcachedSessionFilter">
       <property name="sessionManager" ref="memcachedSessionManager"/>
    </bean>
