package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	@Test
	public void queryTest01() {
		// Given
		createComment(100, "spring data jpa");
		
		// When
		List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10);
		
		// Then
		assertThat(comments.size()).isEqualTo(1);
	}
	
	@Test
	public void queryTest02() {
		// Given
		createComment(100, "spring data jpa");
		createComment(55, "HIBERNATE SPRING");
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Direction.ASC, "LikeCount"));
		
		// When
		Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);
		
		// Then
		assertThat(comments.getNumberOfElements()).isEqualTo(2);
		assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 55);
	}
	
	private void createComment(int likeCount, String comment) {
		Comment newComment = new Comment();
		newComment.setComment(comment);
		newComment.setLikeCount(likeCount);
		commentRepository.save(newComment);
	}
}
