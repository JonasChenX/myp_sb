package com.jonas.myp_sb.example.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Component
public class MailService {

    /**
     * gmail設定參考 https://www.youtube.com/watch?v=IWxwWFTlTUQ
     * */

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 寄出郵件
     * @param receiver 寄出mail
     * @param subject 標頭
     * @param context 內容
     */
    public void sendSimpleMail(String receiver, String subject, String context){
        logContext(receiver,subject,context);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setText(context);
        message.setSubject(subject);
        try{
            mailSender.send(message);
            log.info("郵件已發送");
        }catch (Exception e){
            log.info("失敗:"+e);
        }
    }

    /**
     * 寄出html郵件
     * @param receiver 寄出mail
     * @param subject 標頭
     * @param context 內容
     */
    public void sendHtmlMail(String receiver, String subject, String context){
        logContext(receiver,subject,context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(sender);
            messageHelper.setTo(receiver);
            messageHelper.setSubject(subject);
            messageHelper.setText(context, true);
            mailSender.send(mimeMessage);
            log.info("郵件已發送");
        }catch (Exception e){
            log.info("失敗:"+e);
        }
    }

    public void sendInlineResourceMail(String receiver, String subject, String context,String recPath, String rscId){
        logContext(receiver,subject,context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(sender);
            messageHelper.setTo(receiver);
            messageHelper.setSubject(subject);
            messageHelper.setText(context, true);

            FileSystemResource resource = new FileSystemResource(new File(recPath));
            messageHelper.addInline(rscId, resource);

            mailSender.send(mimeMessage);
            log.info("郵件已發送");
        }catch (Exception e){
            log.info("失敗:"+e);
        }
    }

    /**
     * show出Log出來
     * @param receiver 寄出mail
     * @param subject 標頭
     * @param context 內容
     */
    private void logContext(String receiver, String subject, String context){
        log.info("=====================");
        log.info("寄出者:"+sender);
        log.info("收件者:"+receiver);
        log.info("標頭:"+subject);
        log.info("內容:"+context);
        log.info("=====================");
    }
}
