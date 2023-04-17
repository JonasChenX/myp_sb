package com.jonas.myp_sb.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class sqlTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void queryTest(){
        List<Map<String, Object>> byIdJoinRoleAndUserAndMenu = userRepository.findByIdJoinRoleAndUserAndMenu(1L);
    }

}
