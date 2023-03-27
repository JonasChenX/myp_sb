package com.jonas.myp_sb.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DaoImpl implements Dao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Model getModelById(Integer id) {
        String sql = "select * from demo_test where id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<Model> modelList = namedParameterJdbcTemplate.query(sql, map, new rowMapper());
        if(modelList.size() > 0){
            return modelList.get(0);
        }
        return null;

    }
}
