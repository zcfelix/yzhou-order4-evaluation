package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.ProductMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.*;

public class User implements Record {
    @Inject
    OrderMapper orderMapper;

    @Inject
    ProductMapper productMapper;

    private String id;
    private String name;

    public User() {
    }

    public int getId() {
        return Integer.valueOf(id);
    }

    public String getName() {
        return name;
    }

    public Order createOrder(Map<String, Object> info) {
        info.put("user_id", id);
        //info.put("total_price", 0d);
        double total_price = 0d;
        List<Map<String, Object>> orderItems = new ArrayList<>();
        for (Map<String, Object> item : (List<Map<String, Object>>)info.get("order_items")) {
            int productId = Integer.valueOf(item.get("product_id").toString());
            int quantity = Integer.valueOf(item.get("quantity").toString());
            double priceEach = productMapper.findById(productId).getPrice();
            double amount = quantity * priceEach;
            item.put("amount", amount);
            total_price += amount;
        }
        info.put("total_price", total_price);
        //info.put("order_items", orderItems);
        orderMapper.save(info);
        return orderMapper.findById(Integer.valueOf(info.get("id").toString()));
    }

    public Optional<Order> findOrderById(int orderId) {
        return Optional.ofNullable(orderMapper.findById(orderId));
    }

    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>() {{
            put("uri", routes.userUri(User.this));
            put("id", id);
            put("name", name);
        }};
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

}
