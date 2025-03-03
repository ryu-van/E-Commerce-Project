package org.example.ecommerceproject.service;


import org.example.ecommerceproject.model.Role;
import org.example.ecommerceproject.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }
}
