package com.lambda.UserService.Controller;


import com.lambda.UserService.Service.IUserService;
import com.lambda.UserService.Model.Api.UserLoginAckDTO;
import com.lambda.UserService.Model.Api.UserLoginDTO;
import com.lambda.UserService.Model.Entity.UserCredentials;
import com.lambda.UserService.Model.Entity.UserInfo;
import com.sun.research.ws.wadl.HTTPMethods;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = {"*"})
public class UserController {

    @Autowired
    IUserService userService;


    @ApiOperation("Get user info by userId")
    @GetMapping("user-info/{id}")
    public UserInfo getUserInfo(@PathVariable long id, @RequestHeader(value = "Authorization") String authorization) throws Exception {
        UserLoginAckDTO userLoginAckDTO = this.userService.isUserAuthorized(id, authorization);
        if (!userLoginAckDTO.isAuthenticated()) {
            throw new AccessDeniedException("User is not authorized");
        }
        return this.userService.findById(id);
    }

    @ApiOperation("Register user")
    @PostMapping("user-info/sign-up")
    public UserInfo registerUser (@RequestBody UserCredentials info) {
        return this.userService.createOrUpdateUser(info);
    }

    @ApiOperation("Update user info")
    @PutMapping("/user-info/update")
    public UserInfo updateUserInfo (@RequestBody UserCredentials info, @RequestHeader(value = "Authorization") String authorization) throws AccessDeniedException {
        if (info.getUser().getUserId() == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        Long id = info.getUser().getUserId();
        UserLoginAckDTO userLoginAckDTO = this.userService.isUserAuthorized(id, authorization);
        if (!userLoginAckDTO.isAuthenticated()) {
            throw new AccessDeniedException("User is not authorized");
        }
        return  this.userService.createOrUpdateUser(info);
    }

    @ApiOperation("Sign in")
    @PostMapping("/sign-in")
    public UserLoginAckDTO userLogin (@RequestBody UserLoginDTO userLoginDTO) {
        return  this.userService.login(userLoginDTO);
    }

    @ApiOperation("Check if the user is authorized by userId and Auth token")
    @GetMapping("/is-user-authorized/{userId}")
    public UserLoginAckDTO isUserAuthorized (@PathVariable long userId, @RequestHeader(value = "Authorization") String authorization) {
        return this.userService.isUserAuthorized(userId, authorization);
    }

    @ApiOperation("Delete user by user id")
    @DeleteMapping("delete/{userId}")
    public boolean deleteUser(@PathVariable long userId, @RequestHeader(value = "Authorization") String authorization) throws AccessDeniedException {
        UserLoginAckDTO userLoginAckDTO = this.userService.isUserAuthorized(userId, authorization);
        if (!userLoginAckDTO.isAuthenticated()) {
            throw new AccessDeniedException("User is not authorized");
        }
        return this.userService.deleteUser(userId);
    }
}
