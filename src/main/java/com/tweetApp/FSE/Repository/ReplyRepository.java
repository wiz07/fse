package com.tweetApp.FSE.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetApp.Model.Reply;

public interface ReplyRepository extends MongoRepository<Reply,String> {
	
	List<Reply> findByTweetId(int tweetId);
	String deleteByTweetId(int tweetId);
}
