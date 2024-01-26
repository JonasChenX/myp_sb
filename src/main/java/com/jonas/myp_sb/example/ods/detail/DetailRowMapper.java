package com.jonas.myp_sb.example.ods.detail;

import com.jonas.myp_sb.executionUtil.insertTestData.Detail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailRowMapper implements RowMapper<Detail> {
    @Override
    public Detail mapRow(ResultSet resultSet, int i) throws SQLException {
        Detail detail = new Detail();
        detail.setId(resultSet.getInt("id"));
        detail.setBan(resultSet.getString("ban"));
        detail.setBanAddr(resultSet.getString("ban_addr"));
        detail.setBanNm(resultSet.getString("ban_nm"));
        detail.setC11(resultSet.getString("c11"));
        detail.setInvCnt(resultSet.getString("inv_cnt"));
        detail.setTaxJurisCd(resultSet.getString("tax_juris_cd"));
        detail.setVatLosn(resultSet.getString("vat_losn"));
        detail.setOrgCd(resultSet.getString("org_cd"));
        // 可根據實際情況繼續映射其他欄位

        return detail;
    }
}
