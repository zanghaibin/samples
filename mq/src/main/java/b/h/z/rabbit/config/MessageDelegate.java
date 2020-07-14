package b.h.z.rabbit.config;

import b.h.z.rabbit.bean.ProductManager;
import b.h.z.rabbit.bean.Programmer;

/**
 * @author ：zanghaibin
 * @description：消息委派处理类
 * @date ：Created in 2020/7/9 14:38
 */
public class MessageDelegate {

    public void handleMessage(ProductManager manager) {
        System.out.println("收到一个产品经理" + manager);
    }

    public void handleMessage(Programmer programmer) {
        System.out.println("收到一个程序员" + programmer);
    }

}
