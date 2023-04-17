package com.jonas.myp_sb.login;

import com.jonas.myp_sb.example.ioDemo.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    @Autowired
    private Resources resources;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepositoryCustomImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> findByIdJoinRoleAndUserAndMenu(Long id) {
        String sysUserPermsSql = resources.readAsString("classpath:sql/FindSysUserPerms.sql");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", id);
        log.info("sql參數:\n" + paramMap);
        List<Map<String, Object>> maps = namedParameterJdbcTemplate.queryForList(sysUserPermsSql, paramMap);
        log.info("查詢結果:\n"+maps);

        return maps;
    }
}
