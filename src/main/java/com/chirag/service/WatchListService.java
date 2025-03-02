package com.chirag.service;

import com.chirag.modal.Coin;
import com.chirag.modal.User;
import com.chirag.modal.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;

    WatchList createWatchList(User user);

    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
