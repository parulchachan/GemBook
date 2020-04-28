package com.gemini.gembook.service;

import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.gemini.gembook.model.Photo;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.PhotoRepository;
import com.gemini.gembook.repository.PostRepository;
import com.gemini.gembook.repository.UsersRepository;
import com.google.common.io.Files;

import org.springframework.mock.web.MockMultipartFile;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UsersRepository userRepository;
	private PhotoRepository photoRepository;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public PostService(PostRepository postRepository, UsersRepository userRepository, PhotoRepository photoRepository ){
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.photoRepository = photoRepository;
	}
	
	public List<Post> getAllPosts() {
		List<Post> posts = null;
		try {
			posts = postRepository.getRecentPosts();
		}
		catch(Exception e) {
			logger.error("postRepository.getRecentPosts() throws an exception: {}",e.getMessage());
		}
		return posts; 
	}
	
	public Post findByPostId(int postId){

		Post post = null;
        try{
        	post = postRepository.findByPostId(postId);
        }
        catch(Exception e){
            logger.error("Exception in findByPostId() : {}",e.getMessage());
        }
        return  post;
    }
	
	public List<Post> getPostByUser(String userId) {
		List<Post> posts = null;
		try {
			User user = null;
			user = this.userRepository.findByUserName(userId);
			if(user == null) return posts;
			posts = postRepository.getPostByUserName(userId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByUserName() throws an exception: {}",e.getMessage());
		}
		return posts;
	}
	
	public List<Post> getPostByPostType(int postTypeId) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByType(postTypeId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByType() throws an exception: {}",e.getMessage());
		}
		return posts;
	}

	public List<Post> getNextPost(long postTime) {
		List<Post> posts = null;
		
		try {
			if(ZERO == postTime) {
				posts = postRepository.getRecentPosts();
			}
			else {
				posts = postRepository.getNextPosts(postTime);
			}
        }
        catch (Exception e){
            logger.error("Exception in getNextPost() : {}",e.getMessage());
        }
		return posts;
	}
	
	public Post addPost(int postType, String userId, String postContent, MultipartFile files) {
		Post post = null;
		try {
			post = postRepository.save(new Post(postType,userId,postContent));
			if(files != null) {
					System.out.println(files.getSize());
					if(files.getSize()>100) {
					File convFile = new File(files.getOriginalFilename());
					files.transferTo(convFile);
					
			        BufferedImage image = ImageIO.read(convFile);
	
			        File output = new File(files.getOriginalFilename());
			        OutputStream out = new FileOutputStream(output);
			        

					String fileType = Files.getFileExtension(files.getOriginalFilename());
			        
			        ImageWriter writer =  ImageIO.getImageWritersByFormatName(fileType).next();
			        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
			        writer.setOutput(ios);
	
			        ImageWriteParam param = writer.getDefaultWriteParam();
			        if (param.canWriteCompressed()){
			            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			            param.setCompressionQuality(0.5f);
			            System.out.println("compressed");
			        }
	
			        writer.write(null, new IIOImage(image, null, null), param);
	
			        out.close();
			        ios.close();
			        writer.dispose();
			        
			        byte[] bytesArray = new byte[(int) output.length()];
			        FileInputStream fis = new FileInputStream(output);
			        fis.read(bytesArray); //read file into bytes[]
			        fis.close();
			        files = new MockMultipartFile(files.getOriginalFilename(),bytesArray);
					
			        System.out.println(files.getSize());
				}
				
				Photo photo = new Photo(post.getPostId(),files);
				this.photoRepository.save(photo);
			}
        }
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return post;
	}
	
	public boolean updatePost(int postId, String postContent, MultipartFile files) {
		int status = ZERO;
		try {
			status = postRepository.updatePost(postId, postContent);
//			this.photoRepository.deletePhotos(postId);
			if(files != null) {
				Photo photo = new Photo(postId,files);
				this.photoRepository.save(photo);
			}
		}
		catch(Exception e) {
			logger.error("postRepository.updatePost throws an exception, {} ", e.getMessage());
		}
		return (ONE == status) ? true : false;
	}
	
	public boolean deletePost(int postId,String userId) {
        int status = ZERO;
        try {
        	Post post = postRepository.findByPostId(postId);
        	if(userId.equals(post.getUser().getUserId())){
                postRepository.delete(post);
        		status= ONE;
        	}	
        	else {
        		logger.info("user not matched");
        	}
        }
        catch(Exception e) {
        	logger.error("Exception in deletePost() : {}",e.getMessage());
        }
        return (ONE == status) ? true : false;
    }
	
}
