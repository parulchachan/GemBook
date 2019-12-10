package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class UsersService {
	private UsersRepository usersRepository;
	private final Logger logger  = LoggerFactory.getLogger(UsersService.class);
	@Autowired
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	public User addUser(User user) {
		
//		if(!usersRepository.findOne(example) {
//			usersRepository.addUser(user);
//		}
		//usersRepository.findByUserName(user.getUserName());
//		usersRepository.insertUser(user.getFirstName(),user.getLastName(),user.getUserName(),user.getPassword());
//		return true;
		return usersRepository.save(user);
		
	}
	
	public List<User> getUsers() {
		List<User> users = null;
        try {
            users = usersRepository.findAll();
        }catch (Exception e){
            logger.error("Exception in findAll() : {}",e.getMessage());
        }
        return users;
	}
	
	
}
