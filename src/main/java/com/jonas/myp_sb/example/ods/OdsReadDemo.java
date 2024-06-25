package com.jonas.myp_sb.example.ods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class OdsReadDemo {

    private final Logger log = LoggerFactory.getLogger(OdsReadDemo.class);


    @PostMapping("/readOdsFileContent")
    public void readOdsFileContent(@RequestPart(name = "uploadFileData", required = true) MultipartFile uploadFile){
        try (InputStream inputStream = uploadFile.getInputStream()) {
            readODSContent(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readODSContent(InputStream inputStream) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.getName().equals("content.xml")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        baos.write(buffer, 0, len);
                    }
                    String csvContent = parseContentXMLToCSV(baos.toByteArray());
                    log.info("csvContent:{}", csvContent);
                    break;
                }
                zis.closeEntry();
            }
        }
    }

    private String parseContentXMLToCSV(byte[] xmlData) {
        StringBuilder csvContent = new StringBuilder();

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new ByteArrayInputStream(xmlData));
            xmlDocument.getDocumentElement().normalize();

            NodeList rowList = xmlDocument.getElementsByTagName("table:table-row");
            for (int i = 0; i < rowList.getLength(); i++) {
                Element rowElement = (Element) rowList.item(i);
                NodeList cellList = rowElement.getElementsByTagName("table:table-cell");
                for (int j = 0; j < cellList.getLength(); j++) {
                    Element cellElement = (Element) cellList.item(j);
                    String cellValue = cellElement.getTextContent();
                    csvContent.append(cellValue.replace("\n", "").replace("\r", ""));
                    if (j < cellList.getLength() - 1) {
                        csvContent.append(",");
                    }
                }
                csvContent.append("\n");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return csvContent.toString();
    }
}
