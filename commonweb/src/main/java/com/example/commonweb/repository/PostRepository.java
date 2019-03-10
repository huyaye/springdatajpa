package com.example.commonweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commonweb.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
