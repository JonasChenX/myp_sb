package com.jonas.myp_sb.example.api;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class rowMapper implements RowMapper<Model> {

    @Override
    public Model mapRow(ResultSet resultSet, int i) throws SQLException {
        Model model = new Model();
        model.setId(resultSet.getInt("id"));
        model.setDataYr(resultSet.getDate("data_yr"));
        model.setLabel(resultSet.getString("label"));
        model.setDataUpdateTime(resultSet.getTime("data_update_time"));
        model.setJsonData(resultSet.getString("json_data"));

        return model;
    }
}
