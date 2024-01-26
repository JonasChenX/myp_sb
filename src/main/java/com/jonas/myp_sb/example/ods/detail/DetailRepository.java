package com.jonas.myp_sb.example.ods.detail;

import com.jonas.myp_sb.executionUtil.insertTestData.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DetailRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DetailRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Detail> queryDetail(int startRow, int size, String orgCd) {
        try {
            String sql = "SELECT * FROM myp.detail where org_cd = :orgCd ORDER BY id LIMIT :size OFFSET :startRow";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("orgCd", orgCd)
                    .addValue("size", size)
                    .addValue("startRow", startRow);

            return jdbcTemplate.query(sql, params, new DetailRowMapper());
        } catch (Exception ex) {
            // handle exception
            ex.printStackTrace();
            return null;
        }
    }

    public Long getCountByOrgCd(String orgCd) {
        String sql = "SELECT COUNT(*) FROM myp.detail WHERE org_cd = :orgCd";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgCd", orgCd);

        // 執行 SQL 查詢，並將結果轉換為整數
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
