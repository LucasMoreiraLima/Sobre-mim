CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

CREATE TABLE permissao (
	codigo BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);

INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Lucas', 'premen@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Sub Lucas', 'premen@gmail.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_CADASTRAR_CLIENTE');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_PESQUISAR_CLIENTE');
INSERT INTO permissao (codigo, descricao) values (3, 'ROLE_DELETAR_CLIENTE');
INSERT INTO permissao (codigo, descricao) values (4, 'ROLE_ALTERAR_CLIENTE');


-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);

-- maria
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);