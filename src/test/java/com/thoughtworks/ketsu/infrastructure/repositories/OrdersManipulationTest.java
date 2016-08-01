package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(DatabaseTestRunner.class)
public class OrdersManipulationTest {
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    private User user;
    Product product1, product2;
    private String ordersBaseUri;

    @Before
    public void setUp() throws Exception {
        user = userRepository.createUser(TestHelper.userMap("felix"));
        product1 = productRepository.createProduct(TestHelper.productMap("apple"));
        product2 = productRepository.createProduct(TestHelper.productMap("banana"));
        ordersBaseUri = "users/" + user.getId() + "/orders";
    }

    @Test
    public void should_create_order_and_find_by_id() {
        Order order = user.createOrder(TestHelper.orderMap("kitty", product1, product2));
        Order orderGet = user.findOrderById(order.getId()).get();
        assertThat(order.getName(), is(orderGet.getName()));
        assertThat(order.getId(), is(orderGet.getId()));
    }

    @Test
    public void should_get_order_list_when_get_all_orders() {
        Order order1 = user.createOrder(TestHelper.orderMap("kitty", product1, product2));
        Order order2 = user.createOrder(TestHelper.orderMap("alex", product1, product2));
        List<Order> ordersList = user.findAll();
        assertThat(ordersList.size(), is(2));
    }
}
