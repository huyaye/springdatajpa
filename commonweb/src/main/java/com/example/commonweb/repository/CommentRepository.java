package com.example.commonweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.commonweb.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//	@EntityGraph(value = "Comment.post")
	@EntityGraph(attributePaths = "post")
	Optional<Comment> getById(Long id);

	@Transactional(readOnly = true)
	<T> List<T> findByPost_Id(Long id, Class<T> type);

}
