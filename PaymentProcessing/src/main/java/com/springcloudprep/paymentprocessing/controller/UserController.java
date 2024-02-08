package com.springcloudprep.paymentprocessing.controller;

import com.springcloudprep.paymentprocessing.models.User;
import com.springcloudprep.paymentprocessing.payloads.UserDto;
import com.springcloudprep.paymentprocessing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add-new-user")
    public User addNewUser(@RequestBody UserDto userDto) {
        return userService.addNewUser(userDto);
    }

    //todo: Implement controller to update user
    //todo: Implement controller to read user information
    //todo: Implement controller to delete user
}
