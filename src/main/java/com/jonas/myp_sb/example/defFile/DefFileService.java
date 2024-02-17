package com.jonas.myp_sb.example.defFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import io.jsonwebtoken.lang.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class DefFileService {

    @Autowired
    private final ObjectMapper objectMapper;


    public DefFileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 處理選填之sql
     * @param sql   處理之sql
     * @param model sql變數取代
     * 邏輯:
     *     取--optional:${變數} ... --optionalend 內容
     *     依照model的key判斷是否有值
     *     若沒有該key或該key的值為null空值 則把該內容則刪除
     *     有的話繼續保留
     */
    public String filterOptionalParams(String sql, Map<String, Object> model) {
        Map<String, Object> sqlModel = toVariableModel(model);

        log.info("filterOptionalParams:{}", sqlModel);
        // sql 存在 --optional 才開始處理
        if (sql.contains("--optional")) {

            Pattern optionalPattern = Pattern.compile("(--optional.*?--optionalend)", Pattern.DOTALL | Pattern.MULTILINE);
            Matcher optionalMatcher = optionalPattern.matcher(sql);
            while (optionalMatcher.find()) {
                String modelKey = null;

                //取得optional片段
                String matchStr = optionalMatcher.group(1);
                log.info("matchStr:'{}'", matchStr);

                //取得--optional: 後面的變數
                int colonIndex = matchStr.indexOf("--optional:");
                int newlineIndex = matchStr.indexOf("\n", colonIndex);
                if (colonIndex != -1 && newlineIndex != -1) {
                    modelKey = matchStr.substring(colonIndex + 11, newlineIndex).trim();
                }
                log.info("modelKey:{}",modelKey);

                //必須要有modelKey
                if(modelKey == null){
                    throw new IllegalStateException("modelKey is null");
                }

                //預設刪除
                Boolean isDel = true;
                if(sqlModel.keySet().contains(modelKey) && sqlModel.get(modelKey) != null){
                    //若是string 則不能為null或空值
                    if(sqlModel.get(modelKey) instanceof String || sqlModel.get(modelKey) instanceof Number){
                        log.info("modelKey是String或Number:{}",modelKey);
                        String modelValueStr = String.valueOf(sqlModel.get(modelKey));
                        if(StringUtils.isNotBlank(modelValueStr)){
                            isDel = false;
                        }
                    }
                    //若是List 則不能為null或空陣列
                    if(sqlModel.get(modelKey) instanceof List){
                        log.info("modelKey是List:{}",modelKey);
                        List modelValueList = (List) sqlModel.get(modelKey);
                        if(modelValueList.size() > 0){
                            isDel = false;
                        }
                    }
                    //若是Boolean 若為true則不刪除
                    if(sqlModel.get(modelKey) instanceof Boolean){
                        log.info("modelKey是Boolean:{}",modelKey);
                        Boolean modelValueBoolean = (Boolean) sqlModel.get(modelKey);
                        if(modelValueBoolean){
                            isDel = false;
                        }
                    }
                }
                if(isDel){
                    sql = StringUtils.remove(sql, matchStr);
                }
            }
        }
        return sql;
    }

    private Map<String, Object> toVariableModel(Map<String, Object> defValue){
        Map<String, Object> sqlModel = new HashMap<String, Object>();
        log.info("defModel:'{}'", defValue.get("model").toString());
        Map<String, Map<String, Object>> variableModel = objectMapper.convertValue(defValue.get("model"), Map.class);
        for (String varKey : variableModel.keySet()) {
            Object sqlVal = variableModel.get(varKey);

            if (sqlModel.get(varKey) == null) {
                sqlModel.put(varKey, sqlVal);
            } else {
                log.error(String.format("變數有重複名稱，請檢查：%s", varKey));
            }
        }
        return sqlModel;
    }


}
