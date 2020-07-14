package b.h.z.rabbit.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * rabbit链接信息配置
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/9 14:30
 */
@Data
@PropertySource(value = "classpath:rabbitmq.properties")
@Configuration
public class RabbitProperty {

    @Value("${rabbitmq.addresses}")
    private String addresses;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.virtualhost}")
    private String virtualhost;
}
