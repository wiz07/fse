package com.tweetApp.FSE.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweetApp.DTO.LoginRequestDTO;
import com.tweetApp.DTO.LoginResponseDTO;
import com.tweetApp.DTO.RegisterDTO;
import com.tweetApp.DTO.ResetPasswordRequestDTO;
import com.tweetApp.DTO.ResetPasswordResponseDTO;
import com.tweetApp.DTO.UserDTO;
import com.tweetApp.DTO.UserTweetsDTO;
import com.tweetApp.FSE.Service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "TwittersUserManagement" })
@RestController
@RequestMapping(value = "/api/v1.0/tweets")
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "User Register", response = RegisterDTO.class, notes = "This API used to Register user.It receives emailId,loginId,firstName, lastName,"
			+ "and return registered user details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "*")
	@PostMapping("/register")
	public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
		registerDTO = userService.register(registerDTO);
		return ResponseEntity.ok().body(registerDTO);
	}

	@ApiOperation(value = "User Login", response = LoginResponseDTO.class, notes = "This API used to Login the user.It receives emailId,password"
			+ "and return logged in user details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "*")
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> signUp(@RequestBody LoginRequestDTO loginRequest) {
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO = userService.signUp(loginRequest);

		return ResponseEntity.ok().body(loginResponseDTO);
	}

	@ApiOperation(value = "Reset Password", response = ResetPasswordResponseDTO.class, notes = "This API used to reset the password of loggedin user.It receives new Password and old Password"
			+ "and reset password details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "*")
	@PutMapping("/reset")
	public ResponseEntity<ResetPasswordResponseDTO> resetpassword(@RequestBody ResetPasswordRequestDTO resetRequest) {
		ResetPasswordResponseDTO resetPasswordResponse = new ResetPasswordResponseDTO();
		resetPasswordResponse = userService.resetpassword(resetRequest);

		return ResponseEntity.ok().body(resetPasswordResponse);
	}

	@ApiOperation(value = "Get all user", response = UserDTO.class, notes = "This API used to get all users account."
			+ "and return all loggedin users account details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "*")
	@GetMapping("/getallUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	@ApiOperation(value = "Get user tweet details", response = UserTweetsDTO.class, notes = "This API used to get loggedin user tweet details."
			+ "and return all loggedin users tweet details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "*")
	@GetMapping("/UsersTweet")
	public ResponseEntity<List<UserTweetsDTO>> getUsersTweet(@RequestParam("email") String email) {
		return ResponseEntity.ok().body(userService.getUsersTweet(email));
	}
}
