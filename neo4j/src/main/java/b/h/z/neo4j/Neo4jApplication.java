package b.h.z.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/1/9 15:27
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class Neo4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }
}
