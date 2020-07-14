import b.h.z.rabbit.RabbitBaseConfig;
import b.h.z.rabbit.bean.ProductManager;
import b.h.z.rabbit.bean.Programmer;
import b.h.z.rabbit.constant.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author ：zanghaibin
 * @description：传输对象
 * @date ：Created in 2020/7/9 20:07
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RabbitBaseConfig.class)
public class RabbitSendObjectTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendProgrammer() throws JsonProcessingException {
        MessageProperties messageProperties = new MessageProperties();
        //必须设置 contentType为 application/json
        messageProperties.setContentType("application/json");
        // 必须指定类型
        messageProperties.getHeaders().put("__TypeId__", Type.PROGRAMMER);
        Programmer programmer = new Programmer("xiaoming", 34, 52200.21f, new Date());
        // 序列化与反序列化都使用的Jackson
        ObjectMapper mapper = new ObjectMapper();
        String programmerJson = mapper.writeValueAsString(programmer);
        Message message = new Message(programmerJson.getBytes(), messageProperties);
        rabbitTemplate.send("objectTopic", "object", message);
    }


    @Test
    public void sendProductManager() throws JsonProcessingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__", Type.MANAGER);
        ProductManager manager = new ProductManager("xiaohong", 21, new Date());
        ObjectMapper mapper = new ObjectMapper();
        String managerJson = mapper.writeValueAsString(manager);
        Message message = new Message(managerJson.getBytes(), messageProperties);
        rabbitTemplate.send("objectTopic", "object", message);
    }
}
