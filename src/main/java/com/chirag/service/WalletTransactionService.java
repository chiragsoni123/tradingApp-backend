package com.chirag.service;

import com.chirag.domain.WalletTransactionType;
import com.chirag.modal.Wallet;
import com.chirag.modal.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Wallet wallet,
                                        WalletTransactionType type,
                                        String transferId,
                                        String purpose,
                                        Long amount );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}
