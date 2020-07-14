package b.h.z.active.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * active链接信息配置
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/9 14:30
 */
@Data
@PropertySource(value = "classpath:activemq.properties")
@Configuration
public class ActiveProperty {

    @Value("${activemq.addresses}")
    private String addresses;

    @Value("${activemq.username}")
    private String username;

    @Value("${activemq.password}")
    private String password;


}
