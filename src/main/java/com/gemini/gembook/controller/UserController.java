package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.User;
import com.gemini.gembook.service.UsersService;

@RestController
@RequestMapping(value = "/dashboard/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersService usersService;

    /*
    Returns a list of all users
    */
    @GetMapping(value = "/allusers")
    public BaseResponse getAllUsers(){
        List<User> users = (List<User>) usersService.getUsers();

        if(users == null) {
        	logger.info("user not found.");
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
            
        }

        logger.info("Users retrieved");
        return new BaseResponse("Success",HttpStatus.OK, users);
    }
	
	
    @PostMapping
    public BaseResponse addUser(@RequestParam(value = "firstname") String firstName,
    		@RequestParam(value = "lastname") String lastName, @RequestParam(value = "username") String userName, @RequestParam(value = "password")char[] password){

        User user = usersService.addUser(new User(firstName, lastName, userName, password));
        if(user == null) {
            //logger.warn("User : {} not added",name);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        //logger.info("User created : {}",user);
        return new BaseResponse("Success",HttpStatus.CREATED,user);
    }
    
}
