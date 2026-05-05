CREATE DATABASE estoque_fluxo_caixa_db;

USE estoque_fluxo_caixa_db;

CREATE TABLE categorias (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigoDeBarra VARCHAR(13) UNIQUE,
    nome VARCHAR(40),
    preco DOUBLE,
    quantidade INT,
    version INT DEFAULT 0,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE vendas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    dataHora DATETIME,
    total DOUBLE
);

CREATE TABLE itemVenda (
    venda_id INT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT,
    PRIMARY KEY (venda_id, produto_id),
    FOREIGN KEY (venda_id) REFERENCES vendas(codigo),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
