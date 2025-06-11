CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Roles
DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Users
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Indexes
DROP INDEX IF EXISTS idx_users_email;
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- User Roles
DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Accounts
DROP TABLE IF EXISTS accounts CASCADE;
CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    account_status VARCHAR(50) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    account_number VARCHAR(30) UNIQUE NOT NULL,
    balance NUMERIC(15, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP INDEX IF EXISTS idx_accounts_user_id;
CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts(user_id);
DROP INDEX IF EXISTS idx_accounts_account_number;
CREATE INDEX IF NOT EXISTS idx_accounts_account_number ON accounts(account_number);

-- Transfers
DROP TABLE IF EXISTS transfers CASCADE;
CREATE TABLE IF NOT EXISTS transfers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    transfer_status VARCHAR(50) NOT NULL,
    transfer_number VARCHAR(30) UNIQUE NOT NULL,
    source_account_id UUID NOT NULL,
    target_account_id UUID NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (source_account_id) REFERENCES accounts(id),
    FOREIGN KEY (target_account_id) REFERENCES accounts(id)
);

DROP INDEX IF EXISTS idx_transfers_account_id;
CREATE INDEX idx_transfers_source_account_id ON transfers(source_account_id);

DROP INDEX IF EXISTS idx_transfers_target_account_id;
CREATE INDEX idx_transfers_target_account_id ON transfers(target_account_id);

-- Policies
DROP TABLE IF EXISTS policies CASCADE;
CREATE TABLE IF NOT EXISTS policies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    policy_status VARCHAR(50) NOT NULL,
    policy_type VARCHAR(50) NOT NULL,
    policy_number VARCHAR(30) UNIQUE NOT NULL,
    coverage_amount NUMERIC(15, 2),
    premium NUMERIC(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP INDEX IF EXISTS idx_policies_user_id;
CREATE INDEX IF NOT EXISTS idx_policies_user_id ON policies(user_id);
DROP INDEX IF EXISTS idx_policies_policy_number;
CREATE INDEX IF NOT EXISTS idx_policies_policy_number ON policies(policy_number);

-- Notifications
DROP TABLE IF EXISTS notifications CASCADE;
CREATE TABLE IF NOT EXISTS notifications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    title VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP INDEX IF EXISTS idx_notifications_user_id;
CREATE INDEX IF NOT EXISTS idx_notifications_user_id ON notifications(user_id);