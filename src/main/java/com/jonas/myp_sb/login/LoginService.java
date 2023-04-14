package com.jonas.myp_sb.login;

import java.util.Map;

public interface LoginService {
    Map<String, String> login(User user);

    String logout();
}
