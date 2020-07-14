package b.h.z.active.consumer;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/11 16:29
 */
@Component
public class TopicMsgListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("topic监听者正在监听消息...");
        try {
            System.out.println("topic监听者监听到消息"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
