package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestHelper {
    private static int auto_increment_key = 1;

    public static Map<String, Object> productMap(String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("description", "delicious");
            put("price", 2.5);
        }};
    }

    public static Map<String, Object> userMap(String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
        }};
    }

    public static Map<String, Object> orderMap(String name, Product product1, Product product2) {
        List<Map<String, Object>> orderItems = new ArrayList<>();
        orderItems.add(new HashMap<String, Object>() {{
            put("product_id", product1.getId());
            put("quantity", 200);
        }});
        orderItems.add(new HashMap<String, Object>() {{
            put("product_id", product2.getId());
            put("quantity", 202);
        }});

        return new HashMap<String, Object>() {{
            put("name", name);
            put("address", "xian");
            put("phone", "13996630396");
            put("order_items", orderItems);
        }};
    }

    public static Map<String, Object> paymentMap() {
        return new HashMap<String, Object>() {{
            put("pay_type", "CASH");
            put("amount", 3206d);
        }};
    }

}
