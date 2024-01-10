package com.jonas.myp_sb.example.tesseract;
public class TesseractDemo {

    public static void main(String[] args) {

        //tesseract-ocr
        String TESSDATA_PATH = "C:\\Users\\Jonas\\Desktop\\tessdata";
        //圖片檔案
        String IMAGE_PATH = "src/main/resources/tesseractFile/readTestImage.png";

        TesseractService tesseractService = new TesseractService();
        String imageStr = tesseractService.readImageStr(TESSDATA_PATH, IMAGE_PATH);
        System.out.println(imageStr);
    }
}
