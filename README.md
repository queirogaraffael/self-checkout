# Gerenciador de Estoque e Fluxo de Caixa

Este é um projeto de um sistema de gerenciamento de estoque e fluxo de caixa desenvolvido em Java, com foco para pequenos comerciantes. O sistema permite o cadastro, edição, remoção e listagem de produtos no estoque, além de fornecer funcionalidades de controle de vendas e fluxo de caixa.

## Funcionalidades:

### Gerenciador de Estoque:

- Cadastro, edição, remoção e listagem de produto(s).
- Visualização de produtos com estoque baixo.
- Ativação do gerador de nota fiscal.
- Listagem de todas as vendas realizadas.
- Listagem de vendas por data específica.
- Detalhamento de uma venda específica.

### Fluxo de Caixa:

- Adição e remoção de produtos em uma sacola de compras.
- Modificação de quantidades na sacola de compras.
- Visualização de estoque
- Listagem dos produtos no carrinho de compras.
- Limpeza do carrinho de compras.
- Finalização da compra e geração de nota fiscal.

## Tecnologias Utilizadas

- Java 8: Linguagem de programação utilizada no desenvolvimento do sistema.
- Hibernate: Framework ORM usado como implementação do JPA (Java Persistence API) para gerenciar a persistência de dados em um banco de dados relacional (MySQL).
- SQL: Linguagem utilizada para manipulação e consulta de dados no banco.
- JPQL: Linguagem de consultas orientada a objetos para interagir com entidades JPA.
- Swing: Biblioteca gráfica empregada na criação da interface do usuário.
- JUnit: Framework utilizado para testes unitários.
- Mockito: Framework utilizado para testes de integração.

## Configuração do Banco de Dados (persistence.xml)

O arquivo `persistence.xml` foi utilizado para configurar a persistência de dados com o JPA (Java Persistence API) e o Hibernate como provedor. Ele estabelece a conexão com o banco de dados MySQL, configurando a URL de conexão, as credenciais de acesso, a configuração do Hibernate e o gerenciamento de transações, garantindo a integridade dos dados.

## Imagens do Sistema

### Tela Principal

![Menu Principal](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assets/Menu%20Principal.png?raw=true)

### Gerenciador de Estoque

![Gerenciador de Estoque](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assets/Gerenciador%20Estoque%201.png?raw=true)
![Gerenciador de Estoque](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assets/Gerenciador%20Estoque%202.png?raw=true)

### Fluxo de Caixa

![Fluxo de Caixa](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assets/Fluxo%20Caixa.png?raw=true)

### Validação de Senha

![Validação de Senha](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assets/Validacao%20Senha.png?raw=true)

## Estrutura do Projeto

### O projeto segue a estrutura padrão MVC (Model-View-Controller), onde:

- Model: Representado pelas classes Produto, Venda, ItemVenda e Categoria, responsáveis pela representação dos dados e regras de negócio.
- View: Representada pelas interfaces gráficas do sistema, como os menus de interação com o usuário.
- Controller: Representado pelas classes MenuPrincipalController, EstoqueController e CaixaController, responsáveis por intermediar as interações entre a View e o Model, executando as operações necessárias.

Além disso, o projeto utiliza o padrão de projeto Factory para a criação de instâncias dos controllers e serviços, garantindo a centralização da lógica de instância e promovendo a reutilização de objetos.

### Diagrama UML

![Diagrama UML](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/assest/UML.png?raw=true)

## Pré-Requisitos

- Java Development Kit (JDK) instalado na máquina.
- Banco de dados MySQL configurado e acessível.
- IDE compatível com projetos Java para compilação e execução do código.
- A execução da query localizada na pasta database para adicionar a tabela produtos ao banco de dados é opcional. Mas caso queira adicionar, primeiro execute o programa para poder adicionar as categorias antes dos produtos.

## Como Executar

- Clone o repositório para sua máquina local.
- Rode o script SQL
- Certifique-se de ter configurado corretamente o banco de dados MySQL e as credenciais de acesso no arquivo persistence.xml.
- Abra o projeto em sua IDE Java.
- Compile e execute o projeto a partir da classe Main.

## Acesso a Partes Sensíveis

- Para acessar partes sensíveis do programa, como o gerenciador de estoque, é necessário utilizar a senha senha123.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para propor melhorias e reportar problemas do projeto.

## Reflexões sobre o Projeto

### O que aprendi

O desenvolvimento deste projeto proporcionou uma compreensão mais sólida de arquitetura de software. Por não ser baseado em um curso ou tutorial, a decisão de adotar o padrão MVC foi tomada desde o início, o que exigiu pesquisa ativa sobre a melhor forma de implementá-lo. Além disso, trabalhar com Java 8 e ferramentas menos popularizadas atualmente, como o `EntityManagerFactory` do Hibernate, representou um contato direto com código e práticas consideradas legado, experiência que contribuiu diretamente para a transição posterior ao Spring Boot.

### O que aprendi de mais valioso

O aprendizado mais significativo foi o gerenciamento manual do ciclo de vida dos objetos. Sem um container de IoC, foi necessário controlar explicitamente a criação, o uso e o encerramento de instâncias como `EntityManagerFactory` e `EntityManager`, incluindo a abertura e o fechamento manual de transações, responsabilidades que no Spring Boot são abstraídas pelo framework. Esse contato direto tornou conceitos como injeção de dependência e gerenciamento de beans muito mais concretos.

Em relação às ferramentas utilizadas, a escolha foi guiada pelo interesse em aprendê-las, sem ter passado por um processo formal de avaliação de alternativas. Ainda assim, a combinação de Java com um ORM se mostrou tecnicamente coerente para um sistema que lida com transações e persistência relacional.

### Problemas atuais

A aplicação não possui controle de concorrência implementado na camada Java. Embora o banco de dados garanta a integridade dos dados por meio de constraints SQL, o uso simultâneo por múltiplos usuários pode gerar inconsistências na camada da aplicação. O sistema é composto por dois módulos distintos — gerenciamento de estoque e fluxo de caixa — e ambos não possuem tratamento de concorrência em nível de aplicação, o que significa que, embora poucos usuários simultâneos possam não causar problemas imediatos, à medida que o número de usuários cresce, o risco de inconsistências nos dados aumenta progressivamente.

### O que melhoraria

A principal melhoria seria na organização dos pacotes. Em nível de código, as classes estão bem relacionadas arquiteturalmente, mas a estrutura de diretórios apresenta desorganização que dificulta a navegação e manutenção do projeto. Além disso, seria relevante revisar a aplicação do padrão Factory, refinando seu uso para torná-lo mais consistente e alinhado com as responsabilidades de cada camada. Por fim, a implementação de controle de concorrência permitiria que o sistema fosse utilizado por múltiplos usuários simultaneamente, eliminando a limitação atual. Vale destacar que algumas das ferramentas utilizadas impõem restrições que limitam certos tipos de avanço, o que também seria endereçado com uma eventual migração para tecnologias mais modernas.

## Licença

Este projeto está licenciado sob a [Licença MIT](https://github.com/queirogaraffael/sistema-estoque-caixa/blob/main/LICENSE).
