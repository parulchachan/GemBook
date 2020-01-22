package com.gemini.gembook.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.Comment;
import com.gemini.gembook.model.CompletePost;
import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.CommentRepository;
import com.gemini.gembook.repository.LikeRepository;
import com.gemini.gembook.repository.PostRepository;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private CommentRepository commentRepository;
	private LikeRepository likeRepository;
	private UsersRepository userRepository;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public PostService(PostRepository postRepository, CommentRepository commentRepository, 
			LikeRepository likeRepository, UsersRepository userRepository){
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;

	}
	
	public List<Post> getAllPosts() {
		List<Post> posts = null;
		try {
			posts = postRepository.findAll();
		}
		catch(Exception e) {
			logger.error("postRepository.findAll() throws an exception: {}",e.getMessage());
		}
		return posts; 
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
	
	public List<Post> getPostByType(int postTypeId) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByType(postTypeId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByType() throws an exception: {}",e.getMessage());
		}
		return posts;
	}
	
	public boolean updatePost(int postId, String postContent) {
		int status = ZERO;
		try {
			status = postRepository.updatePost(postId, postContent);
		}
		catch(Exception e) {
			logger.error("postRepository.updatePost throws an exception, {} ", e.getMessage());
		}
		return (ONE == status) ? true : false;
	}


	public List<Post> getNextPosts(long postTime) {
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
            logger.error("Exception in getnextfifteenPost() : {}",e.getMessage());
        }
		return posts;
	}
	
	public List<Post> getNextPostPage(PageRequest pageRequest) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.getNextPostPage(pageRequest);
        }
        catch (Exception e){
            logger.error("Exception in getnextfifteenPost() : {}",e.getMessage());
        }
		return posts;
	}
	
	public Post addPost(int postType, String userId, String postContent) {
		Post post = null;
		try {
			post = postRepository.save(new Post(postType,userId,postContent));
        }
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return post;
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
	
	public boolean deletePost(int postId,String userId) {
        int status = ZERO;
        try {
                status = postRepository.deletePost(postId,userId);
        }
        catch(Exception e) {
        	logger.error("Exception in deletePost() : {}",e.getMessage());
        }
        return (ONE == status) ? true : false;
    }
	
	@SuppressWarnings("null")
	public List<CompletePost> getCompletePost(){
		logger.info("inside complete post");
		List<CompletePost> completePosts = null;
		List<Post> posts = null;
		List<Like> likes = null;
		List<Comment> comments = null;
		try {
			completePosts = new ArrayList<>();
			posts = postRepository.getRecentPosts();
			Iterator<Post> iterator = posts.iterator();
			logger.info(posts.toString());
			while(iterator.hasNext()) {
				Post post = (Post) iterator.next();
				logger.info(post.toString());
				int postId = post.getPostId();
				System.out.println(postId);
				likes = likeRepository.findByLikeIdentityPost(post);
				logger.info(likes.toString());
				comments = commentRepository.getAllComments(postId);
				logger.info(comments.toString());
				CompletePost completePost = new CompletePost(post, likes, comments);
				logger.info(completePost.toString());
				completePosts.add(completePost);
				logger.info(completePosts.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCompletePost() : {}",e.getMessage());
		}
		return completePosts;
	
	}
	
	@SuppressWarnings("null")
	public List<CompletePost> getNextCompletePost(long postTime){
		logger.info("inside next complete post");
		List<CompletePost> completePosts = null;
		List<Post> posts = null;
		List<Like> likes = null;
		List<Comment> comments = null;
		try {
			completePosts = new ArrayList<>();
			posts = postRepository.getNextPosts(postTime);
			Iterator<Post> iterator = posts.iterator();
			logger.info(posts.toString());
			while(iterator.hasNext()) {
				Post post = (Post) iterator.next();
				logger.info(post.toString());
				int postId = post.getPostId();
				likes = likeRepository.findByLikeIdentityPost(post);
				logger.info(likes.toString());
				comments = commentRepository.getAllComments(postId);
				logger.info(comments.toString());
				CompletePost completePost = new CompletePost(post, likes, comments);
				logger.info(completePost.toString());
				completePosts.add(completePost);
				logger.info(completePosts.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCompletePost() : {}",e.getMessage());
		}
		return completePosts;
	
	}
	
	@SuppressWarnings("null")
	public List<CompletePost> getCompletePostByType(int postTypeId){
		logger.info("inside next complete post");
		List<CompletePost> completePosts = null;
		List<Post> posts = null;
		List<Like> likes = null;
		List<Comment> comments = null;
		try {
			completePosts = new ArrayList<>();
			posts = postRepository.getPostByType(postTypeId);
			Iterator<Post> iterator = posts.iterator();
			logger.info(posts.toString());
			while(iterator.hasNext()) {
				Post post = (Post) iterator.next();
				logger.info(post.toString());
				int postId = post.getPostId();
				likes = likeRepository.findByLikeIdentityPost(post);
				logger.info(likes.toString());
				comments = commentRepository.getAllComments(postId);
				logger.info(comments.toString());
				CompletePost completePost = new CompletePost(post, likes, comments);
				logger.info(completePost.toString());
				completePosts.add(completePost);
				logger.info(completePosts.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in getCompletePost() : {}",e.getMessage());
		}
		return completePosts;
	}
	
}
