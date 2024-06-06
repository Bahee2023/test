package com.web.registry.controller;

import com.web.registry.dto.SignupDTO;
import com.web.registry.dto.UserDTO;
import com.web.registry.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO){

        if(userService.hasUserWithEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO createUser = userService.createUser(signupDTO);
        if (createUser == null){
            return new ResponseEntity<>("User not created. Come again later!", HttpStatus.BAD_REQUEST);
        } return new ResponseEntity<>(createUser,HttpStatus.CREATED);

    }

}
