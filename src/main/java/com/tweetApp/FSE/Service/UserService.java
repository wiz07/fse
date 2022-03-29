package com.tweetApp.FSE.Service;

import java.util.List;

import com.tweetApp.DTO.LoginRequestDTO;
import com.tweetApp.DTO.LoginResponseDTO;
import com.tweetApp.DTO.RegisterDTO;
import com.tweetApp.DTO.ResetPasswordRequestDTO;
import com.tweetApp.DTO.ResetPasswordResponseDTO;
import com.tweetApp.DTO.UserDTO;
import com.tweetApp.DTO.UserTweetsDTO;

public interface UserService {

	RegisterDTO register(RegisterDTO registerDTO);
	
	LoginResponseDTO signUp(LoginRequestDTO loginRequestDTO);
	
	ResetPasswordResponseDTO resetpassword(ResetPasswordRequestDTO resetRequest);

	List<UserDTO> getAllUsers();

	List<UserTweetsDTO> getUsersTweet(String email);

}
