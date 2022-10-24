package io.swagger.services.Impl;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }
}
