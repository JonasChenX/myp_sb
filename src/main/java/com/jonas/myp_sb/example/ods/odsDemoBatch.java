package com.jonas.myp_sb.example.ods;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import com.jonas.myp_sb.example.ods.detail.DetailRepository;
import com.jonas.myp_sb.executionUtil.insertTestData.Detail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class odsDemoBatch {

    private final Logger log = LoggerFactory.getLogger(odsDemoBatch.class);

    @Autowired
    DetailRepository detailRepository;

    private static final int BATCH_SIZE = 10000;    //批次寫入資料量
    String DIRECTORY_PATH = "C:\\Users\\Jonas\\Desktop\\新增資料夾";

    @GetMapping("/odsDemoBatch")
    public void odsDemoBatch() throws IOException {

        SpreadSheet spread = new SpreadSheet();
        //title
        List<String> keyList = List.of("國稅局代號", "稽徵所/分局", "代號", "接收張數", "統一編號", "稅籍編號", "營業人名稱", "地址");
        String[] orgs = new String[]{"A05", "B01", "D01", "E01", "H01"};

        for (int i = 0; i < orgs.length; i++) {
            int startRow = 0;   //該區局第幾筆資料
            Long orgAllDataSize = detailRepository.getCountByOrgCd(orgs[i]);   //null改為全部明細資料的數量(sql count(0))
            log.info("查詢數量:{}", orgAllDataSize);
            // 設定檔名與輸出目錄
            String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String fileName = "測試清單X_" + strDate + ".ods";
            //標頭資料
            String sheetName = orgs[i];                       //sheet name
            Sheet sheet = new Sheet(sheetName, orgAllDataSize.intValue() + 2, keyList.size());
            spread.appendSheet(sheet);
            //統計區間(加在第一個row)
            sheet.insertRowBefore(0);
            ArrayList<String> headerArr = new ArrayList<>();
            String statisticalInterval = "統計區間:20230101 ~ 20231231";
            headerArr.add(statisticalInterval);
            sheet.getRange(0, 0).setValues(headerArr.toArray());

            //表頭資料(第二個row)
            sheet.getRange(1, 0).setValue(keyList.get(0));       //國稅局代號
            sheet.getRange(1, 1).setValue(keyList.get(1));       //稽徵所/分局
            sheet.getRange(1, 2).setValue(keyList.get(2));       //代號
            sheet.getRange(1, 3).setValue(keyList.get(3));       //接收張數
            sheet.getRange(1, 4).setValue(keyList.get(4));       //統一編號
            sheet.getRange(1, 5).setValue(keyList.get(5));       //稅籍編號
            sheet.getRange(1, 6).setValue(keyList.get(6));       //營業人名稱
            sheet.getRange(1, 7).setValue(keyList.get(7));       //地址

            for (int count = 0; orgAllDataSize - startRow > 0; count++) {
                //detail 明細資料
                List<Detail> details = detailRepository.queryDetail(startRow, BATCH_SIZE, orgs[i]);
                //detail 明細資料(Map)
                List<Map<String, Object>> dataList = new ArrayList<>();
                for (Detail company : details) {
                    Map<String, Object> map = new HashMap<>();         //該分局一筆資料
                    map.put(keyList.get(0), company.getTaxJurisCd());    //國稅局代號
                    map.put(keyList.get(1), company.getC11());           //稽徵所/分局
                    map.put(keyList.get(2), company.getOrgCd());         //代號
                    map.put(keyList.get(3), company.getInvCnt());        //接收張數
                    map.put(keyList.get(4), company.getBan());           //統一編號
                    map.put(keyList.get(5), company.getVatLosn());       //稅籍編號
                    map.put(keyList.get(6), company.getBanNm());         //營業人名稱
                    map.put(keyList.get(7), company.getBanAddr());       //地址
                    dataList.add(map);
                    // 更新起始行數
                    startRow++;
                }
                //塞sheet
                setSheet(dataList,keyList,sheet,spread,fileName,count,DIRECTORY_PATH);
            }
        }
    }

    private static void setSheet(List<Map<String, Object>> dataList, List<String> keyList, Sheet sheet, SpreadSheet spread, String fileName, int count, String directoryPath) {
        ArrayList<Object> bodyArr = new ArrayList<>();
        //新增sheet
        int startRow = BATCH_SIZE * count + 2;
        int endRow = dataList.size();
        int startCol = 0;
        int endCol = keyList.size() - 1;
        dataList.forEach(map1 -> keyList.forEach(column -> bodyArr.add(map1.get(column))));
        sheet.getRange(startRow, startCol, endRow, endCol - startCol + 1).setValues(bodyArr.toArray());

        //每次都獲取新的檔案往下寫，避免覆蓋舊的資料
        String filePath = Paths.get(directoryPath, fileName + ".ods").toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(filePath))) {
            spread.save(os);            //資料輸出到ods
        } catch (IOException e) {
            //exception
        }
    }

}
