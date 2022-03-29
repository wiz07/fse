package com.tweetApp.FSE.Service;

import java.util.List;

import com.tweetApp.DTO.ReplyDTO;
import com.tweetApp.DTO.TweetRequestDTO;
import com.tweetApp.DTO.TweetResponseDTO;

public interface TweetService {
	
	String postTweet(TweetRequestDTO tweerRequest);
	
	List<TweetResponseDTO> getAllTweets();

	String postReply(ReplyDTO replyDTO);

	String deleteTweet(int tweetId);

}
