package com.chirag.service;

import com.chirag.exception.WalletException;
import com.chirag.modal.Order;
import com.chirag.modal.User;
import com.chirag.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet (User user) throws WalletException;

    Wallet addBalance (Wallet wallet, Long money) throws WalletException;

    Wallet findWalletById(Long id) throws WalletException;

    Wallet walletToWalletTransfer (User sender, Wallet receiverWallet, Long amount) throws WalletException;

    Wallet payOrderPayment(Order order, User user) throws WalletException;
}
