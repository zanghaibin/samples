package b.h.z.neo4j.service;

import java.util.List;

import b.h.z.neo4j.entity.UserNode;
import b.h.z.neo4j.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Neo4jService {

    @Autowired
    private UserRepository userRepository;

    public int addUser(UserNode userNode) {
        userRepository.addUserNodeList(userNode.getName(), userNode.getAge());
        return 1;
    }

    public List<UserNode> getUserNodeList() {
        return userRepository.getUserNodeList();
    }
}