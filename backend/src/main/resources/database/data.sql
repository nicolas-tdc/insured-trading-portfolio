-- Roles
INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER'), ('ROLE_ADMIN'), ('ROLE_ADVISOR');

-- Test users
INSERT INTO users (first_name, last_name, email, password) VALUES
('admin', 'platform', 'admin@platform.com', '$2y$10$/E/zWZ89sm3Gh93yOGWl5OA2zLqZXFQJeYNAgoGeLR8Pw7y1qXCtS'); -- password=admin
('advisor', 'platform', 'advisor@platform.com', '$2y$10$lEOZqq8aWkP7c3lXlbwr9OnNCXmeviqFNhQSSEzmmxEh1/aG9hatC'); -- password=advisor
('jane', 'doe', 'jane@customer.com', '$2y$10$mxxdM2BYtrP./jqt3Bus8OwS/ckNrIBapl/sks6Tmeil23ZAamQYK'), -- password=jane
('john', 'doe', 'john@customer.com', '$2y$10$dOauAs9DZCqoWr6ukgQORuNPETGu6nri8hK.Lfy1Y7GRKFCvVwgY2'), -- password=john

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE (u.email = 'admin@platform.com' AND r.name = 'ROLE_ADMIN')
   OR (u.email = 'advisor@platform.com' AND r.name = 'ROLE_ADVISOR')
   OR (u.email = 'john@customer.com' AND r.name = 'ROLE_CUSTOMER')
   OR (u.email = 'jane@customer.com' AND r.name = 'ROLE_CUSTOMER');