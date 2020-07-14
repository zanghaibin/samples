package b.h.z.rabbit.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zanghaibin
 * @description：声明交换机、队列、绑定关系和消费者监听器
 * @date ：Created in 2020/7/9 14:32
 */
@Configuration
public class RabbitBaseAnnotation {

    @Bean
    public TopicExchange exchange() {
        // 创建一个持久化的交换机
        return new TopicExchange("topic01", true, false);
    }

    @Bean
    public Queue firstQueue() {
        // 创建一个持久化的队列 1
        return new Queue("FirstQueue", true);
    }

    @Bean
    public Queue secondQueue() {
        // 创建一个持久化的队列 2
        return new Queue("SecondQueue", true);
    }

    /**
     * BindingKey 中可以存在两种特殊的字符串“#”和“*”，其中“*”用于匹配一个单词，“#”用于匹配零个或者多个单词
     * 这里我们声明三个绑定关系用于测试 topic 这种类型交换器
     */
    @Bean
    public Binding orange() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with("*.orange.*");
    }

    @Bean
    public Binding rabbit() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("*.*.rabbit");
    }

    @Bean
    public Binding lazy() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("lazy.#");
    }

    /**
     * 创建队列 1 消费者监听
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer firstQueueLister(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置监听的队列
        container.setQueues(firstQueue());
        // 指定要创建的并发使用者数。
        container.setConcurrentConsumers(1);
        // 设置消费者数量的上限
        container.setMaxConcurrentConsumers(5);
        // 设置是否自动签收消费 为保证消费被成功消费，建议手工签收
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                // 可以在这个地方得到消息额外属性
                MessageProperties properties = message.getMessageProperties();
                //得到消息体内容
                byte[] body = message.getBody();
                System.out.println(firstQueue().getName() + "收到消息:" + new String(body));
                /*
                 * DeliveryTag 是一个单调递增的整数
                 * 第二个参数 代表是否一次签收多条，如果设置为 true,则所有 DeliveryTag 小于该 DeliveryTag 的消息都会被签收
                 */
                channel.basicAck(properties.getDeliveryTag(), false);
            }
        });
        return container;
    }


    /**
     * 创建队列 2 消费者监听
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer secondQueueLister(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(secondQueue());
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println(secondQueue().getName() + "收到消息:" + new String(body));
            }
        });
        return container;
    }

}
