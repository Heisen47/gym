package com.example.gym;

import com.example.gym.exception.DuplicateEmailException;
import com.example.gym.exception.DuplicatePhoneNumberException;
import com.example.gym.model.User;
import com.example.gym.repository.UserRepository;
import com.example.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setId(1L);
		testUser.setName("Test User");
		testUser.setEmail("test@example.com");
		testUser.setPhoneNumber("1234567890");
		testUser.setMembership(true);
		testUser.setActive(true);
	}

	@Test
	void getAllUsers_ShouldReturnAllUsers() {
		// Arrange
		List<User> expectedUsers = Arrays.asList(testUser);
		when(userRepository.findAll()).thenReturn(expectedUsers);

		// Act
		List<User> actualUsers = userService.getAllUsers();

		// Assert
		assertThat(actualUsers).isEqualTo(expectedUsers);
		verify(userRepository).findAll();
	}

	@Test
	void addUser_WithValidUser_ShouldSaveUser() {
		// Arrange
		when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(testUser);

		// Act
		User savedUser = userService.addUser(testUser);

		// Assert
		assertThat(savedUser).isEqualTo(testUser);
		verify(userRepository).save(testUser);
	}

	@Test
	void addUser_WithDuplicatePhone_ShouldThrowException() {
		// Arrange
		when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(testUser));

		// Act & Assert
		assertThrows(DuplicatePhoneNumberException.class, () -> userService.addUser(testUser));
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void addUser_WithDuplicateEmail_ShouldThrowException() {
		// Arrange
		when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

		// Act & Assert
		assertThrows(DuplicateEmailException.class, () -> userService.addUser(testUser));
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void getSingleUser_WithExistingId_ShouldReturnUser() {
		// Arrange
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

		// Act
		Optional<User> foundUser = userService.getSingleUser(1L);

		// Assert
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get()).isEqualTo(testUser);
	}

	@Test
	void getSingleUser_WithNonExistingId_ShouldReturnEmpty() {
		// Arrange
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// Act
		Optional<User> foundUser = userService.getSingleUser(1L);

		// Assert
		assertThat(foundUser).isEmpty();
	}

	@Test
	void updateUser_WithExistingUser_ShouldUpdateUser() {
		// Arrange
		User updatedUser = new User();
		updatedUser.setName("Updated Name");
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		// Act
		Optional<User> result = userService.updateUser(1L, updatedUser);

		// Assert
		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Updated Name");
	}

	@Test
	void updateUser_WithNonExistingUser_ShouldReturnEmpty() {
		// Arrange
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// Act
		Optional<User> result = userService.updateUser(1L, testUser);

		// Assert
		assertThat(result).isEmpty();
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void deactivateUser_WithExistingUser_ShouldDeactivateUser() {
		// Arrange
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
		when(userRepository.save(any(User.class))).thenReturn(testUser);

		// Act
		boolean result = userService.deactivateUser(1L);

		// Assert
		assertThat(result).isTrue();
		assertThat(testUser.getActive()).isFalse();
	}

	@Test
	void deactivateUser_WithNonExistingUser_ShouldReturnFalse() {
		// Arrange
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// Act
		boolean result = userService.deactivateUser(1L);

		// Assert
		assertThat(result).isFalse();
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void createAdmin_ShouldCreateUserWithAdminRole() {
		// Arrange
		when(userRepository.save(any(User.class))).thenReturn(testUser);

		// Act
		User admin = userService.createAdmin(testUser);

		// Assert
		assertThat(admin).isNotNull();
		assertThat(admin.getAuthorities())
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
	}
}