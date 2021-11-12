INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ( 'Hector', '12345', 1, 'Hector', 'Alvarado', 'hector@gmail.com' );
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ( 'Rosa', '12345', 1, 'Rosa', 'Campos', 'rosa@gmail.com' );

INSERT INTO roles (name) VALUES ( 'ROLE_USER' );
INSERT INTO roles (name) VALUES ( 'ROLE_ADMIN' );

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES ( 1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES ( 2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES ( 2, 1);