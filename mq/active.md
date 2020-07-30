安装参考链接：
https://www.cnblogs.com/Rozdy/p/11497324.html

**配置信息**

######jetty-realm.properties配置管理控制台用户

用户名:密码,角色

`# Defines users that can access the web (console, demo, etc.)`

`# username: password [,rolename ...]`

`admin: admin, admin`

`user: user, user`

https://blog.csdn.net/qq_26975307/article/details/100705355?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase


XML方式整合配置：

`
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jms="http://www.springframework.org/schema/jms"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
           http://www.springframework.org/schema/aop 
           http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
           http://www.springframework.org/schema/tx
     	   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context-3.0.xsd
           http://www.springframework.org/schema/jms 
           http://www.springframework.org/schema/jms/spring-jms-3.0.xsd">

	<!-- ActiveMQ 连接工厂 -->
	<bean id="connectionFactory" class="org.springframework.jms.connection.CachingConnectionFactory">
		<constructor-arg ref="activeMQConnectionFactory" />
		
	</bean>

	<bean id="activeMQConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		<property name="brokerURL" value="tcp://192.168.0.34:61616" />
		<property name="userName" value="middle" />
		<property name="password" value="middle_mq_2020" />
		<property name="redeliveryPolicy" ref="redeliveryPolicy" />
	</bean>
	
	<!-- 定义ReDelivery(重发机制)机制  -->  
    <bean id="redeliveryPolicy" class="org.apache.activemq.RedeliveryPolicy">  
        <!--是否在每次尝试重新发送失败后,增长这个等待时间 -->  
        <property name="useExponentialBackOff" value="true"></property>  
        <!--重发次数,默认为6次   这里设置为1次 -->  
        <property name="maximumRedeliveries" value="6"></property>  
        <!--重发时间间隔,默认为1秒 -->  
        <property name="initialRedeliveryDelay" value="1000"></property>  
        <!--第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value -->  
        <property name="backOffMultiplier" value="2"></property>  
        <!--最大传送延迟，只在useExponentialBackOff为true时有效（V5.5），假设首次重连间隔为10ms，倍数为2，那么第 二次重连时间间隔为 20ms，第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，以后每次重连时间间隔都为最大重连时间间隔。 -->  
        <property name="maximumRedeliveryDelay" value="1000"></property>  
        
       <property name="queue" value="*" />
       
       <!-- <property name="destination" ref="selfApplyDestination" /> -->
        	
    </bean>  

	<!-- 定义JmsTemplate的Queue类型 -->
	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
		<constructor-arg ref="connectionFactory" />
		<property name="messageConverter">
			<bean class="org.springframework.jms.support.converter.SimpleMessageConverter" />
		</property>
		<!-- 非pub/sub模型（发布/订阅），即队列模式 -->
		<property name="pubSubDomain" value="false" />
		<!-- 
			AUTO_ACKNOWLEDGE = 1 ：自动确认 
			CLIENT_ACKNOWLEDGE = 2：客户端手动确认 
			DUPS_OK_ACKNOWLEDGE = 3： 自动批量确认 
			SESSION_TRANSACTED = 0：事务提交并确认 
			INDIVIDUAL_ACKNOWLEDGE = 4：单条消息确认 
		-->
		<property name="sessionAcknowledgeMode" value="2" />
		<!-- deliveryMode, priority, timeToLive 的开关，要生效，必须配置explicitQosEnabled为true，默认false -->
		<property name="explicitQosEnabled" value="true" />
		<!-- 发送模式 DeliveryMode.NON_PERSISTENT=1:非持久 ; DeliveryMode.PERSISTENT=2:持久 -->
		<property name="deliveryMode" value="2" />
	</bean>

	<!-- 消息监听容器 -->
	<bean id="jmsContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
		<property name="connectionFactory" ref="connectionFactory" />
		<!-- <property name="destinationName" ref="selfApplyDestination" /> -->
		<property name="destination" ref="selfApplyDestination" />
		<property name="messageListener" ref="selfApplyListener" />
		<property name="sessionAcknowledgeMode" value="2" />
	</bean>

	<!-- <jms:listener-container destination-type="queue" container-type="default" 
		connection-factory="connectionFactory" acknowledge="client"> <jms:listener 
		destination="archive.statis.queue" ref="selfApplyListener" /> </jms:listener-container> -->

	<!-- 自主申报消息监听 -->
	<bean id="selfApplyListener" class="com.xzkingdee.eval.util.SelfApplyListener" />

	<!-- 目标队列 -->
	<bean id="selfApplyDestination" class="org.apache.activemq.command.ActiveMQQueue">
		<constructor-arg value="SELF_APPLY_REQUEST" />
	</bean>

</beans>



`

`

public class SelfApplyListener implements SessionAwareMessageListener {



	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		try {
			System.out.println(LocalDateTime.now().toString() + "=====>>>" + message);
			String s = null;
			s.toString();
			message.acknowledge();
		} catch (Exception e) {
			session.recover();
		}
		
	}

	
}

public class SelfApplySender {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_activemq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
		ActiveMQQueue targetQueue = (ActiveMQQueue) context.getBean("selfApplyDestination");
		
		jmsTemplate.send(targetQueue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
                textMessage.setText(LocalDateTime.now().toString());
                return textMessage;
			}
		});
	}
}
`