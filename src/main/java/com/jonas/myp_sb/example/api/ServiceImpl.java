package com.jonas.myp_sb.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceImpl implements ServiceTest {

    @Autowired
    private Dao dao;

    @Override
    public Model getModelById(Integer id) {
        return dao.getModelById(id);
    }
}
