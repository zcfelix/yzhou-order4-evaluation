package com.thoughtworks.ketsu.infrastructure.mybatis.mappers;

import com.thoughtworks.ketsu.domain.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    void save(@Param("info") Map<String, Object> info);

    Product findById(@Param("id") int id);

    List<Product> findAll();

}
