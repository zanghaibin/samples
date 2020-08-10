package b.h.z.neo4j.repository;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/1/9 15:14
 */
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import b.h.z.neo4j.entity.UserNode;

import java.util.List;

@Component
public interface UserRepository extends Neo4jRepository<UserNode, Long> {

    @Query("MATCH (n:User) RETURN n ")
    List<UserNode> getUserNodeList();

    @Query("create (n:User{age:{age},name:{name}}) RETURN n ")
    List<UserNode> addUserNodeList(@Param("name") String name, @Param("age")int age);
}
