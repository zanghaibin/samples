import b.h.z.Client;
import b.h.z.api.IndexApi;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author ：zanghaibin
 * @description：测试用例
 * @date ：Created in 2020/7/28 14:23
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Client.class)
public class EsTest {
    @Test
    public void indexTest() throws IOException {
        IndexResponse response = IndexApi.execute();
        Assert.assertNotNull(response.getId());
    }
}
