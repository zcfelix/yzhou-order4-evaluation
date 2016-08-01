package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(DatabaseTestRunner.class)
public class PaymentManipulationTest {
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    private User user;
    private Product product1, product2;
    private Order order;
    private String paymentBaseUri;

    @Before
    public void setUp() throws Exception {
        user = userRepository.createUser(TestHelper.userMap("felix"));
        product1 = productRepository.createProduct(TestHelper.productMap("apple"));
        product2 = productRepository.createProduct(TestHelper.productMap("banana"));
        order = user.createOrder(TestHelper.orderMap("kitty", product1, product2));
        paymentBaseUri = "users/" + user.getId() + "/orders/" + order.getId() + "/payment";
    }

    @Test
    public void should_create_and_find_payment() {
        Payment payment = order.createPayment(TestHelper.paymentMap());
        assertThat(payment.getOrderId(), is(order.getId()));
    }
}
