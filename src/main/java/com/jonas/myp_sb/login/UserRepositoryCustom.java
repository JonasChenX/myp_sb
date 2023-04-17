package com.jonas.myp_sb.login;

import java.util.List;
import java.util.Map;

public interface UserRepositoryCustom {
    List<Map<String, Object>> findByIdJoinRoleAndUserAndMenu(Long id);
}
