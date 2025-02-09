package com.example.gym.Controller;

import com.example.gym.controller.UserController;
import com.example.gym.exception.ImageSizeException;
import com.example.gym.model.User;
import com.example.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_returnsUserWhenValid() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[1024]);
        User user = new User();
        when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.addUser("John", "john@example.com", "1234567890", true, ZonedDateTime.now(), image);

        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    void addUser_throwsExceptionWhenImageTooLarge() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[3 * 1024 * 1024]);

        assertThrows(ImageSizeException.class, () -> {
            userController.addUser("John", "john@example.com", "1234567890", true, ZonedDateTime.now(), image);
        });
    }

    @Test
    void addUser_returnsBadRequestWhenImageNotValid() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.txt", MediaType.TEXT_PLAIN_VALUE, new byte[1024]);

        ResponseEntity<User> response = userController.addUser("John", "john@example.com", "1234567890", true, ZonedDateTime.now(), image);

        assertEquals(ResponseEntity.badRequest().body(null), response);
    }

    @Test
    void getSingleUser_returnsUserWhenFound() {
        User user = new User();
        when(userService.getSingleUser(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getSingleUser(1L);

        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    void getSingleUser_returnsNotFoundWhenUserNotFound() {
        when(userService.getSingleUser(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getSingleUser(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void updateUserImage_returnsUserWhenValid() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[1024]);
        User user = new User();
        when(userService.updateUserImage(1L, image.getBytes())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.updateUserImage(1L, image);

        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    void updateUserImage_throwsExceptionWhenImageTooLarge() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[3 * 1024 * 1024]);

        assertThrows(ImageSizeException.class, () -> {
            userController.updateUserImage(1L, image);
        });
    }

    @Test
    void updateUserImage_returnsBadRequestWhenImageNotValid() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image", "image.txt", MediaType.TEXT_PLAIN_VALUE, new byte[1024]);

        ResponseEntity<User> response = userController.updateUserImage(1L, image);

        assertEquals(ResponseEntity.badRequest().body(null), response);
    }

    @Test
    void deactivateUser_returnsNoContentWhenUserDeactivated() {
        when(userService.deactivateUser(1L)).thenReturn(true);

        ResponseEntity<User> response = userController.deactivateUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}