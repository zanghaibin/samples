package b.h.z.api;

import b.h.z.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;

import java.net.UnknownHostException;

/**
 * @author ：zanghaibin
 * @description：get api
 * @date ：Created in 2020/7/28 14:58
 */
public class GetApi {

    private static Logger logger= LogManager.getLogger(GetApi.class);

    public static GetResponse execute() throws UnknownHostException {


        GetResponse response = Client.getInstance().prepareGet("test", "_doc", "1").get();
        logger.info(response.getSource());
        return response;
    }
}
