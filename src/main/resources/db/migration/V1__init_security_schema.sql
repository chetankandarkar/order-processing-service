CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    product VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- =========================
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- =========================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT uk_user_role UNIQUE (user_id, role_id)
);

-- =========================
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- =========================
-- password = admin123 / user123 (BCrypt)
INSERT INTO users (username, password, enabled)
VALUES (
  'admin',
  '$2a$10$sq/GJ62V1LKPPJSaBzh1zebfDCDnc8v9JBp/ZNWD/HyabxEnWixg6',
  true
);

INSERT INTO users (username, password, enabled)
VALUES (
  'user',
  '$2a$10$YAlj0Fcm4TIWlyeTg5PJAOoi3rMJO8GKIaNIRgYnnACmBBOnuZrDi',
  true
);

-- =========================
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'user' AND r.name = 'ROLE_USER';
