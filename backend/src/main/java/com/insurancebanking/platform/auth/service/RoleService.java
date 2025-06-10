package com.insurancebanking.platform.auth.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.insurancebanking.platform.auth.model.Role;
import com.insurancebanking.platform.auth.repository.RoleRepository;

public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
