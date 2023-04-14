package com.jonas.myp_sb.login;

import com.jonas.myp_sb.example.Jwt.JwtUtil;
import com.jonas.myp_sb.example.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String, String> login(User user) {
        //AuthenticationManager authenticate進行用戶認證
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = null;

        try{
            authenticate = authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException ex) {
            // 密碼錯誤，處理方式
            throw new RuntimeException("登入失敗");
        }catch (LockedException ex) {
            // 帳號被鎖定，處理方式
        }

        //如果認證通過 使用userId生成一個jwt並返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //把完整的用戶存到redis userId作為key
        redisCache.setCacheObject("login:"+userId, loginUser);

        //回傳token
        Map<String, String> result = new HashMap<>();
        result.put("token",jwt);

        return result;
    }
}
