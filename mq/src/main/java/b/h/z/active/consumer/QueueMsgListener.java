package b.h.z.active.consumer;

import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/7/11 16:29
 */
@Component
public class QueueMsgListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage)message;
        try {
            System.out.println(objectMessage.getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        /*TextMessage textMessage = (TextMessage)message;
        try {
            String text = textMessage.getText();
            System.out.println("监听到的消息：" + textMessage.getText());
            textMessage.acknowledge();
        } catch (JMSException e) {
            // 处理失败 指的是MessageListener的onMessage方法里抛出RuntimeException
            throw new RuntimeException(e.toString());
        }*/
    }
}
