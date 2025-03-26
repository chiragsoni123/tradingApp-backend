package com.chirag.service;

import com.chirag.domain.OrderType;
import com.chirag.exception.WalletException;
import com.chirag.modal.Order;
import com.chirag.modal.User;
import com.chirag.modal.Wallet;
import com.chirag.modal.WalletTransaction;
import com.chirag.repository.WalletRepository;
import com.chirag.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public Wallet generateWallet(User user){
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }


    @Override
    public Wallet getUserWallet(User user) throws WalletException {
        Wallet wallet = walletRepository.findByUserId(user.getId());

        if(wallet == null){
            wallet = new Wallet();
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) throws WalletException{
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
        System.out.println("updated wallet - "+wallet);
        return wallet;
    }

    @Override
    public Wallet findWalletById(Long id) throws WalletException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new WalletException("Wallet Not Found with id"+ id);
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws WalletException {
        Wallet senderWallet = getUserWallet(sender);

        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new WalletException("Insufficient Balance...");
        }

        BigDecimal senderBalance = senderWallet.getBalance()
                .subtract(BigDecimal.valueOf(amount));

        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);

        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepository.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws WalletException {
        Wallet wallet = getUserWallet(user);

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(wallet);
        walletTransaction.setPurpose(order.getOrderType() + " " + order.getOrderItem().getCoin().getId());

        walletTransaction.setDate(LocalDate.now());
        walletTransaction.setTransferId(order.getOrderItem().getCoin().getSymbol());


        if(order.getOrderType().equals(OrderType.BUY)){
            walletTransaction.setAmount(-order.getPrice().longValue());
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());

            if(newBalance.compareTo(order.getPrice())<0){
                System.out.println("Inside");
                throw new WalletException("Insufficient fund for this transaction");
            }
            System.out.println("Outside---------");
            wallet.setBalance(newBalance);
        }
        else if (order.getOrderType().equals(OrderType.SELL)){
            walletTransaction.setAmount(order.getPrice().longValue());
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletTransactionRepository.save(walletTransaction);
        walletRepository.save(wallet);
        return wallet;
    }
}
