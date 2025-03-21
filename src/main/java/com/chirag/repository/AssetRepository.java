package com.chirag.repository;

import com.chirag.modal.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinId(Long userId, String coinId);

    Asset findByIdAndUserId(Long assetId, Long userId);

//    Optional<Asset> findByUserIdAndSymbolAndPortfolioId(Long userId, String symbol, Long portfolioId);
}
