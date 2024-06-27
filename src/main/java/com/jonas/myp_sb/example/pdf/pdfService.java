package com.jonas.myp_sb.example.pdf;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class pdfService {
    //設定table
    public PdfPTable setTable(List<Map<String, Object>> dataList, List<String> keyList, BaseFont bfChinese){
        //初始化
        PdfPTable table = new PdfPTable(keyList.size());
        table.setWidthPercentage(100); //寬度100%
        table.setSpacingBefore(10f); //上間距
        table.setSpacingAfter(10f); //下間距

        Font font = new Font(bfChinese, 8, Font.NORMAL);  //正常字體
        Font fontBold = new Font(bfChinese, 9, Font.BOLD); // 正常加粗字體

        PdfPRow pdfPRow = setCol(keyList, fontBold);
        table.getRows().add(pdfPRow);

        addTableRows(table, font, dataList, keyList);

        return table;
    }

    //設定table欄位
    private PdfPRow setCol(List<String> keyList, Font font){
        PdfPCell cells[] = new PdfPCell[keyList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            cells[i] = new PdfPCell(new Paragraph(keyList.get(i), font));
            cells[i].setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            cells[i].setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            cells[i].setFixedHeight(20f); //固定高度
        }
        PdfPRow cols = new PdfPRow(cells);
        return cols;
    }

    //設定table資料
    private void addTableRows(PdfPTable table, Font font, List<Map<String, Object>> dataList, List<String> keyList){
        //若沒有資料時 顯示查無資料
        if(dataList.isEmpty()){
            PdfPCell cell = new PdfPCell(new Paragraph("查無資料", font));
            cell.setColspan(table.getNumberOfColumns()); //合併表格單元格
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(15f); //固定高度
            table.addCell(cell);
        }

        for (Map row : dataList){
            for (String key : keyList){
                Object dataObj = !Objects.isNull(row.get(key)) ? row.get(key) : "";
                PdfPCell cell;
                //字串型態靠左 數字型態靠右並千分位格式化
                if(dataObj instanceof Number){
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
                    cell = new PdfPCell(new Paragraph(decimalFormat.format(dataObj), font));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }else {
                    Paragraph dataParagraph = new Paragraph(String.valueOf(dataObj), font);
                    cell = new PdfPCell(dataParagraph);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                }
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setNoWrap(false); //充許自動換行
                cell.setLeading(0, 1.5f); //調整行間距
                cell.setPadding(3f); //調整邊距
                table.addCell(cell);
            }
        }
    }
}
