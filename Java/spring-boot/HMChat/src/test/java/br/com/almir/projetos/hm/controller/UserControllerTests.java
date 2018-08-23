package br.com.almir.projetos.hm.controller;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.almir.projetos.hm.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	 
	@Autowired
    private UserController controller;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
    
    @Test
    public void createUserTest() throws Exception {
    	User expectedUser = new User();
    	expectedUser.setName("a");
    	expectedUser.setPassword("123");
    	expectedUser.setUsername("a");    	
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedUser)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
    	        
    }
    
    @Test
    public void createWrongUserTest() throws Exception {
    	User expectedUser = new User();
    	expectedUser.setName(null);
    	expectedUser.setPassword("123");
    	expectedUser.setUsername("a");    	
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedUser)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    public void createDuplicatedUserTest() throws Exception {
    	User expectedUser = new User();
    	expectedUser.setName("a");
    	expectedUser.setPassword("123");
    	expectedUser.setUsername("a");    	
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedUser)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
    	
    	expectedUser = new User();
    	expectedUser.setName("a");
    	expectedUser.setPassword("123");
    	expectedUser.setUsername("a");
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedUser)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    

	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "USER");
	}

}
