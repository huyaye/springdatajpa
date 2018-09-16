package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		testSaveAccount();
//		testCascadeSave();
//		testFetch();
//		testQuery();
	}

	private void testSaveAccount() {
		Account account = new Account();
		account.setUsername("jungwook");
		account.setPassword("password");

		Study study = new Study();
		study.setName("Spring Data JPA");

		account.addStudy(study);

//		entityManager.persist(account);
		Session session = entityManager.unwrap(Session.class);

		session.save(account);
		session.save(study);
	}

	private void testCascadeSave() {
		Post post = new Post();
		post.setTitle("Spring jpa study");

		Comment comment1 = new Comment();
		comment1.setComment("first comment");
		post.addComment(comment1);

		Comment comment2 = new Comment();
		comment2.setComment("second comment");
		post.addComment(comment2);

		Session session = entityManager.unwrap(Session.class);
		session.save(post);

		/*
		 * Cascade option do this automatically.
		 */
//		session.save(comment1);
//		session.save(comment2);
	}

	// Check fetch option of Entity class
	private void testFetch() {
		Session session = entityManager.unwrap(Session.class);
		Post post = session.get(Post.class, 3l);
		System.out.println("======================================");
		System.out.println(post.getTitle());
		
		post.getComments().forEach(c -> {
			System.out.println("-----------------------------------");
			System.out.println(c.getComment());
		});
	}
	
	private void testQuery() {
		TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post As p", Post.class);
		List<Post> posts = query.getResultList();
		posts.forEach(System.out::println);
	}
}
