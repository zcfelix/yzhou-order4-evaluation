package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.PaymentMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.*;

public class Order implements Record {
    @Inject
    PaymentMapper paymentMapper;

    private int id;
    private int userId;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
    private Date time;
    private List<Map<String, Object>> orderItems;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Payment createPayment(Map<String, Object> info) {
        info.put("order_id", id);
        paymentMapper.save(info);
        return paymentMapper.findByOrderId(Integer.valueOf(info.get("order_id").toString()));
    }

    public Optional<Payment> findPayment() {
        return Optional.ofNullable(paymentMapper.findByOrderId(id));
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>() {{
            put("uri", routes.orderUri(Order.this));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", totalPrice);
            put("created_at", time);
            put("order_items", orderItems);
        }};
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap<String, Object>() {{
            put("uri", routes.orderUri(Order.this));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", totalPrice);
            put("created_at", time);
        }};
    }
}
