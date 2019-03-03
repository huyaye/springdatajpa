package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;
	
	@Test
	@Rollback(false)
	public void crudRepository() {
		// Given
		Post post = new Post();
		post.setTitle("hello spring boot common");
		assertThat(post.getId()).isNull();
		
		// When
		Post newPost = postRepository.save(post);
		
		// Then
		assertThat(newPost.getId()).isNotNull();
		
		// When
		List<Post> posts = postRepository.findAll();
		
		// Then
		assertThat(posts.size()).isEqualTo(1);
		assertThat(posts.contains(newPost));
	}
}
