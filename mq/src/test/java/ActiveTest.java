import b.h.z.active.ActiveConfig;
import b.h.z.active.MessageBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;
import java.time.LocalDateTime;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/11 15:16
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ActiveConfig.class)
public class ActiveTest {

    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    private JmsTemplate jmsTopicTemplate;

    @Autowired
    private ActiveMQQueue activeMQQueue;

    @Autowired
    private ActiveMQTopic activeMQTopic;

    @Test
    public void sendQueueMessage() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            jmsQueueTemplate.send(activeMQQueue, session -> {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                textMessage.setText(String.valueOf(finalI));
                return textMessage;
            });
        }

        while (true) {}
    }

    @Test
    public void sendQueueObject() {
        jmsQueueTemplate.convertAndSend(activeMQQueue, new MessageBean());
    }

    @Test
    public void sendTopicMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200L);
            jmsTopicTemplate.send(activeMQTopic, session -> {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("==>>" + LocalDateTime.now());
                return textMessage;
            });
        }

    }
}
