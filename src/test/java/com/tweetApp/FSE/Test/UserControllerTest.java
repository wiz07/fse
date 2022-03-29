package com.tweetApp.FSE.Test;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetApp.DTO.LoginResponseDTO;
import com.tweetApp.DTO.RegisterDTO;
import com.tweetApp.DTO.ResetPasswordResponseDTO;
import com.tweetApp.DTO.UserDTO;
import com.tweetApp.DTO.UserTweetsDTO;
import com.tweetApp.FSE.Controller.UserController;
import com.tweetApp.FSE.Service.UserService;
import com.tweetApp.FSE.ServiceImpl.UserServiceImpl;



@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userServiceMock = new UserServiceImpl();

	@InjectMocks
	private UserController userController;

	RegisterDTO registerDTO = new RegisterDTO();

	String requestJson = "{\"email\":\"fse@gmail.com\",\"firstName\":\"admin\",\"lastName\":\"admin\",\"password\":\"admin\",\"gender\":\"male\"}";

	String responseJson = "{\"id\":1,\"firstName\":\"admin\",\"lastName\":\"admin\",\"gender\":\"male\",\"email\":\"fse@gmail.com\",\"password\":\"admin\",\"errorMessage\":null,\"sucessMessage\":null}";
	String loginResponseJson="{\"email\":\"fse@gmail.com\",\"status\":true,\"errorMessage\":null}";
	
	String resetPasswordResJson="{\"errorMessage\":null,\"successMessage\":\"Password Updated Successfully\"}";
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			this.registerDTO = objectMapper.readValue(requestJson, RegisterDTO.class);

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void registerPositiveTest() throws Exception {

		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setId(1);
		registerDTO.setEmail("fse@gmail.com");
		registerDTO.setFirstName("admin");
		registerDTO.setLastName("admin");
		registerDTO.setPassword("admin");
		registerDTO.setGender("male");

		String requestJson = "{\"id\":\"1\",\"email\":\"fse@gmail.com\",\"firstName\":\"admin\",\"lastName\":\"admin\",\"password\":\"admin\",\"gender\":\"male\"}";

		
		
		String url = "/api/v1.0/tweets/register";

		when(userServiceMock.register(Mockito.any())).thenReturn(registerDTO);
		MockHttpServletResponse response = mockMvc
				.perform(post(url).accept(MediaType.APPLICATION_JSON).content(requestJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(responseJson, response.getContentAsString());

	}

	@Test
	public void registerNegativeTest() throws Exception {

		String url = "/api/v1.0/tweets/register";

		when(userServiceMock.register(Mockito.any())).thenReturn(null);
		MockHttpServletResponse response = mockMvc
				.perform(post(url).accept(MediaType.APPLICATION_JSON).content(requestJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals("", response.getContentAsString());

	}
	
	@Test
	public void LoginPositiveTest() throws Exception {
		String url = "/api/v1.0/tweets/login";
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setEmail("fse@gmail.com");
		loginResponseDTO.setStatus(true);
		String loginReqJson="{\"email\":\"fse@gmail.com\",\"password\":\"admin\"}";
		when(userServiceMock.signUp(Mockito.any())).thenReturn(loginResponseDTO);
		MockHttpServletResponse response = mockMvc
				.perform(post(url).accept(MediaType.APPLICATION_JSON).content(loginReqJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(loginResponseJson, response.getContentAsString());
		
	}
	
	@Test
	public void ResetPasswordPositiveFlow() throws Exception{
		String url = "/api/v1.0/tweets/reset";
		ResetPasswordResponseDTO resetPasswordResponseDTO=new ResetPasswordResponseDTO();
		resetPasswordResponseDTO.setSuccessMessage("Password Updated Successfully");
		
		String resetPasswordReqJson="{\"emailId\":\"fse@gmail.com\",\"newpassword\":\"admin\",\"oldpassword\":\"admin123\"}";
		
		when(userServiceMock.resetpassword(Mockito.any())).thenReturn(resetPasswordResponseDTO);
		MockHttpServletResponse response = mockMvc
				.perform(put(url).accept(MediaType.APPLICATION_JSON).content(resetPasswordReqJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(resetPasswordResJson, response.getContentAsString());
		
	}
	
	@Test
	public void getAllUsersPositiveFlow() throws Exception{
		String url="/api/v1.0/tweets/getallUsers";
		List<UserDTO> userDTOList= new ArrayList<>();
		UserDTO userDTO= new UserDTO();
		userDTO.setEmail("fse@gmail.com");
		userDTO.setFirstName("fse");
		userDTO.setGender("male");
		userDTO.setId(1);
		userDTOList.add(userDTO);
		
		String expectedJson="[{\"id\":1,\"email\":\"fse@gmail.com\",\"firstName\":\"fse\",\"gender\":\"male\"}]";
		
		when(userServiceMock.getAllUsers()).thenReturn(userDTOList);
		MockHttpServletResponse response = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertEquals(expectedJson, response.getContentAsString());
	}
	
	@Test
	public void getUsersTweetsPositiveFlow() throws Exception{
		String url="/api/v1.0/tweets/UsersTweet/?email=fse@gmail.com";
		List<UserTweetsDTO> userTweetsDTOList= new ArrayList<>();
		UserTweetsDTO userTweetsDTO= new UserTweetsDTO();
		userTweetsDTO.setTweetId(1);
		userTweetsDTO.setTweetDesc("desc");
		userTweetsDTOList.add(userTweetsDTO);
		
		String expectedJson="[{\"tweetId\":1,\"tweetDesc\":\"desc\",\"replyDTOList\":null,\"date\":null}]";
		when(userServiceMock.getUsersTweet(Mockito.any())).thenReturn(userTweetsDTOList);
		MockHttpServletResponse response = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertEquals(expectedJson, response.getContentAsString());
	}
	
	

}
