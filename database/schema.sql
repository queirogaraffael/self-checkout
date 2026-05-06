CREATE DATABASE estoque_fluxo_caixa_db;

USE estoque_fluxo_caixa_db;

CREATE TABLE categorias (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigoDeBarra VARCHAR(13) NOT NULL UNIQUE,
    nome VARCHAR(40) NOT NULL,
    preco DOUBLE NOT NULL,
    quantidade INT NOT NULL,
    version INT NOT NULL DEFAULT 0,
    categoria_id INT NOT NULL,

    FOREIGN KEY (categoria_id) REFERENCES categorias(id),

    CONSTRAINT chk_produto_preco CHECK (preco >= 0),
    CONSTRAINT chk_produto_quantidade CHECK (quantidade >= 0)
);

CREATE TABLE vendas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    dataHora DATETIME NOT NULL,
    total DOUBLE NOT NULL,

    CONSTRAINT chk_venda_total CHECK (total >= 0)
);

CREATE TABLE itemVenda (
    venda_id INT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,

    PRIMARY KEY (venda_id, produto_id),
    FOREIGN KEY (venda_id) REFERENCES vendas(codigo),
    FOREIGN KEY (produto_id) REFERENCES produtos(id),
    
    CONSTRAINT chk_itemvenda_quantidade CHECK (quantidade > 0)
);
