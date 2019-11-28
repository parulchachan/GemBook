package com.gemini.gembook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class UsersService {
	private UsersRepository usersRepository;
	
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
	
	public Iterable<User> getUsers() {
		return usersRepository.findAll();
	}
	
	
}
