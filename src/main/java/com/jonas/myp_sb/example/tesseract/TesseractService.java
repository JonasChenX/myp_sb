package com.jonas.myp_sb.example.tesseract;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class TesseractService {

    public String readImageStr(String tessdataPath, String imagePath){
        String result = "";
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            ITesseract tesseract = new Tesseract();

            //https://github.com/tesseract-ocr
            tesseract.setDatapath(tessdataPath); // 設定 Tesseract 的訓練數據目錄
            tesseract.setLanguage("eng");
            result = tesseract.doOCR(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
