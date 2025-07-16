package com.insuredtrading.portfolio.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insuredtrading.portfolio.auth.model.Role;
import com.insuredtrading.portfolio.auth.repository.RoleRepository;
import com.insuredtrading.portfolio.auth.exception.RoleNotFoundException;

/**
 * Service responsible for role retrieval and management.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role
     * @return the matching role
     * @throws NotFoundException if role does not exist
     */
    public Role findByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
