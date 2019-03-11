package com.example.commonweb.entity;

public interface CommentSummary {

	String getComment();

	int getUp();

	int getDown();

	default String getVotes() {
		return getUp() + ", " + getDown();
	}

}
