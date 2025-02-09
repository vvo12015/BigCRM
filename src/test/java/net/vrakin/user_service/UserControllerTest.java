package net.vrakin.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vrakin.user_service.config.TestSecurityConfig;
import net.vrakin.user_service.controller.UserController;
import net.vrakin.user_service.dto.UserRequestDTO;
import net.vrakin.user_service.dto.UserResponseDTO;
import net.vrakin.user_service.entity.User;
import net.vrakin.user_service.mapper.UserMapper;
import net.vrakin.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    private User testUser;
    private UserResponseDTO testUserResponse;
    private UserRequestDTO testUserRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");

        testUserResponse = new UserResponseDTO();
        testUserResponse.setId(testUser.getId());
        testUserResponse.setName(testUser.getName());
        testUserResponse.setEmail(testUser.getEmail());

        testUserRequest = new UserRequestDTO();
        testUserRequest.setName(testUser.getName());
        testUserRequest.setEmail(testUser.getEmail());
        testUserRequest.setPassword("password");

        Mockito.when(userService.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        Mockito.when(userService.findAll()).thenReturn(List.of(testUser));
        Mockito.when(userService.createUser(any(UserRequestDTO.class))).thenReturn(testUser);
        Mockito.when(userService.save(any(User.class))).thenReturn(testUser);
        Mockito.when(userMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

        Mockito.when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(testUser);
    }


    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testUser.getId()))
                .andExpect(jsonPath("$[0].name").value(testUser.getName()))
                .andExpect(jsonPath("$[0].email").value(testUser.getEmail()));

        Mockito.verify(userService).findAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserResponse.getId()))
                .andExpect(jsonPath("$.name").value(testUserResponse.getName()))
                .andExpect(jsonPath("$.email").value(testUserResponse.getEmail()));

        Mockito.verify(userService).findById(1L);
    }

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserResponse.getId()))
                .andExpect(jsonPath("$.name").value(testUserResponse.getName()))
                .andExpect(jsonPath("$.email").value(testUserResponse.getEmail()));

        Mockito.verify(userService).createUser(any(UserRequestDTO.class));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserResponse.getId()))
                .andExpect(jsonPath("$.name").value(testUserResponse.getName()))
                .andExpect(jsonPath("$.email").value(testUserResponse.getEmail()));

        Mockito.verify(userService).save(any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteById(1L);
    }
}
