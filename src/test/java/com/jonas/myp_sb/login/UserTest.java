package com.jonas.myp_sb.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestBCryptPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //加密
        String encode1 = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        //每次出來的密文會不一樣
        System.out.println(encode1);
        System.out.println(encode2);
        //校驗(明文and密文 比對是否相同)
        boolean matches = passwordEncoder.matches("1234",
                "$2a$10$CG.S64H0rutzt99klJtLm.ajxdBYTgJHyEgXq6bZGpTHe.FTAnqYO");
        System.out.println(matches);
    }

    @Test
    public void TestUser(){
        User User = userRepository.findByUserName("JonasChen");
        System.out.println(User);
    }
}
