package com.jonas.myp_sb.example.security;

import com.jonas.myp_sb.login.LoginUser;
import com.jonas.myp_sb.login.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("SG")
public class SGExpressionRoot {

    /**
     * 自定義權限校驗邏輯
     * @PreAuthorize("@SG.hasAuthority()")
     * 前面添加@該組件的名稱會調用該組件
     * */

    public boolean hasAuthority(String authority){
        //獲取當前用戶的權限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();

        //判斷用戶權限集合中是否存在authority
        return permissions.contains(authority);
    }
}
