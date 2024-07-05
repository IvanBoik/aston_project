package com.aston.aston_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;

    public void sendUpdatesInWishList(String to, String product) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("%s снова в продаже!".formatted(product));
        message.setText("""
        %s теперь в наличии.
        Зайдите в свой список ожидания, чтобы оформить заказ.
        """.formatted(product));

        mailSender.send(message);
    }
}
