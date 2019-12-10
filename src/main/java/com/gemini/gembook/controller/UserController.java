package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.User;
import com.gemini.gembook.service.UsersService;

@RestController
@RequestMapping(value = "/GemBook")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersService usersService;

    /*
    Returns a list of all users
    */
    @GetMapping
    public BaseResponse getAllUsers(){
        List<User> users = (List<User>) usersService.getUsers();

        if(users == null) {
        	logger.info("user not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
            
        }

        logger.info("Users retrieved");
        return new BaseResponse("Success",HttpStatus.OK, users);
    }
}
