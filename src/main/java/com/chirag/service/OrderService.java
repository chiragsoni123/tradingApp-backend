package com.chirag.service;

import com.chirag.domain.OrderType;
import com.chirag.modal.Coin;
import com.chirag.modal.Order;
import com.chirag.modal.OrderItem;
import com.chirag.modal.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, String orderType, String assetSymbol);

    void cancelOrder(Long orderId) throws Exception;

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
