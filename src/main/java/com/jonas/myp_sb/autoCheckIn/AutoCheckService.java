package com.jonas.myp_sb.autoCheckIn;

import com.jonas.myp_sb.common.RandomFun;
import com.microsoft.playwright.Page;
import org.springframework.stereotype.Service;

@Service
public class AutoCheckService {

    public void checkIn(Page page, String userId, String pwd, String imageStr) throws InterruptedException {
        checkAct(page, userId, pwd, imageStr);
        page.locator("#btnSignIn").click();
    }

    public void checkOut(Page page, String userId, String pwd, String imageStr) throws InterruptedException {
        checkAct(page, userId, pwd, imageStr);
        page.locator("#btnSignOut").click();
    }

    private void checkAct(Page page, String userId, String pwd, String imageStr) throws InterruptedException {
        Thread.sleep(RandomFun.generateRandomNumber(1000,3000));

        //帳號
        page.locator("#txtAccount").click();
        page.locator("#txtAccount").fill(userId);

        Thread.sleep(RandomFun.generateRandomNumber(1000,3000));

        //密碼
        page.locator("#txtPassword").click();
        page.locator("#txtPassword").fill(pwd);

        Thread.sleep(RandomFun.generateRandomNumber(1000,3000));

        //驗證碼
        page.locator("#txtVerification").click();
        page.locator("#txtVerification").fill(imageStr);

        Thread.sleep(RandomFun.generateRandomNumber(1000,3000));
    }
}
