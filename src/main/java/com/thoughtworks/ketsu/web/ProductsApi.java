package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.omg.CORBA.DynAnyPackage.Invalid;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("products")
public class ProductsApi {
    @Context
    Routes routes;

    @Context
    ProductRepository productRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Map<String, Object> info) {
        List<String> invalidParamsList = new ArrayList<>();
        if (info.getOrDefault("name", "").toString().trim().isEmpty())
            invalidParamsList.add("name");
        if (info.getOrDefault("description", "").toString().trim().isEmpty())
            invalidParamsList.add("description");
        if (info.getOrDefault("price", "").toString().trim().isEmpty())
            invalidParamsList.add("price");
        if (invalidParamsList.size() > 0)
            throw new InvalidParameterException(invalidParamsList);
        return Response.created(routes.productUri(productRepository.createProduct(info))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Path("{productId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("productId") int productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product not found"));
    }
}
