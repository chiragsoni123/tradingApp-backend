package com.chirag.service;

import com.chirag.modal.PaymentDetails;
import com.chirag.modal.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
