package com.example.commonweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.commonweb.entity.Comment;
import com.example.commonweb.entity.CommentOnly;
import com.example.commonweb.entity.CommentSummary;
import com.example.commonweb.entity.Post;
import com.example.commonweb.repository.CommentRepository;
import com.example.commonweb.repository.PostRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	@Test
	public void getCommentByEntityGraph() {
		commentRepository.getById(1l);

		System.out.println("\n=========== Compare generated query =============\n");

		commentRepository.findById(1l);
	}

	@Test
	public void getCommentByProjection() {
		Post post = new Post();
		post.setTitle("JPA");
		Post savedPost = postRepository.save(post);

		Comment comment = new Comment();
		comment.setComment("Spring data jpa");
		comment.setPost(savedPost);
		comment.setUp(10);
		comment.setDown(1);
		commentRepository.save(comment);

		commentRepository.findByPost_Id(1l, CommentSummary.class).forEach(c -> {
			System.out.println("=================================================");
			System.out.println(c.getVotes());
		});

		commentRepository.findByPost_Id(1l, CommentOnly.class).forEach(c -> {
			System.out.println("=================================================");
			System.out.println(c.getComment());
		});

	}

}
