package com.test.shop.controller;

import com.test.shop.dto.UserDto;
import com.test.shop.exceptions.AlreadyExistsException;
import com.test.shop.exceptions.ResourceNotFoundException;
import com.test.shop.model.User;
import com.test.shop.request.CreateUserRequest;
import com.test.shop.request.UserUpdateRequest;
import com.test.shop.response.ApiResponse;
import com.test.shop.service.user.IUserService;
import com.test.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

//    @GetMapping("/{userId}/user")
//    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
//        try {
//            User user = userService.getUserById(userId);
//            UserDto userDto = userService.convertUserToDto(user);
//            return ResponseEntity.ok(new ApiResponse("Sucess", userDto));
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
//        }
//    }
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            System.out.println("DEBUG user id=" + user.getId() + ", email=" + user.getEmail());
            UserDto userDto = userService.convertUserToDto(user);  // Có thể lỗi ở dòng này
            return ResponseEntity.ok(new ApiResponse("Sucess", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();  // IN RA STACKTRACE ĐỂ XÁC ĐỊNH DÒNG NÀO LỖI
            return ResponseEntity.status(500).body(new ApiResponse("Internal error: " + e.getMessage(), null));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
