package com.chirag.service;

import com.chirag.modal.Order;
import com.chirag.modal.User;
import com.chirag.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet (User user);

    Wallet addBalance (Wallet wallet, Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer (User sender, Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;
}
