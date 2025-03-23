package com.chirag.service;

import com.chirag.modal.CoinDTO;
import com.chirag.response.ApiResponse;

public interface ChatBotService {
    ApiResponse getCoinDetails(String coinName);

    CoinDTO getCoinByName(String coinName);

    String simpleChat(String prompt);
}
