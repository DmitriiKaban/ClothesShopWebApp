package com.example.clothesshopwebapp.services;

public interface MailService {
    void send(String fromAddress, String toAddress, String content) throws Exception;
}
