package b.h.z.api;

import b.h.z.Client;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * @author ：zanghaibin
 * @description：index api demo
 * @date ：Created in 2020/7/28 14:15
 */
public class IndexApi {

    public static IndexResponse execute() throws IOException {

        /**
         *
         */
        IndexResponse response = Client.getInstance().prepareIndex("test", "_doc", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "zanghaibin")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                ).get();

        return response;

    }

}
