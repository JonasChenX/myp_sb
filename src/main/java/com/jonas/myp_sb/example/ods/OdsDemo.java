package com.jonas.myp_sb.example.ods;

import com.github.miachm.sods.*;
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

        List<Map<String, String>> dataList = List.of(
                Map.of("col1", "2333344\n22222222", "col2", "先生公司", "col3", "a"),
                Map.of("col1", "111111", "col2", "先生公司2", "col3", "c"),
                Map.of("col1", "222222\n22222222\n22222222\n22222222\n22222222\n22222222", "col2", "先生公司3", "col3", "c"),
                Map.of("col1", "333333\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222\n22222222", "col2", "先生公司4", "col3", "c"),
                Map.of("col1", "555555\n22222222\n22222222", "col2", "先生公司5", "col3", "c")
        );

        List<String> keyList = List.of("col1", "col2", "col3");

        Sheet sheet = setSheet(dataList,keyList, "sheet_測試");


        spread.appendSheet(sheet);

        // Save the spreadsheet to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        spread.save(outputStream);

        //檔案名稱
        String strDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileName = "測試ODS表單_" + strDate + ".ods";

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
    private Sheet setSheet(List<Map<String, String>> dataList, List<String> keyList, String sheetName) {
        //添加column 和 data
        ArrayList<Object> bodyArr = new ArrayList<>(keyList);

        //紀錄每筆資料的行數
        int[][] dataLinesLog = new int[dataList.size()][keyList.size()];

        //新增sheet
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> data = dataList.get(i);
            for (int j = 0; j < keyList.size(); j++) {
                String column = keyList.get(j);
                String value = data.get(column);
                bodyArr.add(value);

                dataLinesLog[i][j] = data.get(column) != null ? value.split("\n").length : 1;
            }
        }

        //紀錄每一行 的最大行數
        ArrayList<Integer> rowLinesMax = new ArrayList<>();
        for (int[] row : dataLinesLog) {
            int max = Arrays.stream(row).max().orElseThrow();
            rowLinesMax.add(max);
        }

        int rows = dataList.size() + 1;
        int columns = keyList.size();
        Sheet sheet = new Sheet(sheetName, rows, columns);
        sheet.getDataRange().setValues(bodyArr.toArray());

        //加在第一欄位
        sheet.insertRowBefore(0);
        sheet.getRange(0, 0).setValues(new String[]{"標題"});

        //處理第一個row
        //getRange 的參數(起始row,起始column,數量row,數量colum)
        Range headerRange = sheet.getRange(0, 0, 1, columns);
        //合併儲存格
        headerRange.merge();
        //文字 左右上下置中
        Style styleByText = new Style();
        styleByText.setTextAligment(Style.TEXT_ALIGMENT.Center);
        styleByText.setVerticalTextAligment(Style.VERTICAL_TEXT_ALIGMENT.Middle);
        headerRange.setStyle(styleByText);

        //處理第二個row
        Range columnRange = sheet.getRange(1, 0, 1, columns);
        //變粗體 中文沒有支援
        columnRange.setStyle(styleByText);
        columnRange.setFontBold(true);

        //處理第三個row之後
        Range dataRange = sheet.getRange(2, 0, rows - 1, columns);
        //設定border
        Style styleByBorders = new Style();
        Borders borders = new Borders();
        borders.setBorder(true);
        borders.setBorderProperties("0.035cm solid #616f71 ");
        styleByBorders.setBorders(borders);
        dataRange.setStyle(styleByBorders);

        //設定高度(第一個row 標題)
        sheet.setRowHeights(0,1,12D);
        //設定高度(第二個row 欄位)
        sheet.setRowHeights(1,1,8D);
        //設定高度(第三個row 資料)
        for (int i = 0; i < rowLinesMax.size(); i++) {
            Double height = (rowLinesMax.get(i) * 4.1D) + 1D;
            sheet.setRowHeight(i + 2, height);
        }
        //設定寬度
        sheet.setColumnWidths(0, columns, 50D);


        // 針對 儲存格的設定文件參考
        // https://miachm.github.io/SODS/com/github/miachm/sods/Range.html

        return sheet;
    }

}
