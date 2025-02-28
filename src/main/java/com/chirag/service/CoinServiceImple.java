package com.chirag.service;

import com.chirag.modal.Coin;
import com.chirag.repository.CoinRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImple implements CoinService{

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public List<Coin> getCoinList(int page) {
        return List.of();
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        return "";
    }

    @Override
    public String getCoinDetails(String coinId) {
        return "";
    }

    @Override
    public Coin findById(String coinId) {
        return null;
    }

    @Override
    public String searchCoin(String keyword) {
        return "";
    }

    @Override
    public String getTop50CoinsByMarketCapRank() {
        return "";
    }

    @Override
    public String getTreadingCoins() {
        return "";
    }
}
