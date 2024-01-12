package com.jonas.myp_sb.example.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class PlaywrightService {

    /**
     * 截圖
     * @param page Page物件
     * @param pathNm 截圖名稱
     */
    public void screenshot(Page page, String pathNm){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(pathNm + ".png")));
    }

    /**
     * 截圖
     * @param page Page物件
     * @param pathNm 截圖名稱
     * @param selector 截圖的節點範圍
     */
    public void screenshot(Page page, String pathNm, String selector){
        page.locator(selector).screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(pathNm + ".png")));
    }
}
