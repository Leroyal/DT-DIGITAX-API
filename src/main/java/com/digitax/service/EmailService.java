package com.digitax.service;

public interface EmailService {
    String changeEmailSupport(String to, String subject,String UserName, String string);

	String changePasswordSupport(String email, String UserName);

	String forgotPasswordSupport(String email, String token, String username);
}