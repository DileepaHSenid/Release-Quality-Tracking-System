package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.dto.UserResponse;
import com.hsenid.releasetracker.dto.mapper.UserMapper;
import com.hsenid.releasetracker.exception.AlreadyExistsException;
import com.hsenid.releasetracker.exception.UserNotFoundException;
import com.hsenid.releasetracker.model.User;
import com.hsenid.releasetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(UserMapper.userToUserResponse(user));
        }
        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return userResponses;
    }

    public User addUserdetails(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    throw new AlreadyExistsException("User already exists with username: "+user.getUsername());
                });
        return userRepository.save(user);
    }

    public UserResponse fetchUsingId(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id: " + id));
        return UserMapper.userToUserResponse(user);
    }

    public void deleteById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id: " + id));
        userRepository.deleteById(id);
    }
}
