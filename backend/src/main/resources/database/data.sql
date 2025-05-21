INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER'), ('ROLE_ADMIN');

-- Mock User
INSERT INTO users (email, password, first_name, last_name)
VALUES (
    'customer@example.com',
    '$2a$10$Z/PLrjclzS69tt/lriihC.WNDF/q.7VFjL4aZQDYwPuQ7C2jCBZza',
    'John',
    'Doe'
);

-- Assign Role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'customer@example.com' AND r.name = 'ROLE_CUSTOMER';

-- Account
INSERT INTO accounts (user_id, account_number, type, balance)
SELECT u.id, 'ACC123456', 'CHECKING', 10000.00
FROM users u WHERE u.email = 'customer@example.com';

-- Policy
INSERT INTO policies (user_id, policy_number, type, coverage_amount, premium)
SELECT u.id, 'POL789012', 'LIFE', 500000.00, 150.00
FROM users u WHERE u.email = 'customer@example.com';

-- Notification
INSERT INTO notifications (user_id, title, message)
SELECT u.id, 'Welcome!', 'Your account has been successfully created.'
FROM users u WHERE u.email = 'customer@example.com';