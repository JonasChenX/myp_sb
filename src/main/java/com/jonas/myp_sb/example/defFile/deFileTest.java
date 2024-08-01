package com.jonas.myp_sb.example.defFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jonas.myp_sb.example.ioDemo.Resources;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class deFileTest {

    private final Resources resources;

    private final DefFileService defFileService;

    @Autowired
    public deFileTest(Resources resources, DefFileService defFileService) {
        this.resources = resources;
        this.defFileService = defFileService;
    }

    @Test
    public void test() throws JsonProcessingException {
        //獲取a.sql內容
        String sql = resources.readAsString("classpath:deFile/a.sql");

        //獲取model.json 並將參數轉成map
        ObjectMapper mapper = new ObjectMapper();
        String modelJson = resources.readAsString("classpath:deFile/model.json");
        ObjectNode root = (ObjectNode) mapper.readTree(modelJson);
        ObjectNode modelMap = (ObjectNode) root.get("model");
        Map<String, Object> ParamsMap = new HashMap<>();
        ParamsMap.put("model", modelMap);

        //帶入 sql內容 和 參數
        //若該參數沒有值則會篩選掉
        String filterSql = defFileService.filterOptionalParams(sql, ParamsMap);
        String addWhereSql = defFileService.addWhereParams(filterSql, ParamsMap);
        log.info("addWhereSql:{}", addWhereSql);
    }
}
