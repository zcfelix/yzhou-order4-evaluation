package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Record {
    @Inject
    OrderMapper orderMapper;

    private int orderId;
    private String payType;
    private double amount;
    private Date time;

    public Payment() {
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        Order order = orderMapper.findById(orderId);
        return new HashMap<String, Object>() {{
            put("order_uri", routes.orderUri(order));
            put("uri", routes.paymentUri(Payment.this, order.getUserId()));
            put("pay_type", payType);
            put("amount", amount);
            put("created_at", time);
        }};
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }
}
