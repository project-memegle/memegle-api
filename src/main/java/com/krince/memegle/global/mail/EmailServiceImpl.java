package com.krince.memegle.global.mail;

import com.krince.memegle.util.RandomCodeUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final String senderEmail;

    public EmailServiceImpl(
            JavaMailSender javaMailSender,
            @Value("${spring.mail.username}") String senderEmail
    ) {
        this.javaMailSender = javaMailSender;
        this.senderEmail = senderEmail;
    }

    public String sendUserAuthenticationEmail(String email) throws MessagingException {
        String authenticationCode = RandomCodeUtil.generateRandomCode();
        MimeMessage emailForm = createEmailForm(email, authenticationCode);

        javaMailSender.send(emailForm);

        return authenticationCode;
    }

    private MimeMessage createEmailForm(String email, String authenticationCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, email);
        mimeMessage.setSubject("memegle 이메일 인증코드");
        mimeMessage.setFrom(senderEmail);
        mimeMessage.setText(authenticationCode, "utf-8", "html");

        return mimeMessage;
    }
}
