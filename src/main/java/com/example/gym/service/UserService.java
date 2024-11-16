package com.example.gym.service;

import com.example.gym.dto.UserCreateDTO;
import com.example.gym.dto.UserDTO;
import com.example.gym.exception.EmailAlreadyExistsException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.User;
import com.example.gym.repository.MembershipRepo;
import com.example.gym.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final MembershipRepo membershipRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       MembershipRepo membershipRepo,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.membershipRepo = membershipRepo;
        this.modelMapper = modelMapper;
    }


    public UserDTO createUser(UserCreateDTO userCreateDTO){

        if(userRepository.existsByEmail(userCreateDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = modelMapper.map(userCreateDTO , User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser , UserDTO.class);
    }

    public UserDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO updateUser(Long id , UserDTO userDTO){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!existingUser.getEmail().equals(userDTO.getEmail()) &&
                userRepository.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already taken");
        }

        modelMapper.map(userDTO , existingUser);
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser , UserDTO.class);
    }
}
