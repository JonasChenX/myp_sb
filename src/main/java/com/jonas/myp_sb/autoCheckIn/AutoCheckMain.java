package com.jonas.myp_sb.autoCheckIn;

import com.jonas.myp_sb.example.mail.MailService;
import com.jonas.myp_sb.example.playwright.PlaywrightService;
import com.jonas.myp_sb.example.tesseract.TesseractService;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AutoCheckMain {

    private final Logger log = LoggerFactory.getLogger(AutoCheckMain.class);

    @Autowired
    PlaywrightService playwrightService;

    @Autowired
    TesseractService tesseractService;

    @Autowired
    AutoCheckService autoCheckService;

    @Autowired
    private MailService mailService;

    //tesseract-ocr
    String TESSDATA_PATH = "C:\\Users\\Jonas\\Desktop\\tessdata";
    //圖片檔案
    String IMAGE_PATH = "image";

    String USER_ID = "";
    String PWD = "";

//    @Scheduled(cron = "0 00 08 ? * *") // 每天觸發一次
    public void autoCheckIn() throws InterruptedException {
        log.info("autoCheckIn執行");
        try (Playwright playwright = Playwright.create()) {
            //設定無痕模式
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("http://cwams.ecloudsrv.com/OnlineSign/OnlineSignInOut.aspx");

            page.onResponse(response -> {
                log.info("------------ " + response.url() + " ------------");
                if ("http://cwams.ecloudsrv.com/OnlineSign/OnlineSignInOut.aspx".equals(response.url())) {
                    log.info(response.text());
                    boolean isNo = response.text().contains("驗證碼錯誤");
                    if(isNo){
                        log.error("自動系統 - 簽到失敗");
                        mailService.sendSimpleMail("@gmail.com",
                                "自動系統 - 簽到失敗",
                                "自動系統 - 簽到失敗");
                        try {
                            autoCheckInAct(page);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        log.error("自動系統 - 簽到成功");
                        mailService.sendSimpleMail("@gmail.com",
                                "自動系統 - 簽到成功",
                                "自動系統 - 簽到成功");
                        page.close();
                        browser.close();
                    }
                }
            });

            autoCheckInAct(page);

            //啟動調試工具
            page.pause();
        }
    }


//    @Scheduled(cron = "0 10 09 ? * *") // 每天觸發一次
    public void autoCheckOut() throws InterruptedException {
        log.info("autoCheckOut執行");
        try (Playwright playwright = Playwright.create()) {
            //設定無痕模式
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("http://cwams.ecloudsrv.com/OnlineSign/OnlineSignInOut.aspx");

            page.onResponse(response -> {
                log.info("------------ " + response.url() + " ------------");
                if ("http://cwams.ecloudsrv.com/OnlineSign/OnlineSignInOut.aspx".equals(response.url())) {
                    log.info(response.text());
                    boolean isNo = response.text().contains("驗證碼錯誤");
                    if(isNo){
                        log.error("自動系統 - 簽退失敗");
                        mailService.sendSimpleMail("@gmail.com",
                                "自動系統 - 簽退失敗",
                                "自動系統 - 簽退失敗");
                        try {
                            autoCheckOutAct(page);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        log.info("自動系統 - 簽退成功");
                        mailService.sendSimpleMail("@gmail.com",
                                "自動系統 - 簽退成功",
                                "自動系統 - 簽退成功");
                        page.close();
                        browser.close();
                    }
                }
            });

            autoCheckOutAct(page);

            //啟動調試工具
            page.pause();
        }
    }

    private void autoCheckOutAct(Page page) throws InterruptedException {
        playwrightService.screenshot(page, IMAGE_PATH, "#captchasign");
        String imageStr = tesseractService.readImageStr(TESSDATA_PATH, IMAGE_PATH + ".png");
        System.out.println(imageStr);
        autoCheckService.checkOut(page,USER_ID,PWD,imageStr);
    }

    private void autoCheckInAct(Page page) throws InterruptedException {
        playwrightService.screenshot(page, IMAGE_PATH, "#captchasign");
        String imageStr = tesseractService.readImageStr(TESSDATA_PATH, IMAGE_PATH + ".png");
        System.out.println(imageStr);
        autoCheckService.checkIn(page,USER_ID,PWD,imageStr);
    }
}
