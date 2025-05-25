-- Roles
INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER'), ('ROLE_ADMIN');

-- Test users
INSERT INTO users (first_name, last_name, email, password) VALUES
('test', 'test', 'test@test.com', '$2a$12$5REZR0NrwQ.qkXixzAhBVeH.vGX2lKQePM7gli5./kuctzB7Z4SBK'), -- password=test
('tost', 'tost', 'tost@tost.com', '$2a$12$gq6tQMSlk/4e0fwWxKBpa.ZEgJ4xRHtgPBbq6TLkmaeaFMPuZLbHu'), -- password=tost
('admin', 'admin', 'admin@admin.com', '$2a$12$SAIU47j4EGw01ClQHIjkEOqt7D8NasFh1MkVOArDUcN5Md4xF.c8G'); -- password=admin

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE (u.email = 'test@test.com' AND r.name = 'ROLE_CUSTOMER')
   OR (u.email = 'tost@tost.com' AND r.name = 'ROLE_CUSTOMER')
   OR (u.email = 'admin@admin.com' AND r.name = 'ROLE_ADMIN');