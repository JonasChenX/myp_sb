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
     *
     * @param sql      處理之sql
     * @param model 前端參數
     */
    public String filterOptionalParams(String sql, Map<String, Object> model) {
        Map<String, Object> sqlModel = toVariableModel(model);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        //1. 取得各 --optional start 到 end 片段
        //2. optional 片段處理
        //  2.1. 取得 optional 片段參數
        //  2.2. 檢查參數都是否存在 sqlModel(考慮種類一、二共通處理方式，若有困難就寫死 同群 對應方式)
        //      種類一： 選填
        //      種類二： 同群排除行業別，二擇一情境
        //    2.2.1. 存在則不做處理
        //    2.2.2. 其中一個不存在則 [從 sql中移除該 optional 片段]
        log.info("------filterOptionalParams-------with sqlModel:'{}',and sql:'{}' ", sqlModel.toString(), sql);
        // sql 存在 --optional 才開始處理
        if (sql.contains("--optional")) {
            // 行業代號參數處理
            String bscd = Objects.nullSafeToString(sqlModel.get("BSCD"));
            if (!("{}".equals(bscd) || "null".equals(bscd))) {

                Map<String, Object> bscdMap = objectMapper.convertValue(sqlModel.get("BSCD"), typeFactory.constructMapType(Map.class, String.class, Object.class));

                List<String> bscdList = objectMapper.convertValue(bscdMap.get("resultsArray"), typeFactory.constructCollectionType(List.class, String.class));
                sqlModel.put("BSCD", bscdList);
                Boolean isExclude = objectMapper.convertValue(bscdMap.get("isExclude"), Boolean.class);
                sqlModel.put("isExclude", isExclude);
                log.debug("sqlModel:'{}'", sqlModel.toString());
            }
            //重要原因代號
            String importantChangeReasonCode = Objects.nullSafeToString(sqlModel.get("importantChangeReasonCode"));
            if (
                    !(
                            "{}".equals(importantChangeReasonCode) ||
                                    "null".equals(importantChangeReasonCode) ||
                                    "[]".equals(importantChangeReasonCode)
                    )
            ) {
                Map<String, Object> reasonCodeMap = objectMapper.convertValue(sqlModel.get("importantChangeReasonCode"), typeFactory.constructMapType(Map.class, String.class, Object.class));
                List<String> reasonCodeList = objectMapper.convertValue(reasonCodeMap.get("resultsArray"), typeFactory.constructCollectionType(List.class, String.class));
                sqlModel.put("importantChangeReasonCode", reasonCodeList);
                Boolean isExclude2 = objectMapper.convertValue(reasonCodeMap.get("isExclude2"), Boolean.class);
                sqlModel.put("isExclude2", isExclude2);
                log.info("sqlModel:'{}'", sqlModel.toString());
            }

            log.info("---contains optional sql variable---");
            Pattern optionalPattern = Pattern.compile("(--optional.*?--optionalend)", Pattern.DOTALL | Pattern.MULTILINE);
            Matcher optionalMatcher = optionalPattern.matcher(sql);
            while (optionalMatcher.find()) {
                String matchStr = optionalMatcher.group(1);
                log.info("matchStr:'{}'", matchStr);
                String optionsString = StringUtils.substringBetween(matchStr, "--optional", "LEFT");
                if (Strings.isNullOrEmpty(optionsString)) {
                    // and為小寫時
                    optionsString = StringUtils.substringBetween(matchStr, "--optional", "AND");
                    if (Strings.isNullOrEmpty(optionsString)) {
                        // and為小寫時
                        optionsString = StringUtils.substringBetween(matchStr, "--optional", "and");
                        if (Strings.isNullOrEmpty(optionsString)) {
                            // optional句為having
                            optionsString = StringUtils.substringBetween(matchStr, "--optional", "having");
                            if (Strings.isNullOrEmpty(optionsString)) {
                                // optional句為,
                                optionsString = StringUtils.substringBetween(matchStr, "--optional", ",");
                                if (Strings.isNullOrEmpty(optionsString)) {
                                    // or為小寫
                                    optionsString = StringUtils.substringBetween(matchStr, "--optional", "or");
                                }
                            }
                        }
                    }
                }
                List<String> optionsList = Arrays.asList(optionsString.split(":"));
                List<Boolean> flagList = new LinkedList<>();
                //行業勾選sql中optional字串
                AtomicReference<String> isExcludeOption = new AtomicReference<>("");
                //重要原因代號勾選sql中optional字串
                AtomicReference<String> isExclude2Option = new AtomicReference<>("");
                //前端傳入行業勾選flag
                AtomicReference<String> isExcludeFlag = new AtomicReference<>("init");
                //前端傳入重要原因代號勾選flag
                AtomicReference<String> isExclude2Flag = new AtomicReference<>("init");
                //>0情境之sql 處理
                AtomicReference<String> additionOption = new AtomicReference<>("");

                optionsList.forEach(
                        option -> {
                            // 排除掉split後為空的option
                            log.info("option:'{}'", option);

                            if (!Strings.isNullOrEmpty(option)) {
                                //行業、稅籍勾選特別處理
                                if (option.contains("isExclude")) {
                                    if (option.contains("2")) {
                                        //重要原因代號
                                        isExclude2Option.set(option);
                                        isExclude2Flag.set(Objects.nullSafeToString(sqlModel.get("isExclude2")));
                                    } else {
                                        //行業代號
                                        isExcludeOption.set(option);
                                        isExcludeFlag.set(Objects.nullSafeToString(sqlModel.get("isExclude")));
                                    }
                                } else if (option.contains("!") && !option.contains("isExclude")) {
                                    additionOption.set(option);
                                } else {
                                    //  一般情況
                                    boolean containsKey = sqlModel.containsKey(option.trim());
                                    String sqlModelValue = Objects.nullSafeToString(sqlModel.get(option.trim()));
                                    log.info("key:{}",option.trim());
                                    log.info("value:{}",option.trim());
                                    log.info("sqlModelValue:'{}'", sqlModelValue);

                                    // 如果有傳入此參數 且非空值與null
                                    if (
                                            containsKey &&
                                                    (
                                                            !Strings.isNullOrEmpty(sqlModelValue) &&
                                                                    !"null".equals(sqlModelValue) &&
                                                                    !"{}".equals(sqlModelValue) &&
                                                                    !"[]".equals(sqlModelValue)
                                                    )
                                    ) {
                                        flagList.add(true);
                                    } else {
                                        flagList.add(false);
                                    }
                                }
                            }
                        }
                );
                // 判斷參數存在flag
                boolean flag = !flagList.contains(false);
                if (!flag) {
                    // 有任一參數不存在 則移除optional sql
                    sql = StringUtils.remove(sql, matchStr);
                    log.info("remove option:" + matchStr);
                } else {
                    System.err.println(additionOption);
                    //   1.!參數處理
                    if (!"".equals(additionOption.get())) {
                        String addition = additionOption.get().split("!")[1].trim();
                        if (sqlModel.get(addition) != null) {
                            sql = StringUtils.remove(sql, matchStr);
                        }
                    }
                    //   2.行業別特別處理
                    //   當參數都存在時判斷要啟用哪一個optional(勾選、未勾選)

                    // 如果isExcludeFlag 傳入為空或null
                    if ((Strings.isNullOrEmpty(isExcludeFlag.get()) || "null".equals(isExcludeFlag.get()))) {
                        sql = StringUtils.remove(sql, matchStr);
                    }
                    // 如果isExcludeFlag 傳入為false 移除 IN(:...)
                    if (
                            "@isExclude".equals(isExcludeOption.get()) &&
                                    "false".equals(isExcludeFlag.get())
                    ) {
                        // 未勾選移除IN (:isExclude)
                        sql = StringUtils.remove(sql, matchStr);
                    }
                    // 如果isExclude2Flag 傳入為false 移除 IN(:...)
                    if (
                            "@isExclude2".equals(isExclude2Option.get()) &&
                                    "false".equals(isExclude2Flag.get())
                    ) {
                        // 未勾選移除IN (:isExclude2)
                        sql = StringUtils.remove(sql, matchStr);
                    }
                    // 如果isExcludeFlag 傳入為true 移除 NOT IN(:...)
                    if (
                            "@!isExclude".equals(isExcludeOption.get()) &&
                                    "true".equals(isExcludeFlag.get())
                    ) {
                        // 有勾選移除NOT IN (:isExclude)
                        sql = StringUtils.remove(sql, matchStr);
                    }
                    // 如果isExclude2Flag 傳入為true 移除 NOT IN(:...)
                    if (
                            "@!isExclude2".equals(isExclude2Option.get()) &&
                                    "true".equals(isExclude2Flag.get())
                    ) {
                        // 有勾選移除NOT IN (:isExclude2)
                        sql = StringUtils.remove(sql, matchStr);
                    }
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
