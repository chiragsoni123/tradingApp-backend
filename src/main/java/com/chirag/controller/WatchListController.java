package com.chirag.controller;

import com.chirag.modal.Coin;
import com.chirag.modal.User;
import com.chirag.modal.WatchList;
import com.chirag.service.CoinService;
import com.chirag.service.UserService;
import com.chirag.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchlist(
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwt(jwt);

        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

//    @PostMapping("/create")
//    public ResponseEntity<WatchList> createWatchlist(
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception{
//
//        User user = userService.findUserByJwt(jwt);
//        WatchList createdWatchlist = watchListService.createWatchList(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchlist);
//    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchlistById( @PathVariable Long watchlistId) throws Exception{

        WatchList watchList = watchListService.findById(watchlistId);
        return ResponseEntity.ok(watchList);
    }

    @PostMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId
    ) throws Exception{

        User user = userService.findUserByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchList(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
