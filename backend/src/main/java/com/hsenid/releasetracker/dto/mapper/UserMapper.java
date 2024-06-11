package com.hsenid.releasetracker.dto.mapper;

import com.hsenid.releasetracker.dto.UserResponse;
import com.hsenid.releasetracker.model.User;

public class UserMapper {
    public static UserResponse userToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}