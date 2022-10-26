package io.swagger.services;

import io.swagger.model.ModelApiResponse;
import io.swagger.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> createUsersWithListInput(List<User> users);
    ModelApiResponse deleteUser(String username);
    User findByUserName(String username);
    ModelApiResponse updateUser(String username, User updatedUser);
}
