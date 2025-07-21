-- Permissions
CREATE TABLE permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Role-Permission mapping
CREATE TABLE roles_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

-- Users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    password_salt VARCHAR(255) NOT NULL
);

-- User-Role mapping
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Seed permissions
INSERT INTO permissions (name) VALUES ('READ_PRIVILEGES');
INSERT INTO permissions (name) VALUES ('WRITE_PRIVILEGES');

-- Seed roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Assign permissions to roles
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1); -- ROLE_USER: READ_PRIVILEGES
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 1); -- ROLE_ADMIN: READ_PRIVILEGES
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 2); -- ROLE_ADMIN: WRITE_PRIVILEGES

-- Seed users
INSERT INTO users (username, password_hash, password_salt) VALUES (
    'user',
    '5d5e792708bfa15f0ab42e817b4e69379777d2722e0529dfb031c0b847db137d',
    'somesalt'
);
INSERT INTO users (username, password_hash, password_salt) VALUES (
    'admin',
    '6b31d670e59d428c2f07427919e44e2fe0f59f5aadef947a1cb776a2929f3295',
    'somesalt'
);

-- Assign roles to users
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- user: ROLE_USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2); -- admin: ROLE_ADMIN
