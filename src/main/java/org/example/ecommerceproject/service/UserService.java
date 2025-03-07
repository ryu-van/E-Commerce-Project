package org.example.ecommerceproject.service;


import jakarta.validation.Valid;
import org.example.ecommerceproject.model.User;
import org.example.ecommerceproject.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsersPage(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().equals("")) {
            return userRepository.findUsersByRole(pageable);
        }
        return userRepository.findByFullnameOrEmailOrPhone(keyword,pageable);
    }

    public void createUser(@Valid User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void updateUser( @Valid User user) {
        userRepository.save(user);
    }

    public Page<User> getClientPages(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().equals("")) {
            return userRepository.findUsersByRoleClient(pageable);
        }
        return userRepository.findByFullnameOrEmailOrPhoneClient(keyword,pageable);
    }
}
