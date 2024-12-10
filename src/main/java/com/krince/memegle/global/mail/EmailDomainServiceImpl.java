package com.krince.memegle.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailDomainServiceImpl implements EmailDomainService {

    private final JavaMailSender javaMailSender;
    private final String senderEmail;

    public EmailDomainServiceImpl(
            JavaMailSender javaMailSender,
            @Value("${spring.mail.username}") String senderEmail
    ) {
        this.javaMailSender = javaMailSender;
        this.senderEmail = senderEmail;
    }

    @Async
    public void sendUserAuthenticationEmail(String email, String authenticationCode) throws MessagingException {
        MimeMessage emailForm = createEmailForm(email, authenticationCode);

        javaMailSender.send(emailForm);
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
