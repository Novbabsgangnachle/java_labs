INSERT INTO owners(id, first_name, last_name, email, password) VALUES
    (1, 'admin', 'admin', 'admin@email.com', '$2a$12$TptiyDhPeiMQpQDnl/YBAeu1SLDvzYSpfIGGpNc57.6ii5ZZhoPYq');
INSERT INTO owner_roles (owner_id, roles) VALUES
    (1, 'ROLE_ADMIN');