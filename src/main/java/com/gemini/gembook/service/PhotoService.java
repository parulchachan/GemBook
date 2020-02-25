package com.gemini.gembook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.gembook.model.Photo;
import com.gemini.gembook.repository.PhotoRepository;

@Service
public class PhotoService {
	

	private PhotoRepository photoRepository;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private final int ZERO = 0;
	private final int ONE = 1;
	
	
	@Autowired
	public PhotoService(PhotoRepository photoRepository){
		this.photoRepository = photoRepository;
	}
	
	public Photo addPhoto(Photo photo) {
		Photo uploadPhoto = null;
		try {
			uploadPhoto = photoRepository.save(photo);
		}
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return uploadPhoto;
	}

}
