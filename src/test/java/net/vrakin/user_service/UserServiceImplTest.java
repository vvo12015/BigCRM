package net.vrakin.user_service;

import net.vrakin.user_service.entity.User;
import net.vrakin.user_service.repository.UserRepository;
import net.vrakin.user_service.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        users.add(new User(1L, "test@test.com", "testPassword", "testName",
                "+380112223344", true, LocalDate.now(),
                LocalDate.of(1987, 2, 5), "test_decryption"));
        users.add(new User(2L, "test2@test.com", "test2Password", "test2Name",
                "+380112223342", false, LocalDate.now(),
                LocalDate.of(1987, 3, 5), "test2_decryption"));
        users.add(new User(3L, "test3@test.com", "test3Password", "test3Name",
                "+380112223343", false, LocalDate.now(),
                LocalDate.of(1987, 4, 5), "test3_decryption"));
    }

    @Test
    void testGetUser() {

        Long userId = 1L;

        var testUser = users.getFirst();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(userId);

        Assertions.assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSaveUser() {

        var testUser = users.getFirst();
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.save(testUser);

        Assertions.assertNotNull(result);
        assertEquals(testUser, result);
        assertEquals(testUser.getActiveStatus(), result.getActiveStatus());
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getBirthDate(), result.getBirthDate());
        assertEquals(testUser.getPhone(), result.getPhone());
        assertEquals(testUser.getDescription(), result.getDescription());
        assertEquals(testUser.getStartDate(), result.getStartDate());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testFindAll() {

        when(userRepository.findAll()).thenReturn(users);

        List<User> testUsers = userService.findAll();

        assertEquals(3, testUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testByName() {

        var name = "testName";
        var testUser = users.getFirst();
        when(userRepository.findByName(name)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByName(name);

        Assertions.assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findByName(name);
    }

    @Test
    void testByEmail() {

        var email = "test@test.com";
        var testUser = users.getFirst();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByEmail(email);

        Assertions.assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testByPhone() {

        var phone = "+380112223344";
        var testUser = users.getFirst();
        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByPhone(phone);

        Assertions.assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findByPhone(phone);
    }

    @Test
    void testByNameLike() {

        var namePattern = "testName%";
        when(userRepository.findByNameLike(namePattern)).thenReturn(users);

        List<User> testUsers = userService.findByNameLike(namePattern);

        assertEquals(3, testUsers.size());
        verify(userRepository, times(1)).findByNameLike(namePattern);
    }
}