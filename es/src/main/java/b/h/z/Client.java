package b.h.z;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ：zanghaibin
 * @description：TransportClient客户端
 * @date ：Created in 2020/7/28 14:05
 *
 */
@Configuration
public class Client {

    public static TransportClient getInstance() throws UnknownHostException {
        return new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.250"), 9300));
    }

    public static void main(String[] args) throws UnknownHostException {
        // TransportClient已经废弃
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.250"), 9300));
        // on shutdown
        client.close();
    }
}
