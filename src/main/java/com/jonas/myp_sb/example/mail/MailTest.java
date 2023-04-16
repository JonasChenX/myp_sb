package com.jonas.myp_sb.example.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail(){
        mailService.sendSimpleMail("a12345@gmail.com",
                "text simple mail",
                "測試測試");
    }

    @Test
    public void testHtmlMail(){
        String html =
                "<html>\n" +
                "    <body>\n" +
                "        <h3>哈囉</h3>\n" +
                "    </body>\n" +
                "</html>";

        mailService.sendHtmlMail("a12345@gmail.com",
                "text simple mail",
                html);
    }

    @Test
    public void testInlineResourceMail(){
        String rscId = "img01";
        String html =
                "<html>\n" +
                "    <body>\n" +
                "        <h3>大家好</h3>\n" +
                "        <img src=\'cid:"+ rscId +"\'> "+
                "    </body>\n" +
                "</html>";

        mailService.sendInlineResourceMail("a12345@gmail.com",
                "text simple mail",
                html,"src/main/resources/ioDemo/test.png",rscId);
    }
}
