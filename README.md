# Gerenciador de Estoque e Fluxo de Caixa

Este é um projeto de um sistema de gerenciamento de estoque e fluxo de caixa desenvolvido em Java. O sistema permite o cadastro, edição, remoção e listagem de produtos no estoque, além de fornecer funcionalidades de controle de vendas e fluxo de caixa.

## Funcionalidades:

### Gerenciador de Estoque:
* Cadastro, edição, remoção e listagem de produto(s).
* Visualização de produtos com estoque baixo.
* Ativação do gerador de nota fiscal.
* Listagem de todas as vendas realizadas.
* Listagem de vendas por data específica.
* Detalhamento de uma venda específica.

### Fluxo de Caixa:
* Adição e remoção de produtos em uma sacola de compras.
* Modificação de quantidades na sacola de compras.
* Visualização de estoque
* Listagem dos produtos no carrinho de compras.
* Limpeza do carrinho de compras.
* Finalização da compra e geração de nota fiscal.

## Tecnologias Utilizadas
* Java 8: Linguagem de programação utilizada para o desenvolvimento do sistema.
* Hibernate: Framework utilizado como implementação do JPA (Java Persistence API) para a persistência de dados em um banco de dados relacional (MySQL).
* Swing: Biblioteca gráfica utilizada para a interface do usuário.

## Estrutura do Projeto

### O projeto segue a estrutura padrão MVC (Model-View-Controller), onde:

* Model: Representado pelas classes Produto, Venda, ItemVenda e Categoria, responsáveis pela representação dos dados e regras de negócio.
* View: Representada pelas interfaces gráficas do sistema, como os menus de interação com o usuário.
* Controller: Representado pelas classes MenuPrincipalController, EstoqueController e CaixaController, responsáveis por intermediar as interações entre a View e o Model, executando as operações necessárias.

Além disso, o projeto utiliza o padrão de projeto Factory para a criação de instâncias dos controllers e serviços, garantindo a centralização da lógica de instância e promovendo a reutilização de objetos.

## Pré-Requisitos
* Java Development Kit (JDK) instalado na máquina.
* Banco de dados MySQL configurado e acessível.
* IDE compatível com projetos Java para compilação e execução do código.
* A execução da query localizada na pasta database para adicionar a tabela produtos ao banco de dados é opcional. Mas caso queira adicionar, primeiro execute o programa para poder adicionar as categorias antes dos produtos.

## Como Executar
* Clone o repositório para sua máquina local.
* Abra o projeto em sua IDE Java.
* Certifique-se de ter configurado corretamente o banco de dados MySQL e as credenciais de acesso no arquivo persistence.xml.
* Compile e execute o projeto a partir da classe Main.

## Acesso a Partes Sensíveis
* Para acessar partes sensíveis do programa, como o gerenciador de estoque, é necessário utilizar a senha senha123.

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para propor melhorias e reportar problemas do projeto.

## Licença
Este projeto está licenciado sob a [Licença MIT](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/LICENSE).