package com.hsenid.releasetracker.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hsenid.releasetracker.dto.UserResponse;
import com.hsenid.releasetracker.exception.AlreadyExistsException;
import com.hsenid.releasetracker.exception.UserNotFoundException;
import com.hsenid.releasetracker.model.User;
import com.hsenid.releasetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User("1", "john_doe", "password", "John Doe");
        userResponse = new UserResponse("1", "john_doe", "John Doe");
    }

    @Test
    void testFetchAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserResponse> users = userService.fetchAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(userResponse, users.get(0));
    }

    @Test
    void testFetchAllUsers_NoUsersFound() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.fetchAllUsers();
        });

        assertEquals("No users found", exception.getMessage());
    }

    @Test
    void testAddUserdetails_UserAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.addUserdetails(user);
        });

        assertEquals("User already exists with username: john_doe", exception.getMessage());
    }

    @Test
    void testAddUserdetails_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User addedUser = userService.addUserdetails(user);

        assertNotNull(addedUser);
        assertEquals(user, addedUser);
    }

    @Test
    void testFetchUsingId_Success() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponse fetchedUser = userService.fetchUsingId("1");

        assertNotNull(fetchedUser);
        assertEquals(userResponse, fetchedUser);
    }

    @Test
    void testFetchUsingId_UserNotFound() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.fetchUsingId("2");
        });

        assertEquals("There is no user with id: 2", exception.getMessage());
    }

    @Test
    void testDeleteById_Success() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById("1");

        userService.deleteById("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteById_UserNotFound() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteById("2");
        });

        assertEquals("There is no user with id: 2", exception.getMessage());
    }
}
