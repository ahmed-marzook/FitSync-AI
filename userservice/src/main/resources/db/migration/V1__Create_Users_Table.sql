-- Create users table
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    user_guid     UUID         NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    role          VARCHAR(50)  NOT NULL DEFAULT 'USER',
    last_login_at TIMESTAMP NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add index for email lookups
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_guid ON users (user_guid);

-- Add comment to the table
COMMENT
ON TABLE users IS 'Stores user account information and profile details';