package b.h.z.neo4j.controller;

import b.h.z.neo4j.entity.UserNode;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

@RestController
public class Neo4jController {

    @Autowired
    private b.h.z.neo4j.service.Neo4jService Neo4jService;

    //创建400个node
    @RequestMapping(path = "/addUserNode", method = RequestMethod.GET)
    public String addUserNode() {
        int i = 0;
        do {
            UserNode user = new UserNode();
            user.setAge(RandomUtils.nextInt(15, 40));
            user.setName("Fredia" + RandomUtils.nextInt(1, 1000));
            user.setUserId(UUID.randomUUID().toString());
            user.setNodeId(RandomUtils.nextLong(1L, 999L));
            Neo4jService.addUser(user);
            i += 1;
        } while (i < 400);

        return "ok";
    }

    @RequestMapping(path = "/getUserNodeList", method = RequestMethod.GET)
    public List<UserNode> getUserNodeList() {
        return Neo4jService.getUserNodeList();
    }
}