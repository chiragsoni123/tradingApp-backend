package com.chirag.service;

import com.chirag.domain.VerificationType;
import com.chirag.modal.User;

public interface UserService {

    public User findUserByJwt(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    public User updatePassword(User user, String newPassword);
}
