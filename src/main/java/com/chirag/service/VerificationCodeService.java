package com.chirag.service;

import com.chirag.domain.VerificationType;
import com.chirag.modal.User;
import com.chirag.modal.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode findVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(User userId);

    Boolean verifyOtp(String otp, VerificationCode verificationCode);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
