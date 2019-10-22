import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class SimpleMail implements Job {

    private static String user = "@qq.com";
    private static String passwd = "";//授权码
    private static String to = "@qq.com";
    private static String host = "smtp.qq.com";

    public static void sendMailForSmtp(String title, String content, String[] tos, String[] ccs) throws EmailException {
        SimpleEmail mail = new SimpleEmail();
        // 设置邮箱服务器信息
        mail.setHostName(host);
        mail.setSmtpPort(25);
        // 设置密码验证器passwd为授权码
        mail.setAuthentication(user, passwd);
        // 设置邮件发送者
        mail.setFrom(user);
        // 设置邮件编码
        mail.setCharset("UTF-8");
        // 设置邮件主题
        mail.setSubject(title);
        //SSL方式
        mail.setSSLOnConnect(true);
        // 设置邮件内容
//		mail.setMsg(content);
        // 设置邮件接收者
//		mail.addTo(to);
        mail.addTo(tos);
        mail.addCc(ccs);
        // 发送邮件
        MimeMultipart multipart = new MimeMultipart();
        //邮件正文
        BodyPart contentPart = new MimeBodyPart();
        try {
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            //邮件附件
            BodyPart attachmentPart = new MimeBodyPart();
            File file = new File("E:\\timfile\\报错.txt");
            FileDataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(MimeUtility.encodeWord(file.getName()));
            multipart.addBodyPart(attachmentPart);
            mail.setContent(multipart);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println(mail.toString());
        mail.send();
        System.out.println("mail send success!");
    }
    public void execute(JobExecutionContext var1) throws JobExecutionException {
        // TODO Auto-generated method stub
        //多个接收者
        String[] tos = {"@qq.com"};
        //多个抄送者
        String[] ccs = {"@qq.com"};
        try {
            SimpleMail.sendMailForSmtp("title", "hello <br> ccy", tos, ccs);
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}