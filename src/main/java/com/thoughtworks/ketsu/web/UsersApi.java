package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.*;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("users")
public class UsersApi {
    @Context
    UserRepository userRepository;

    @Context
    Routes routes;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Map<String, Object> info) {
        List<String> invalidParamsList = new ArrayList<>();
        if (info.getOrDefault("name", "").toString().trim().isEmpty())
            invalidParamsList.add("name");
        if (invalidParamsList.size() > 0)
            throw new InvalidParameterException(invalidParamsList);

        return Response.created(routes.userUri(userRepository.createUser(info))).build();
    }

    @Path("{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
    }

}

