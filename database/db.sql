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


INSERT INTO categorias (id, nome) VALUES 
    (1, 'Alimentos e Bebidas'),
    (2, 'Produtos de Limpeza'),
    (3, 'Higiene Pessoal'),
    (4, 'Eletrônicos'),
    (5, 'Roupas e Acessórios'),
    (6, 'Móveis e Decoração'),
    (7, 'Ferramentas e Equipamentos'),
    (8, 'Livros e Materiais de Escritório'),
    (9, 'Saúde e Bem-Estar'),
    (10, 'Automotivo'),
    (11, 'Outra');


-- Inserir produtos de Alimentos e Bebidas
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000001', 'Arroz', 5.25, 100, 1),
    ('1000000000002', 'Feijão', 7.0, 80, 1),
    ('1000000000003', 'Macarrão', 3.5, 120, 1),
    ('1000000000004', 'Leite', 2.75, 150, 1),
    ('1000000000005', 'Ovos', 4.0, 90, 1),
    ('1000000000006', 'Óleo de Soja', 6.5, 110, 1),
    ('1000000000007', 'Farinha de Trigo', 3.0, 100, 1),
    ('1000000000008', 'Açúcar', 4.75, 85, 1),
    ('1000000000009', 'Sal', 1.5, 130, 1),
    ('1000000000010', 'Café', 8.0, 70, 1),
    ('1000000000011', 'Carne', 12.5, 60, 1),
    ('1000000000012', 'Laranja', 1.75, 140, 1),
    ('1000000000013', 'Banana', 2.0, 110, 1),
    ('1000000000014', 'Cenoura', 1.2, 120, 1),
    ('1000000000015', 'Tomate', 1.5, 130, 1),
    ('1000000000016', 'Batata', 2.25, 95, 1),
    ('1000000000017', 'Cebola', 1.8, 110, 1),
    ('1000000000018', 'Alho', 2.5, 80, 1),
    ('1000000000019', 'Maçã', 3.0, 100, 1),
    ('1000000000020', 'Pêra', 2.75, 90, 1);

-- Inserir produtos de Produtos de Limpeza
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000021', 'Sabão em Pó', 8.0, 50, 2),
    ('1000000000022', 'Detergente', 3.5, 70, 2),
    ('1000000000023', 'Desinfetante', 6.0, 40, 2),
    ('1000000000024', 'Esponja de Limpeza', 2.5, 60, 2),
    ('1000000000025', 'Água Sanitária', 4.0, 30, 2),
    ('1000000000026', 'Lustra Móveis', 5.0, 45, 2),
    ('1000000000027', 'Limpa Vidros', 3.75, 55, 2),
    ('1000000000028', 'Limpa Carpete', 6.5, 25, 2),
    ('1000000000029', 'Amaciante de Roupas', 7.5, 40, 2),
    ('1000000000030', 'Sabão em Barra', 2.25, 75, 2),
    ('1000000000031', 'Desengordurante', 5.25, 35, 2),
    ('1000000000032', 'Esponja de Aço', 1.8, 65, 2),
    ('1000000000033', 'Inseticida', 9.0, 20, 2),
    ('1000000000034', 'Álcool Gel', 6.25, 50, 2),
    ('1000000000035', 'Pano de Limpeza', 3.0, 80, 2),
    ('1000000000036', 'Alvejante', 4.5, 30, 2),
    ('1000000000037', 'Limpa Banheiro', 5.75, 35, 2),
    ('1000000000038', 'Difusor de Ambiente', 8.5, 25, 2),
    ('1000000000039', 'Cera para Pisos', 7.0, 40, 2),
    ('1000000000040', 'Pasta para Limpeza', 4.25, 60, 2);

-- Inserir produtos de Higiene Pessoal
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000041', 'Sabonete', 2.5, 100, 3),
    ('1000000000042', 'Shampoo', 8.0, 80, 3),
    ('1000000000043', 'Condicionador', 7.0, 70, 3),
    ('1000000000044', 'Creme Dental', 4.25, 90, 3),
    ('1000000000045', 'Escova de Dentes', 3.0, 110, 3),
    ('1000000000046', 'Pasta de Dente', 3.5, 100, 3),
    ('1000000000047', 'Enxaguante Bucal', 6.0, 80, 3),
    ('1000000000048', 'Fio Dental', 2.75, 120, 3),
    ('1000000000049', 'Desodorante', 5.0, 70, 3),
    ('1000000000050', 'Papel Higiênico', 7.5, 60, 3),
    ('1000000000051', 'Toalha de Rosto', 6.25, 90, 3),
    ('1000000000052', 'Sabonete Líquido', 4.0, 80, 3),
    ('1000000000053', 'Creme Hidratante', 9.0, 50, 3),
    ('1000000000054', 'Protetor Solar', 12.0, 40, 3),
    ('1000000000055', 'Absorvente Feminino', 6.75, 60, 3),
    ('1000000000056', 'Cotonetes', 3.25, 110, 3),
    ('1000000000057', 'Barbeador Descartável', 5.5, 70, 3),
    ('1000000000058', 'Creme Depilatório', 7.75, 50, 3),
    ('1000000000059', 'Lâmina de Barbear', 2.0, 100, 3),
    ('1000000000060', 'Perfume', 15.0, 30, 3);

-- Inserir produtos de Eletrônicos
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000061', 'Smartphone', 1500.0, 50, 4),
    ('1000000000062', 'Notebook', 2500.0, 30, 4),
    ('1000000000063', 'Tablet', 800.0, 40, 4),
    ('1000000000064', 'Smartwatch', 400.0, 60, 4),
    ('1000000000065', 'Fone de Ouvido Bluetooth', 150.0, 100, 4),
    ('1000000000066', 'Caixa de Som Bluetooth', 200.0, 80, 4),
    ('1000000000067', 'Câmera Digital', 700.0, 20, 4),
    ('1000000000068', 'Televisão LED', 1200.0, 25, 4),
    ('1000000000069', 'Console de Video Game', 600.0, 45, 4),
    ('1000000000070', 'Impressora Multifuncional', 300.0, 35, 4),
    ('1000000000071', 'Roteador Wi-Fi', 80.0, 70, 4),
    ('1000000000072', 'Mouse Gamer', 100.0, 90, 4),
    ('1000000000073', 'Teclado Mecânico', 120.0, 80, 4),
    ('1000000000074', 'Monitor LED', 250.0, 40, 4),
    ('1000000000075', 'HD Externo', 150.0, 60, 4),
    ('1000000000076', 'Cartão de Memória', 50.0, 120, 4),
    ('1000000000077', 'Carregador Portátil', 30.0, 150, 4),
    ('1000000000078', 'Drone', 1000.0, 15, 4),
    ('1000000000079', 'Câmera de Segurança', 180.0, 50, 4),
    ('1000000000080', 'Adaptador HDMI', 20.0, 100, 4);

-- Inserir produtos de Roupas e Acessórios
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000081', 'Camiseta Masculina', 30.0, 150, 5),
    ('1000000000082', 'Camiseta Feminina', 30.0, 150, 5),
    ('1000000000083', 'Calça Jeans Masculina', 50.0, 100, 5),
    ('1000000000084', 'Calça Jeans Feminina', 50.0, 100, 5),
    ('1000000000085', 'Blusa de Frio Masculina', 60.0, 80, 5),
    ('1000000000086', 'Blusa de Frio Feminina', 60.0, 80, 5),
    ('1000000000087', 'Tênis Esportivo Masculino', 120.0, 60, 5),
    ('1000000000088', 'Tênis Esportivo Feminino', 120.0, 60, 5),
    ('1000000000089', 'Vestido Casual Feminino', 80.0, 70, 5),
    ('1000000000090', 'Camisa Social Masculina', 70.0, 90, 5),
    ('1000000000091', 'Camisa Social Feminina', 70.0, 90, 5),
    ('1000000000092', 'Blazer Masculino', 150.0, 40, 5),
    ('1000000000093', 'Blazer Feminino', 150.0, 40, 5),
    ('1000000000094', 'Bermuda Masculina', 40.0, 120, 5),
    ('1000000000095', 'Shorts Feminino', 40.0, 120, 5),
    ('1000000000096', 'Boné', 20.0, 200, 5),
    ('1000000000097', 'Óculos de Sol', 60.0, 100, 5),
    ('1000000000098', 'Cinto de Couro', 25.0, 150, 5),
    ('1000000000099', 'Bolsa Feminina', 80.0, 70, 5),
    ('1000000000100', 'Relógio de Pulso', 100.0, 50, 5);

-- Inserir produtos de Móveis e Decoração
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000101', 'Sofá', 800.0, 20, 6),
    ('1000000000102', 'Mesa de Jantar', 400.0, 30, 6),
    ('1000000000103', 'Cadeira de Escritório', 150.0, 50, 6),
    ('1000000000104', 'Cama Box Casal', 1000.0, 15, 6),
    ('1000000000105', 'Guarda-Roupas', 600.0, 25, 6),
    ('1000000000106', 'Poltrona', 300.0, 35, 6),
    ('1000000000107', 'Mesa de Centro', 200.0, 40, 6),
    ('1000000000108', 'Tapete', 80.0, 60, 6),
    ('1000000000109', 'Quadro Decorativo', 50.0, 80, 6),
    ('1000000000110', 'Luminária de Teto', 120.0, 30, 6),
    ('1000000000111', 'Cortina', 70.0, 50, 6),
    ('1000000000112', 'Estante', 250.0, 25, 6),
    ('1000000000113', 'Mesa Lateral', 100.0, 45, 6),
    ('1000000000114', 'Cadeira de Jardim', 80.0, 70, 6),
    ('1000000000115', 'Painel para TV', 180.0, 20, 6),
    ('1000000000116', 'Cômoda', 150.0, 35, 6),
    ('1000000000117', 'Vaso Decorativo', 40.0, 100, 6),
    ('1000000000118', 'Cadeira de Balanço', 120.0, 15, 6),
    ('1000000000119', 'Abajur', 60.0, 40, 6),
    ('1000000000120', 'Mesa de Escritório', 200.0, 30, 6);

-- Inserir produtos de Ferramentas e Equipamentos
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000121', 'Furadeira', 100.0, 30, 7),
    ('1000000000122', 'Parafusadeira', 80.0, 35, 7),
    ('1000000000123', 'Chave de Fenda', 10.0, 50, 7),
    ('1000000000124', 'Martelo', 15.0, 60, 7),
    ('1000000000125', 'Alicate', 12.0, 70, 7),
    ('1000000000126', 'Trena', 8.0, 80, 7),
    ('1000000000127', 'Serrote', 20.0, 40, 7),
    ('1000000000128', 'Escada', 50.0, 20, 7),
    ('1000000000129', 'Broca', 5.0, 90, 7),
    ('1000000000130', 'Nível a Laser', 120.0, 15, 7),
    ('1000000000131', 'Lixa', 3.0, 100, 7),
    ('1000000000132', 'Pá', 18.0, 25, 7),
    ('1000000000133', 'Enxada', 25.0, 20, 7),
    ('1000000000134', 'Régua de Medição', 6.0, 30, 7),
    ('1000000000135', 'Máscara de Proteção', 4.0, 40, 7),
    ('1000000000136', 'Luvas de Segurança', 7.0, 50, 7),
    ('1000000000137', 'Capacete de Proteção', 20.0, 60, 7),
    ('1000000000138', 'Maleta de Ferramentas', 60.0, 15, 7),
    ('1000000000139', 'Serra Elétrica', 150.0, 10, 7),
    ('1000000000140', 'Lanterna', 15.0, 30, 7);

-- Inserir produtos de Livros e Materiais de Escritório
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000141', 'Livro de Romance', 25.0, 50, 8),
    ('1000000000142', 'Livro de Ficção Científica', 30.0, 40, 8),
    ('1000000000143', 'Livro de Autoajuda', 20.0, 60, 8),
    ('1000000000144', 'Agenda', 15.0, 70, 8),
    ('1000000000145', 'Caderno Universitário', 10.0, 80, 8),
    ('1000000000146', 'Canetas Esferográficas', 1.0, 200, 8),
    ('1000000000147', 'Marcadores de Texto', 1.5, 150, 8),
    ('1000000000148', 'Papel Sulfite', 5.0, 100, 8),
    ('1000000000149', 'Calculadora Científica', 20.0, 30, 8),
    ('1000000000150', 'Porta-Retratos', 8.0, 80, 8),
    ('1000000000151', 'Grampeador', 5.0, 50, 8),
    ('1000000000152', 'Clips', 0.5, 200, 8),
    ('1000000000153', 'Lápis de Cor', 2.0, 100, 8),
    ('1000000000154', 'Tesoura Escolar', 3.0, 70, 8),
    ('1000000000155', 'Borracha', 0.5, 150, 8),
    ('1000000000156', 'Apontador', 1.0, 100, 8),
    ('1000000000157', 'Envelope', 0.3, 200, 8),
    ('1000000000158', 'Dicionário', 15.0, 30, 8),
    ('1000000000159', 'Pasta Suspensa', 3.0, 80, 8),
    ('1000000000160', 'Gravador de Voz', 40.0, 20, 8);

-- Inserir produtos de Saúde e Bem-Estar
INSERT INTO produtos (codigoDeBarra, nome, preco, quantidade, categoria_id) VALUES 
    ('1000000000161', 'Termômetro Digital', 10.0, 60, 9),
    ('1000000000162', 'Fita Métrica', 5.0, 80, 9),
    ('1000000000163', 'Balança Digital', 20.0, 40, 9),
    ('1000000000164', 'Fraldas Descartáveis', 25.0, 50, 9),
    ('1000000000165', 'Suplemento de Vitamina C', 15.0, 70, 9),
    ('1000000000166', 'Creme Hidratante Corporal', 8.0, 100, 9),
    ('1000000000167', 'Fio Dental', 2.0, 150, 9),
    ('1000000000168', 'Escova de Dentes Elétrica', 30.0, 30, 9),
    ('1000000000169', 'Preservativos', 12.0, 50, 9),
    ('1000000000170', 'Antiácido', 5.0, 60, 9),
    ('1000000000171', 'Protetor Solar', 15.0, 80, 9),
    ('1000000000172', 'Shampoo Anticaspa', 10.0, 70, 9),
    ('1000000000173', 'Condicionador Hidratante', 8.0, 90, 9),
    ('1000000000174', 'Termômetro de Testa', 12.0, 40, 9),
    ('1000000000175', 'Curativo Adesivo', 3.0, 120, 9),
    ('1000000000176', 'Álcool em Gel', 5.0, 100, 9),
    ('1000000000177', 'Analgésico', 8.0, 80, 9),
    ('1000000000178', 'Anti-inflamatório', 10.0, 70, 9),
    ('1000000000179', 'Desodorante Antitranspirante', 6.0, 90, 9),
    ('1000000000180', 'Soro Fisiológico', 4.0, 100, 9);
