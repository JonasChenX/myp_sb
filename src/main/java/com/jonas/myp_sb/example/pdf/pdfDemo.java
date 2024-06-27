package com.jonas.myp_sb.example.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class pdfDemo {

    public static void main(String[] args) {

        final String DIRECTORY_PATH = "C:\\Users\\Jonas\\Desktop\\PDF資料夾";
        final String FILE_NAME = "勞保資料(XXX)";

        String filePath = DIRECTORY_PATH + "\\" + FILE_NAME + ".pdf";

        exportPdfToFile(filePath);
    }

    public static class HeaderFooterPageEvent extends PdfPageEventHelper {
        private Font headerFont;
        private Font footerFont;
        private Image img;

        public HeaderFooterPageEvent(Font headerFont, Font footerFont, String imagePath) throws IOException {
            this.headerFont = headerFont;
            this.footerFont = footerFont;
            this.img = Image.getInstance(imagePath);
        }

        //處理每一頁的方法
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Rectangle pageSize = document.getPageSize();

            //顯示時間
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String formattedDate = now.format(formatter);

            // Header
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    new Phrase(formattedDate, headerFont),
                    pageSize.getRight() - 70,
                    pageSize.getTop() - 30, 0);


            // Footer
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    new Phrase("第 " + document.getPageNumber() + " 頁", footerFont),
                    (pageSize.getLeft() + pageSize.getRight()) / 2,
                    pageSize.getBottom() + 30, 0);

            //浮水印 垂直置中
            PdfContentByte canvas = writer.getDirectContentUnder();
            img.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            float imgX = (document.getPageSize().getWidth() - img.getScaledWidth()) / 2;
            float imgY = (document.getPageSize().getHeight() - img.getScaledHeight()) / 2;
            img.setAbsolutePosition(imgX, imgY);

            //設定圖片透明度
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.2f); //設置透明度為20%
            canvas.setGState(gState);

            // 設置圖片位置和大小，使其覆蓋整個頁面
            canvas.addImage(img);
        }
    }

    private static void exportPdfToFile(String filePath) {

        //參考 https://github.com/qyaaaa/open-pdf/tree/main

        final String FONT_PATH = "C:\\Users\\Jonas\\Desktop\\自家專案\\setting\\font\\SimSun.ttf";

        final String IMAGE_PATH = "C:\\Users\\Jonas\\Desktop\\自家專案\\setting\\googleLogo.png";

        //設定尺寸
        Document document = new Document(PageSize.A4);

        try {
            //檢查創建目錄
            Path path = Paths.get(filePath).getParent();
            if (path != null && !Files.exists(path)) {
                Files.createDirectories(path);
            }

            BaseFont bfChinese = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese, 8, Font.NORMAL);  //正常字體
            Font fontBigBold = new Font(bfChinese, 16, Font.BOLD); // 加粗大字體

            //將PDF寫入指定的路徑
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

            writer.setPageEvent(new HeaderFooterPageEvent(new Font(bfChinese, 8, Font.NORMAL), new Font(bfChinese, 8, Font.NORMAL),IMAGE_PATH));
            document.open();

            //創建一個段落
            Paragraph title = new Paragraph("XXX勞保報告書", fontBigBold);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph indexInfo = new Paragraph("勞保記錄內容:", font);
            indexInfo.setAlignment(Element.ALIGN_LEFT);
            document.add(indexInfo);

            //資料
            List<Map<String, Object>> dataList = initDataList();
            List<String> keyList = List.of("公司名稱", "起始日期", "終止日期", "勞保金額", "平均", "備註");

            //建table
            pdfService pdfService = new pdfService();
            PdfPTable table = pdfService.setTable(dataList, keyList, bfChinese);
            document.add(table);

            Paragraph remark = new Paragraph("註:機密資料請勿外流", font);
            remark.setAlignment(Element.ALIGN_LEFT);
            remark.setSpacingAfter(10f);
            document.add(remark);

            Paragraph jgdw = new Paragraph("勞保局", font);
            jgdw.setAlignment(Element.ALIGN_RIGHT);
            jgdw.setSpacingAfter(2f);
            document.add(jgdw);

            Calendar now = Calendar.getInstance();
            Paragraph rq = new Paragraph(now.get(Calendar.YEAR) + "年 " + (now.get(Calendar.MONTH) + 1) + "月 " + now.get(Calendar.DAY_OF_MONTH) + "日", font);
            rq.setAlignment(Element.ALIGN_RIGHT);
            document.add(rq);

        } catch (DocumentException | IOException e) {
            System.err.println(e.getMessage());
        }

        document.close();
    }

    private static List<Map<String, Object>> initDataList(){
        List<Map<String, Object>> fakeDataList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            fakeDataList.add(
                Map.of(
                    "起始日期", generateRandomDate(2000, 2020),
                    "終止日期", generateRandomDate(2021, 2024),
                    "公司名稱", "公司" + i,
                    "勞保金額", 25000 + (i * 500),
                    "平均", 21.02 + (i * 500),
                    "備註",  i % 2 == 0 ? "測試長度,測試長度,測試長度,測試長度,測試長度,測試長度,測試長度,測試長度,測試長度,測試長度" : ""
                )
            );
        }

        return fakeDataList;
    }

    private static String generateRandomDate(int startYear, int endYear) {
        int year = (int) (Math.random() * (endYear - startYear + 1)) + startYear;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;
        return year + "/" + month + "/" + day;
    }
}
