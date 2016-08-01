package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("users/{userId}/orders/{orderId}/payment")

public class PaymentApi {

    @Context
    Routes routes;

    @Context
    UserRepository userRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(Map<String, Object> info,
                                  @PathParam("userId") int userId,
                                  @PathParam("orderId") int orderId) {
        List<String> invalidParamsList = new ArrayList<>();
        if (info.getOrDefault("pay_type", "").toString().trim().isEmpty())
            invalidParamsList.add("pay_type");
        if (info.getOrDefault("amount", "").toString().trim().isEmpty())
            invalidParamsList.add("amount");
        if (invalidParamsList.size() > 0)
            throw new InvalidParameterException(invalidParamsList);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        Order order = user.findOrderById(orderId).orElseThrow(() -> new NotFoundException("order not found"));
        Payment payment = order.createPayment(info);
        return Response.created(routes.paymentUri(payment, userId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Payment findPayment(@PathParam("userId") int userId,
                               @PathParam("orderId") int orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        Order order = user.findOrderById(orderId).orElseThrow(() -> new NotFoundException("order not found"));
        return order.findPayment().orElseThrow(() -> new NotFoundException("payment not found"));
    }
}
