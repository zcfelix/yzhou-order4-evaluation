package com.thoughtworks.ketsu.infrastructure.mybatis.mappers;

import com.thoughtworks.ketsu.domain.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    void save(@Param("info") Map<String, Object> info);

    Order findById(@Param("id") int id);

    List<Order> findAll();
}
