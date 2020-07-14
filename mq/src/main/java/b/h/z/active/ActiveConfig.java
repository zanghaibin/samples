package b.h.z.active;

import b.h.z.active.config.ActiveProperty;
import b.h.z.active.consumer.QueueMsgListener;
import b.h.z.active.consumer.TopicMsgListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.jms.Session;


/**
 * activemq 基础配置
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/11 15:07
 */
@Configuration
@ComponentScan("b.h.z.active")
public class ActiveConfig {


    @Bean
    public CachingConnectionFactory connectionFactory(ActiveProperty activeProperty, RedeliveryPolicy activeMQRedeliveryPolicy) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setUserName(activeProperty.getUsername());
        connectionFactory.setPassword(activeProperty.getPassword());
        connectionFactory.setBrokerURL(activeProperty.getAddresses());
        connectionFactory.setRedeliveryPolicy(activeMQRedeliveryPolicy);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        cachingConnectionFactory.setSessionCacheSize(100);
        return cachingConnectionFactory;
    }

    /**
     * 非pub/sub模型（发布/订阅），即队列模式
     * @param connectionFactory
     * @param jmsMessageConverter
     * @return
     */
    @Bean
    public JmsTemplate jmsQueueTemplate(CachingConnectionFactory connectionFactory, MessageConverter jmsMessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.setMessageConverter(jmsMessageConverter);
        // 手动签收
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return jmsTemplate;
    }

    /**
     * pub/sub模型（发布/订阅）
     * @param connectionFactory
     * @param jmsMessageConverter
     * @return
     */
    @Bean
    public JmsTemplate jmsTopicTemplate(CachingConnectionFactory connectionFactory, MessageConverter jmsMessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(jmsMessageConverter);
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return jmsTemplate;
    }

    /**
     * 消息处理器
     * @return
     */
    @Bean
    public MessageConverter jmsMessageConverter() {
        MessageConverter messageConverter = new SimpleMessageConverter();
        return messageConverter;
    }

    /**
     * 配置队列目的地
     * @return
     */
    @Bean
    public ActiveMQQueue activeMQQueue(){
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("QUEUE_NAME");
        return activeMQQueue;
    }

    /**
     * 配置主题目的地
     * @return
     */
    @Bean
    public ActiveMQTopic activeMQTopic(){
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("TOPIC_NAME");
        return activeMQTopic;
    }


    /**
     * 配置topic监听容器
     * @param connectionFactory
     * @param topicMsgListener
     * @param activeMQTopic
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(CachingConnectionFactory connectionFactory, TopicMsgListener topicMsgListener, ActiveMQTopic activeMQTopic){
        return buildMessageListenerContainer(connectionFactory, topicMsgListener, activeMQTopic);
    }

    /**
     * 配置queue监听容器
     * @param connectionFactory
     * @param queueMsgListener
     * @param activeMQQueue
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainerQueue(CachingConnectionFactory connectionFactory, QueueMsgListener queueMsgListener, ActiveMQQueue activeMQQueue){
        return buildMessageListenerContainer(connectionFactory, queueMsgListener, activeMQQueue);
    }

    private DefaultMessageListenerContainer buildMessageListenerContainer(CachingConnectionFactory connectionFactory, MessageListener listener, Destination destination) {
        //创建容器
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        //设置监听器
        defaultMessageListenerContainer.setMessageListener(listener);
        //设置连接工厂
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        //设置监听目的地
        defaultMessageListenerContainer.setDestination(destination);

        //
        defaultMessageListenerContainer.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return defaultMessageListenerContainer;
    }

    /**
     * 定义ReDelivery（重发机）机制
     * @return
     */
    @Bean
    public RedeliveryPolicy activeMQRedeliveryPolicy(ActiveMQQueue activeMQQueue) {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();

        redeliveryPolicy.setDestination(activeMQQueue);
        // 是否每次尝试重新发送失败后，增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重发时间间隔，默认1秒
        redeliveryPolicy.setInitialRedeliveryDelay(3000);
        // 第一次失败后重新发送之前等待500毫秒，第二次失败再等待 500*2 毫秒，这里的2是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //  最大传送延迟，只在useExponentialBackOff为true时有效（V5.5），假设首次重连间隔为10ms 倍数为2，
        //            那么第二次重连时间间隔为20ms，第三次重连时间间隔为40ms，
        //            当重连时间间隔大的最大重连时间间隔时，以后每次重连时间间隔散为最大重连时间间隔。
        redeliveryPolicy.setMaximumRedeliveryDelay(1000);
        return redeliveryPolicy;
    }
}

