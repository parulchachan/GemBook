package com.gemini.gembook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.PostType;
import com.gemini.gembook.repository.PostTypeRepository;

@Service
public class PostTypeService {
	
	private Logger logger = LoggerFactory.getLogger(UsersService.class);
	
	@Autowired
	PostTypeRepository postTypeRepository;
	
	public PostType getPostTypeById(int postTypeId) {
		PostType postType = null;
		try {
			postType = postTypeRepository.findByPostTypeId(postTypeId);
		}
		catch(Exception e) {
			logger.error("postTypeRepository.findByPostTypeId throws an exception: {} ",e.getMessage());
		}
		return postType;
	}
}
