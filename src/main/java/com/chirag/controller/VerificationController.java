package com.chirag.controller;

import com.chirag.service.EmailService;
import com.chirag.service.UserService;
import com.chirag.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {
    private final VerificationCodeService verificationCodeService;
    private final UserService userService;

    @Autowired
    private EmailService emailService;

    public VerificationController(VerificationCodeService verificationCodeService, UserService userService) {
        this.verificationCodeService = verificationCodeService;
        this.userService = userService;
    }
}
