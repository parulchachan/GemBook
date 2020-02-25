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
		try {
			usersRepository.insertUser(user.getFirstName(), user.getLastName(), user.getUserId());
		}
		catch(Exception e) {
			logger.error("usersRepository.addUser method throws an exception, {} ",e.getMessage());
			return null;
		}
		return user;
	}
	
	public List<User> getUsers() {
		List<User> users = null;
        try {
            users = usersRepository.findAll();
        }catch (Exception e){
            logger.error("usersRepository.findAll() throws an exception: {}",e.getMessage());
        }
        return users;
	}
	
	public User findByUserName(String userName) {
		User user = null;
		try {
		  user = usersRepository.findByUserName(userName);
		}
		catch(Exception e) {
			logger.error("usersReposiory.findByUserName throws an exception, {} ",e.getMessage());
		}
		return user;
	}
	
	public boolean deleteUser(String userId) {
		try {
			User user = new User(userId);
			usersRepository.delete(user);
		}
		catch(Exception e) {
			logger.error("usersRepository.deleteByUserName throws an exception, {} ",e.getMessage());
		}
		return findByUserName(userId) == null;
	}
	
}
