package com.thoughtworks.ketsu.domain.user;

import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    User createUser(Map<String, Object> info);

    Optional<User> findById(int id);
}
