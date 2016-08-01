package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport{

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    private User user;
    private Product product1, product2;
    private Order order;
    private String paymentBaseUri;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        user = userRepository.createUser(TestHelper.userMap("felix"));
        product1 = productRepository.createProduct(TestHelper.productMap("apple"));
        product2 = productRepository.createProduct(TestHelper.productMap("banana"));
        order = user.createOrder(TestHelper.orderMap("kitty", product1, product2));
        paymentBaseUri = "users/" + user.getId() + "/orders/" + order.getId() + "/payment";
    }


    @Test
    public void should_return_201_and_location_when_create_payment() {
        final Response POST = post(paymentBaseUri, TestHelper.paymentMap());
        assertThat(POST.getStatus(), is(201));
        assertThat(Pattern.matches(".*?/users/[0-9-]*/orders/[0-9-]*/payment", POST.getLocation().toASCIIString()), is(true));
    }

    @Test
    public void should_return_400_when_create_invalid_payment() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("pay_type", "");
            put("amount", "");
        }};
        final Response POST = post(paymentBaseUri, map);
        assertThat(POST.getStatus(), is(400));
    }

    @Test
    public void should_return_200_and_details_when_find_payment() {
        order.createPayment(TestHelper.paymentMap());
        final Response GET = get(paymentBaseUri);
        Map<String, Object> ret = GET.readEntity(Map.class);
        assertThat(GET.getStatus(), is(200));
        assertThat(ret.get("pay_type"), is("CASH"));
    }

    @Test
    public void should_return_404_when_payment_not_found() {
        order.createPayment(TestHelper.paymentMap());
        final Response GET = get("users/" + user.getId() + "/orders/" + (order.getId() + 1) + "/payment");
        assertThat(GET.getStatus(), is(404));
    }

}
