package com.example.commonweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.commonweb.entity.Post;
import com.example.commonweb.repository.PostRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void save() {
		Post post = new Post();
		post.setTitle("jpa");
		Post savedPost = postRepository.save(post); // persist

		assertThat(entityManager.contains(savedPost)).isTrue();
		assertThat(entityManager.contains(post)).isTrue();
		assertThat(savedPost == post).isTrue();

		Post postUpdate = new Post();
		postUpdate.setId(1l);
		postUpdate.setTitle("hibernate");
		Post updatedPost = postRepository.save(postUpdate); // merge
		updatedPost.setTitle("This will be applied instead of hibernate."); // Use return value!!!

		assertThat(entityManager.contains(updatedPost)).isTrue();
		assertThat(entityManager.contains(postUpdate)).isFalse();
		assertThat(updatedPost == postUpdate).isFalse();

		List<Post> all = postRepository.findAll();
		assertThat(all.size()).isEqualTo(1);
	}

	@Test
	public void findByTitleStartsWith() {
		Post post = new Post();
		post.setTitle("Spring Data Jpa");
		postRepository.save(post); // persist

		List<Post> all = postRepository.findByTitleStartsWith("Spring");
		assertThat(all.size()).isEqualTo(1);
	}

	@Test
	public void findByTitle() {
		Post post = new Post();
		post.setTitle("Spring");
		postRepository.save(post); // persist

		List<Post> results = postRepository.findByTitle("Spring", Sort.by("title"));
		assertThat(results.size()).isEqualTo(1);

		results = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));
		assertThat(results.size()).isEqualTo(1);
	}
	
	@Test
	public void updateTitle() {
		Post post = new Post();
		post.setTitle("Spring");
		postRepository.save(post); // persist
		
		int update = postRepository.updateTitle("Hibernate", post.getId());
		assertThat(update).isEqualTo(1);
		
		/*
		 * Without @Modifying(clearAutomatically = true, flushAutomatically = true)
		 * This test will be failed. because SELECT query does not occur without above code.
		 */
		Optional<Post> byId = postRepository.findById(post.getId());
		assertThat(byId.get().getTitle()).isEqualTo("Hibernate");
	}
}
