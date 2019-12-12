package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @GetMapping(value="/allusers")
    public BaseResponse getAllUsers(){
        List<User> users = (List<User>) usersService.getUsers();

        if(users == null) {
        	logger.info("users could not be fetched");
            return new BaseResponse("users could not be fetched", HttpStatus.INTERNAL_SERVER_ERROR,null);            
        }
        
        if(users.isEmpty()) {
        	logger.info("users not found");
            return new BaseResponse("users not found", HttpStatus.NOT_FOUND,null);
        }

        logger.info("Users retrieved");
        return new BaseResponse("Success",HttpStatus.OK, users);
    }
	
	
    @PostMapping
    public BaseResponse addUser(@RequestParam(value = "firstName") String firstName,
    		@RequestParam(value = "lastName") String lastName, @RequestParam(value = "userId") String userId){

        User user = usersService.addUser(new User(firstName, lastName, userId));
        if(user == null) {
            logger.error("User : {} not added",userId);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("User created : {}", userId);
        return new BaseResponse("Success",HttpStatus.CREATED,user);
    }
    
    
    @GetMapping(value = "/userdetail")
    public BaseResponse getUserByUserName(@RequestParam(value = "userId") String userId){
        User user = usersService.findByUserName(userId);

        if(user == null) {
        	logger.info("user not found.");
            return new BaseResponse("User not found", HttpStatus.NOT_FOUND,null);            
        }

        logger.info("User {}, retrieved",userId);
        return new BaseResponse("Success",HttpStatus.OK, user);
    }
    
    
    @DeleteMapping
    public BaseResponse deleteUser(@RequestParam(value = "userId") String userId) {
    	
    	User user = usersService.findByUserName(userId);
    	if(null == user) {
    		logger.warn("user does not exist");
    		return new BaseResponse("user does not exist",HttpStatus.NOT_FOUND,null);
    	}
    	
    	if(usersService.deleteUser(userId)) {
    		logger.info("user deleted successfully.");
    		return new BaseResponse("User deleted successfully",HttpStatus.OK,true);
    	}
    	else {
    		logger.error("user: {} could not be deleted",userId);
    		return new BaseResponse("User could not be deleted",HttpStatus.INTERNAL_SERVER_ERROR,
    				false);
    	}
    }

}
