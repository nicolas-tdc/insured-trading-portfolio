package com.insuredtrading.portfolio.auth.exception;

/**
 * Exception thrown when a role with the specified name is not found in the system.
 *
 * @param roleName the name of the role that was not found
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleName) {
        super("Role with name " + roleName + " not found.");
    }
}
