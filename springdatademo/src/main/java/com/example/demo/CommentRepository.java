package com.example.demo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends MyRepository<Comment, Long>{

	List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keword, Integer likeCount);
	
	Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);
}
