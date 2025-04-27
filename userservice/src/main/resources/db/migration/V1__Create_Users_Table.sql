-- Create users table
CREATE TABLE users (
                               id UUID PRIMARY KEY,
                               email VARCHAR(255) NOT NULL UNIQUE,
                               password VARCHAR(255) NOT NULL,
                               first_name VARCHAR(255),
                               last_name VARCHAR(255),
                               role VARCHAR(50) NOT NULL DEFAULT 'USER',
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add index for email lookups
CREATE INDEX idx_users_email ON users(email);

-- Add comment to the table
COMMENT ON TABLE users IS 'Stores user account information and profile details';