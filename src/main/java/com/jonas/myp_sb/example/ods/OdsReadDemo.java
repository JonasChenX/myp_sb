package com.jonas.myp_sb.example.ods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class OdsReadDemo {

    private final Logger log = LoggerFactory.getLogger(OdsReadDemo.class);

    private final String SEPARATOR = "@V@";


    @PostMapping("/readOdsFileContent")
    public List<Map<String, String>> readOdsOrExcelFileContent(@RequestPart(name = "uploadFileData", required = true) MultipartFile uploadFile){
        log.info("readOdsFileContent");
        List<Map<String, String>> resultMapList = new ArrayList<>();
        try (InputStream inputStream = uploadFile.getInputStream()) {
            //取得檔案內容的字串
            String resultContent = readODSOrExcelContent(inputStream);
            log.info("result:\n{}", resultContent);
            //格式化內容
            resultMapList = parseData(resultContent, 2, 31);
            log.info("resultMapList:\n{}",resultMapList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMapList;
    }

    private String readODSOrExcelContent(InputStream inputStream) throws IOException {
        Map<Integer, String> sharedStrings = new HashMap<>();
        String sheetXml = null;
        String contentXml = null;
        StringBuilder result = new StringBuilder();

        Map<String, byte[]> zipEntries = new HashMap<>();

        // 先將所有ZipEntry讀取到Map中，避免Stream Closed問題
        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                zipEntries.put(zipEntry.getName(), readZipEntryToBytes(zis));
            }
        }

        // 處理 XLSX（Excel）
        if (zipEntries.containsKey("xl/worksheets/sheet1.xml")) {
            sheetXml = new String(zipEntries.get("xl/worksheets/sheet1.xml"), StandardCharsets.UTF_8);
            if (zipEntries.containsKey("xl/sharedStrings.xml")) {
                try {
                    sharedStrings = parseSharedStrings(new ByteArrayInputStream(zipEntries.get("xl/sharedStrings.xml")));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing sharedStrings.xml", e);
                }
            }
            try {
                result.append(parseSheet(sheetXml, sharedStrings));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 處理 ODS
        if (zipEntries.containsKey("content.xml")) {
            contentXml = new String(zipEntries.get("content.xml"), StandardCharsets.UTF_8);
            result.append(parseContentXMLByOds(contentXml.getBytes(StandardCharsets.UTF_8)));
        }
        return result.toString();
    }

    private byte[] readZipEntryToBytes(ZipInputStream zis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = zis.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }

    private Map<Integer, String> parseSharedStrings(InputStream inputStream) throws Exception {
        Map<Integer, String> sharedStrings = new HashMap<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        NodeList stringItems = doc.getElementsByTagName("t");
        for (int i = 0; i < stringItems.getLength(); i++) {
            sharedStrings.put(i, stringItems.item(i).getTextContent());
        }
        return sharedStrings;
    }

    private String parseSheet(String sheetXml, Map<Integer, String> sharedStrings) throws Exception {
        StringBuilder content = new StringBuilder();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new ByteArrayInputStream(sheetXml.getBytes(StandardCharsets.UTF_8)));
        NodeList rows = doc.getElementsByTagName("row");

        for (int i = 0; i < rows.getLength(); i++) {
            NodeList cells = rows.item(i).getChildNodes();
            for (int j = 0; j < cells.getLength(); j++) {
                Node cell = cells.item(j);
                if (cell.getNodeName().equals("c")) {
                    String type = cell.getAttributes().getNamedItem("t") != null
                            ? cell.getAttributes().getNamedItem("t").getNodeValue()
                            : "";
                    String value = cell.getTextContent().trim();
                    if ("s".equals(type)) {
                        int index = Integer.parseInt(value);
                        value = sharedStrings.getOrDefault(index, value).replace("\n"," ");
                    }
                    content.append(value);
                    if (j < cells.getLength() - 1) {
                        content.append(SEPARATOR);
                    }
                }
            }
            content.append("\n");
        }
        return content.toString();
    }

    private String parseContentXMLByOds(byte[] xmlData) {
        StringBuilder content = new StringBuilder();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xmlData));
            doc.getDocumentElement().normalize();
            NodeList rowList = doc.getElementsByTagName("table:table-row");
            for (int i = 0; i < rowList.getLength(); i++) {
                Element rowElement = (Element) rowList.item(i);
                NodeList cellList = rowElement.getElementsByTagName("table:table-cell");
                for (int j = 0; j < cellList.getLength(); j++) {
                    Element cellElement = (Element) cellList.item(j);
                    String cellValue = cellElement.getTextContent().trim().replace("\n", "").replace("\r", "");
                    content.append(cellValue);
                    if (j < cellList.getLength() - 1) {
                        content.append(SEPARATOR);
                    }
                }
                content.append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing content.xml", e);
        }
        return content.toString();
    }

    /**
     *
     * @param data 字串資料
     * @param headerRowIndex 從第幾行取標頭(起數從0開始)
     * @param rowCount 取幾筆資料
     * @return
     */
    public List<Map<String, String>> parseData(String data, int headerRowIndex, int rowCount) {
        List<Map<String, String>> result = new ArrayList<>();
        String[] rows = data.split("\n"); // 分割行

        if (headerRowIndex >= rows.length) {
            throw new IllegalArgumentException("Header row index is out of bounds.");
        }

        String[] headers = rows[headerRowIndex].split(SEPARATOR); // 取得標頭

        for (int i = headerRowIndex + 1; i < rows.length && (i - headerRowIndex) <= rowCount; i++) {
            String[] values = rows[i].split(SEPARATOR);
            Map<String, String> rowMap = new LinkedHashMap<>();

            for (int j = 0; j < headers.length; j++) {
                String key = headers[j].trim();
                String value = (j < values.length) ? values[j].trim() : "";
//                if(j == 0){
//                    value = formatDate(values[j].trim());
//                }
                rowMap.put(key, value);
            }
            result.add(rowMap);
        }

        return result;
    }

    private String formatDate(String dateString) {
        // 正則表達式匹配 "1月01日(三)" 這種格式
        String regex = "(\\d{1,2}月\\d{2}日\\([一二三四五六日]\\))";

        // 匹配字串
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(dateString);

        // 查找並輸出匹配的結果
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "未找到符合的日期格式";
    }

}
