package b.h.z.rabbit;

import b.h.z.rabbit.config.RabbitProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zanghaibin
 * @description：rabbit基础配置
 * @date ：Created in 2020/7/9 14:31
 */
@Configuration
@ComponentScan("b.h.z.rabbit")
public class RabbitBaseConfig {

    /**
     * 声明连接工厂
     */
    @Bean
    public ConnectionFactory connectionFactory(RabbitProperty property) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(property.getAddresses());
        connectionFactory.setUsername(property.getUsername());
        connectionFactory.setPassword(property.getPassword());
        connectionFactory.setVirtualHost(property.getVirtualhost());
        return connectionFactory;
    }

    /**
     * 创建一个管理器（org.springframework.amqp.rabbit.core.RabbitAdmin），用于管理交换，队列和绑定。
     * auto-startup 指定是否自动声明上下文中的队列,交换和绑定, 默认值为 true。
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}