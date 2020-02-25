package com.gemini.gembook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gemini.gembook.service.PhotoService;


@RestController
@RequestMapping(value="/dashboard/post/document")
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
//	
//	@PostMapping( value="/save")
//	public BaseResponse saveDocuments(@RequestParam String postId,
//								@RequestParam MultipartFile photo,){
//		
//	}
}
