package com.example.commonweb;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.commonweb.entity.Post;
import com.example.commonweb.repository.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class PostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PostRepository postRepository;

	@Test
	public void getPost() throws Exception {
		Post post = new Post();
		post.setTitle("jpa");
		postRepository.save(post);
		
		// @formatter:off
		mockMvc.perform(get("/posts/" + post.getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"id\":" + post.getId() + ",\"title\":\"jpa\",\"created\":null}"));
		// @formatter:on
	}
	
	@Test
	public void getPosts() throws Exception {
		createPosts(100);

		// @formatter:off
		mockMvc.perform(get("/posts")
				.param("page", "3")
				.param("size", "10")
				.param("sort", "created,desc")
				.param("sort", "title"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].title", is("jpa")));
		// @formatter:on
	}

	@Test
	public void getPostsByHateoas() throws Exception {
		createPosts(100);
		
		// @formatter:off
		mockMvc.perform(get("/posts/hateoas")
				.param("page", "3")
				.param("size", "10")
				.param("sort", "created,desc")
				.param("sort", "title"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links").exists());
		// @formatter:on
	}

	private void createPosts(int count) {
		for (int i = 0; i < count; i++) {
			Post post = new Post();
			post.setTitle("jpa");
			postRepository.save(post);
		}
	}
}
