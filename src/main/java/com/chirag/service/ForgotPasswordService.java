package com.chirag.service;

import com.chirag.domain.VerificationType;
import com.chirag.modal.ForgotPasswordToken;
import com.chirag.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken (User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
