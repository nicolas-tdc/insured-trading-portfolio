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

-- User Roles (Many-to-Many)
DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Currencies
DROP TABLE IF EXISTS currencies CASCADE;
CREATE TABLE IF NOT EXISTS currencies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(10) UNIQUE NOT NULL
);

-- Account types
DROP TABLE IF EXISTS account_types CASCADE;
CREATE TABLE IF NOT EXISTS account_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(30) UNIQUE NOT NULL
);

-- Accounts
DROP TABLE IF EXISTS accounts CASCADE;
CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    account_type_id UUID NOT NULL,
    currency_id UUID NOT NULL,
    account_number VARCHAR(30) UNIQUE NOT NULL,
    balance NUMERIC(15, 2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (account_type_id) REFERENCES account_types(id),
    FOREIGN KEY (currency_id) REFERENCES currencies(id)
);

DROP INDEX IF EXISTS idx_accounts_user_id;
CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts(user_id);
DROP INDEX IF EXISTS idx_accounts_account_number;
CREATE INDEX IF NOT EXISTS idx_accounts_account_number ON accounts(account_number);

-- Transfer types
DROP TABLE IF EXISTS transfer_types CASCADE;
CREATE TABLE IF NOT EXISTS transfer_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(30) UNIQUE NOT NULL
);

-- Transfers
DROP TABLE IF EXISTS transfers CASCADE;
CREATE TABLE IF NOT EXISTS transfers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    transfer_type_id UUID NOT NULL,
    source_account_id UUID NOT NULL,
    target_account_id UUID NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    type VARCHAR(30) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (transfer_type_id) REFERENCES transfer_types(id),
    FOREIGN KEY (source_account_id) REFERENCES accounts(id),
    FOREIGN KEY (target_account_id) REFERENCES accounts(id)
);

DROP INDEX IF EXISTS idx_transfers_account_id;
CREATE INDEX idx_transfers_source_account_id ON transfers(source_account_id);

DROP INDEX IF EXISTS idx_transfers_target_account_id;
CREATE INDEX idx_transfers_target_account_id ON transfers(target_account_id);

-- Policy types
DROP TABLE IF EXISTS policy_types CASCADE;
CREATE TABLE IF NOT EXISTS policy_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(30) UNIQUE NOT NULL
);

-- Policies
DROP TABLE IF EXISTS policies CASCADE;
CREATE TABLE IF NOT EXISTS policies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    policy_type_id UUID NOT NULL,
    policy_number VARCHAR(30) UNIQUE NOT NULL,
    type VARCHAR(30) NOT NULL,
    coverage_amount NUMERIC(15, 2),
    premium NUMERIC(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (policy_type_id) REFERENCES policy_types(id),
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