package com.jonas.myp_sb.example.ods;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import com.jonas.myp_sb.annotation.LogAnnotation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;
@RestController
public class OdsDemo {

    @GetMapping("/downloadOds")
    @LogAnnotation(methodName = "下載ODS檔")
    public DownloadableResource downloadFile() throws IOException {
        SpreadSheet spread = new SpreadSheet();

        List<Map<String, Object>> dataList = List.of(
                Map.of("統一編號", "2333344", "營業人名稱", "aaaaa", "機關代號", "a"),
                Map.of("統一編號", "999999", "營業人名稱", "888888", "機關代號", "c")
        );

        List<String> keyList = List.of("機關代號", "營業人名稱", "統一編號");

        Sheet sheet = setSheet(dataList,keyList, "選案模型選案列選清單_0");
        spread.appendSheet(sheet);

        // Save the spreadsheet to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        spread.save(outputStream);

        //檔案名稱
        String strDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileName = "選案模型選案列選清單_" + strDate + ".ods";

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        DownloadableResource downloadableResource = new DownloadableResource(resource, fileName);

        return downloadableResource;
    }

    /**
     *
     * @param dataList  倒出資料
     * @param keyList   欄位
     * @param sheetName 標籤名稱
     * @return Sheet物件
     */
    private Sheet setSheet(List<Map<String, Object>> dataList, List<String> keyList, String sheetName) {
        ArrayList<Object> bodyArr = new ArrayList<>(keyList);
        //新增sheet
        dataList.forEach(map1 -> {
            keyList.forEach(column -> {
                bodyArr.add(map1.get(column));
            });
        });

        int rows = dataList.size() + 1;
        int columns = keyList.size();
        Sheet sheet = new Sheet(sheetName, rows, columns);

        sheet.getDataRange().setValues(bodyArr.toArray());
        sheet.getRange(0, 0, 0, 1).setFontBold(true);

        /*
        加在第一欄位
         */
        sheet.insertRowBefore(0);
        ArrayList<String> headerArr = new ArrayList<>();
        headerArr.add("機密等級: 密");
        sheet.getRange(0, columns - 1).setValues(headerArr.toArray());
        sheet.getRange(0, 0, 0, 1).setFontBold(true);
        sheet.setRowHeight(sheet.getLastRow() - 2, 4.0D);

        return sheet;
    }

}
