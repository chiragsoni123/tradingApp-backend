package com.chirag.controller;

import com.chirag.domain.WalletTransactionType;
import com.chirag.modal.*;
import com.chirag.response.PaymentResponse;
import com.chirag.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
//@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.OK);

    }

    @GetMapping("/api/wallet/transactions")
    public ResponseEntity<List<WalletTransaction>> getWalletTransaction(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user = userService.findUserByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        List<WalletTransaction> transactions = walletTransactionService.getTransactions(wallet, null);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping("/api/wallet/deposit/amount/{amount}")
    public ResponseEntity<PaymentResponse> depositMoney(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long amount) throws Exception{

        User user = userService.findUserByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposit success");
        walletService.addBalance(wallet, amount);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction req) throws Exception{
        User senderUser = userService.findUserByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);

        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser, receiverWallet, req.getAmount()
        );

        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
                wallet,
                WalletTransactionType.WALLET_TRANSFER, receiverWallet.getId().toString(),
                req.getPurpose(),
                -req.getAmount()
        );

//        transac
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long orderId
                                                         ) throws Exception{
        User user = userService.findUserByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);


        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(@RequestHeader("Authorization") String jwt,
                                                  @RequestParam(name = "payment_id") String paymentId,
                                                   @RequestParam(name = "order_id") Long orderId
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);


        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentService.getPaymentOrderById(orderId);

        Boolean status = paymentService.proceedPaymentOrder(order, paymentId);

        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposite success");

//        if (wallet.getBalance()==null){
//            wallet.setBalance(BigDecimal.valueOf(0));
//        }

        if (status){
            wallet = walletService.addBalance(wallet, order.getAmount());
        }


        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
