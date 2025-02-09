package net.vrakin.user_service.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.vrakin.user_service.dto.UserRequestDTO;
import net.vrakin.user_service.dto.UserResponseDTO;
import net.vrakin.user_service.entity.User;
import net.vrakin.user_service.mapper.UserMapper;
import net.vrakin.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDTO> getUserByName(@PathVariable String name) {
        return userService.findByName(name)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return userService.findByName(email)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User savedUser = userService.createUser(userRequestDTO);
        UserResponseDTO responseDTO = userMapper.toResponseDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userMapper.toEntity(userRequestDTO);
        user.setId(id);

        UserResponseDTO responseDTO = userMapper.toResponseDTO(userService.save(user));

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
