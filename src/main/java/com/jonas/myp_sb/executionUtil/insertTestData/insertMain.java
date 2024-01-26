package com.jonas.myp_sb.executionUtil.insertTestData;

import com.jonas.myp_sb.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@Api(tags = "insert測試資料API")
public class insertMain {

    private final Logger log = LoggerFactory.getLogger(insertMain.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //設定初始化數量
    Integer INIT_DATA_SIZE = 10;

    //定義初始化的物件
    Class CLASS = Detail.class;

    /**
     * 大量insert測試資料
     */
    @GetMapping("/insertTestData")
    @LogAnnotation(methodName = "insert測試資料到DB")
    public String insertTestData(){
        //先初始化資料
        List<Object> data = initData(INIT_DATA_SIZE);

        //將初始化的資料insert
        insertDetails(data, CLASS);

        return "insert完畢";
    }

    private List<Object> initData(int initDataSize){

        List<String> orgCdRandom = List.of("A05", "B01", "D01", "E01", "H01");

        List<Object> result = new ArrayList<>();
        for (int i = 0; i < initDataSize; i++) {

            //初始化的物件
            Detail detail = new Detail();
            detail.setBan(generateRandomNumber(8));
            detail.setBanAddr("地址"+i);
            detail.setBanNm("營業人名稱"+i);
            detail.setC11("d"+i);
            detail.setInvCnt("e"+i);
            detail.setTaxJurisCd("f"+i);
            detail.setVatLosn("g"+i);
            detail.setOrgCd(getRandomValue(orgCdRandom));

            result.add(detail);
        }
        return result;
    }

    /**
     *
     * @param dataList 要insert的資料
     * @param objClass insert的物件
     */
    private void insertDetails(List<Object> dataList, Class<?> objClass) {
        //存放資料庫的欄位
        List<String> dbFieldList = new ArrayList<>();

        //存放取代欄位的變數
        List<String> setDbFieldList = new ArrayList<>();

        Arrays.stream(objClass.getDeclaredFields())
            .map(Field::getName)
            .forEach(item ->{
                if("id".equals(item)){
                    return;
                }
                dbFieldList.add(convertCamelToSnake(item));
                setDbFieldList.add(":" + item);
            });

        /**
         * 範例
         * INSERT INTO detail (tax_juris_cd, c11, org_cd, inv_cnt, ban, vat_losn, ban_nm, ban_addr)
         * VALUES (:taxJurisCd, :c11, :orgCd, :invCnt, :ban, :vatLosn, :banNm, :banAddr)
         */
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(convertCamelToSnake(objClass.getSimpleName()));
        insertSql.append(" ( ");
        insertSql.append(String.join(", ", dbFieldList));
        insertSql.append(" ) ");

        insertSql.append(" VALUE ");
        insertSql.append(" ( ");
        insertSql.append(String.join(", ", setDbFieldList));
        insertSql.append(" ) ");
//        log.info("sql: {}", insertSql);

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            Object item = dataList.get(i);
            try {
                batchParams[i] = new MapSqlParameterSource();
                for (Field field : objClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(item);
//                    log.info("Field:{}, Value:{}", field.getName(), value);
                    // 在這裡，您可以將值插入資料庫或進行其他操作
                    batchParams[i].addValue(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        jdbcTemplate.batchUpdate(String.valueOf(insertSql), batchParams);
    }

    //隨機選出某個元素的值
    private String getRandomValue(List<String> rangeParam){
        // 檢查是否為空
        if (rangeParam == null || rangeParam.isEmpty()) {
            throw new IllegalArgumentException("RangeParam cannot be null or empty");
        }
        // 產生隨機索引
        Random random = new Random();
        int randomIndex = random.nextInt(rangeParam.size());

        // 返回隨機值
        return rangeParam.get(randomIndex);
    }

    //依照位數 自動生成隨機碼
    private String generateRandomNumber(int numberOfDigits) {
        // 檢查位數是否合法
        if (numberOfDigits <= 0) {
            throw new IllegalArgumentException("Number of digits must be greater than zero");
        }
        // 生成隨機數字字串
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numberOfDigits; i++) {
            int digit = ThreadLocalRandom.current().nextInt(10);  // 生成 0 到 9 之間的隨機數字
            result.append(digit);
        }

        return result.toString();
    }

    //轉成 _命名規則
    public static String convertCamelToSnake(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                // 將大寫字母轉換為底線 + 小寫字母
                if (result.length() > 0) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }
}
