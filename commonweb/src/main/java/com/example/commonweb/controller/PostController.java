package com.example.commonweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.commonweb.entity.Post;
import com.example.commonweb.repository.PostRepository;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepository;

	/*
	 * General form
	 */
//	@GetMapping("/posts/{id}")
//	public Post getPost(@PathVariable Long id) {
//		Optional<Post> byId = postRepository.findById(id);
//		Post post = byId.get();
//		return post;
//	}

	/*
	 * Using DomainClassConverter
	 */
	@GetMapping("/posts/{id}")
	public Post getPost(@PathVariable("id") Post post) {
		return post;
	}

	/*
	 * Pagable and Sort
	 */
	@GetMapping("/posts")
	public Page<Post> getPosts(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	/*
	 * HATEOAS
	 */
	@GetMapping("/posts/hateoas")
	public PagedResources<Resource<Post>> getPostsByHateoas(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
		return assembler.toResource(postRepository.findAll(pageable));
	}
 
}
