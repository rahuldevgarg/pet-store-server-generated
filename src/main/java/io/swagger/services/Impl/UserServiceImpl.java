package io.swagger.services.Impl;

import io.swagger.model.ModelApiResponse;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setId(null);
        try{
            user = userRepository.save(user);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
        return user;
    }

    @Override
    public List<User> createUsersWithListInput(List<User> users) {
        List<User> saved = new LinkedList<>();
        StringBuilder errs = new StringBuilder();
        users.forEach(user -> {
            user.setId(null);
            try{
                user = userRepository.save(user);
                saved.add(user);
            }catch (Exception e){
                errs.append(e.getMessage()).append("\n");
            }
        });
        LOGGER.error(errs.toString());
        return saved;
    }

    @Override
    public ModelApiResponse deleteUser(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        ModelApiResponse modelApiResponse = new ModelApiResponse();
        if(!foundUser.isPresent()){
            modelApiResponse.setCode(404);
            return modelApiResponse;
        }
        userRepository.delete(foundUser.get());
        LOGGER.info(username+" deleted");
        modelApiResponse.setCode(200);
        return modelApiResponse;
    }

    @Override
    public User findByUserName(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.orElse(null);
    }

    @Override
    public ModelApiResponse updateUser(String username, User updatedUser) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        ModelApiResponse modelApiResponse = new ModelApiResponse();
        if(!foundUser.isPresent()){
            modelApiResponse.setCode(404);
            return modelApiResponse;
        }
        User user = foundUser.get();
        if(!Objects.equals(user.getId(), updatedUser.getId())){
            modelApiResponse.setCode(400);
            return modelApiResponse;
        }
        userRepository.save(updatedUser);
        modelApiResponse.setCode(200);
        return modelApiResponse;
    }
}
