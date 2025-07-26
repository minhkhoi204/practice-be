package com.test.shop.service.user;

import com.test.shop.dto.UserDto;
import com.test.shop.model.User;
import com.test.shop.request.CreateUserRequest;
import com.test.shop.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
