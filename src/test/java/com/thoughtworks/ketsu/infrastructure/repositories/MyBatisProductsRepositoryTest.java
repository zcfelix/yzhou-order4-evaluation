package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(DatabaseTestRunner.class)
public class MyBatisProductsRepositoryTest {
    @Inject
    ProductRepository productRepository;

    @Test
    public void should_create_and_find_product_by_id() {
        Product product =  productRepository.createProduct(TestHelper.productMap("apple"));
        assertThat(product.getName(), is("apple"));
    }

    @Test
    public void should_return_products_list() {
        Product product1 =  productRepository.createProduct(TestHelper.productMap("apple"));
        Product product2 =  productRepository.createProduct(TestHelper.productMap("banana"));
        List<Product> productsList = productRepository.findAll();
        assertThat(productsList.size(), is(2));
    }
}
