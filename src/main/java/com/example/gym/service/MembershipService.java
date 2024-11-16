package com.example.gym.service;

import com.example.gym.dto.MembershipDTO;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.Membership;
import com.example.gym.model.User;
import com.example.gym.repository.MembershipRepo;
import com.example.gym.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MembershipService {
    private final MembershipRepo membershipRepo;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MembershipService(MembershipRepo membershipRepo, UserRepository userRepository, ModelMapper modelMapper) {
        this.membershipRepo = membershipRepo;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public MembershipDTO createMembership(Long userId , MembershipDTO membershipDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Membership membership = modelMapper.map(membershipDTO , Membership.class);
        membership.setUser(user);

        Membership savedMembership = membershipRepo.save(membership);
        return modelMapper.map(savedMembership , MembershipDTO.class);
    }

    public MembershipDTO updateMembership(Long userId , MembershipDTO membershipDTO){
        Membership membership = membershipRepo.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Membership not found"));

        membership.setPaymentDate(membershipDTO.getPaymentDate());
        membership.setDueDate(membershipDTO.getDueDate());

        Membership updatedMembership = membershipRepo.save(membership);
        return modelMapper.map(updatedMembership , MembershipDTO.class);
    }
}
