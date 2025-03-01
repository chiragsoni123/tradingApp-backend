package com.chirag.repository;

import com.chirag.modal.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByUserId(Long userId);

    Optional<Wallet> findById(Long id);
}
