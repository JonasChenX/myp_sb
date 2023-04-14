package com.jonas.myp_sb.example.security;

import com.jonas.myp_sb.login.LoginUser;
import com.jonas.myp_sb.login.User;
import com.jonas.myp_sb.login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //查詢用戶訊息
        User user = userRepository.findByUserName(userName);
        //如果沒有查到用戶名則拋出異常
        if(Objects.isNull(user)){
            throw new RuntimeException("帳號或密碼錯誤");
        }
        //查詢對應的權限訊息
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("test","admin"));

        //把資料封裝成UserDetails返回
        return new LoginUser(user, arrayList);
    }
}
