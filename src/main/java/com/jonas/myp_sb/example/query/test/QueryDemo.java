package com.jonas.myp_sb.example.query.test;

import com.jonas.myp_sb.example.ioDemo.Resources;
import com.jonas.myp_sb.example.query.Query;
import com.jonas.myp_sb.example.query.SqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QueryDemo {

    @Autowired
    private final SqlExecutor sqlExecutor;

    @Autowired
    private final Resources resources;

    public QueryDemo(SqlExecutor sqlExecutor, Resources resources) {
        this.sqlExecutor = sqlExecutor;
        this.resources = resources;
    }

    @GetMapping("/testSQL1")
    public List<testMapper> testSQL1(){
        /**
         * 直接SQL語句查詢
         * 查到的資料映射到該物件中
         * */
        Query query = resources.readAsQuery("classpath:ioDemo/readSQL.sql");
        List<testMapper> testMappers = sqlExecutor.queryForList(query, testMapper.class);
        return testMappers;
    }

    @GetMapping("/testSQL2")
    public List<testMapper> testSQL2(){
        /**
         * 直接SQL語句查詢 並帶入對應的參數
         * 查到的資料映射到該物件中
         * */
        Map<String, Object> parameter = new HashMap();
        parameter.put("id", 1);
        Query query = resources.readAsQueryBuilder("classpath:ioDemo/readSQL2.sql")
                .putAll(parameter)
                .build();
        List<testMapper> testMappers = sqlExecutor.queryForList(query, testMapper.class);
        return testMappers;
    }

    @GetMapping("/testSQL3")
    public String testSQL3(){

        /**
         * 添加SQL語句 並依序帶入參數
         * 更新該筆資料
         * */

        String setLabel = "測試測試";
        final Query query = Query
                .builder()
                .append(" UPDATE demo_test SET label = :setLabel WHERE id = :id ", setLabel, 1)
                .build();
        sqlExecutor.update(query);

        return "更新成功";
    }

}
