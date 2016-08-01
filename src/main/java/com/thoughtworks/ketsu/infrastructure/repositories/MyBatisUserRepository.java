package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.domain.user.UserId;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.UserMapper;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.ketsu.util.Json.mapper;

public class MyBatisUserRepository implements UserRepository {
    @Inject
    UserMapper userMapper;

    @Override
    public User createUser(Map<String, Object> info) {
        userMapper.save(info);
        return userMapper.findById(Integer.valueOf(info.get("id").toString()));
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(userMapper.findById(id));
    }

}
