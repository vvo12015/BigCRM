package net.vrakin.user_service;

import net.vrakin.user_service.entity.User;
import net.vrakin.user_service.repository.UserRepository;
import net.vrakin.user_service.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUser() {
        Long userId = 1L;
        User mockUser = new User(userId, "test@test.com", "testPassword", "testName",
                "+380112223344", true, LocalDate.now(),
                LocalDate.of(1987, 02, 05), "test_decryption");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userService.findById(userId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockUser, result.get());
        Mockito.verify(userRepository, times(1)).findById(userId);
    }
}
