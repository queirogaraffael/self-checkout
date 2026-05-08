# Self-Checkout Retail Desktop

![Java](https://img.shields.io/badge/Java-8-007396?logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-5.4-59666C?logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-4.12-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-1.10.19-green)
![License](https://img.shields.io/badge/Licença-MIT-blue)

---

## Visão Geral

Sistema desktop de autoatendimento (self-checkout) para pequeno varejo, desenvolvido em Java puro sem frameworks de aplicação (sem Spring, sem CDI). O objetivo do projeto foi implementar manualmente as responsabilidades que frameworks modernos abstraem: gerenciamento de beans, ciclo de vida do EntityManager, injeção de dependência e concorrência, com o propósito de compreender o que ocorre por baixo dessas abstrações.

**Arquitetura e Padrões:** O projeto segue o padrão arquitetural **MVC (Model-View-Controller)**, garantindo que as regras de negócio e a persistência de dados (Model) estejam totalmente desacopladas das interfaces gráficas (View) através da orquestração de controladores intermediários (Controller).

**Contexto e Escopo de Uso:** O sistema foi dimensionado para as características reais de um pequeno/médio supermercado:

- **Escala de Terminais:** Projetado para operar em um ambiente com, no máximo, 4 a 10 caixas de autoatendimento funcionando simultaneamente na mesma loja.
- **Transações Rápidas (Modelo Express):** Focado no comportamento real de caixas de autoatendimento, que recebem clientes com carrinhos menores (estimado para o processamento rápido de, no máximo, ~60 itens). O sistema não é projetado para processar "compras do mês" gigantescas, mas sim para dar agilidade à loja.
  _Nota: Este escopo específico foi fundamental para as decisões de design arquitetural. Com poucas instâncias e transações breves, abordagens de performance mais "leves", como o Optimistic Locking, tornam-se altamente eficientes e seguras._

O sistema tem dois módulos:

- **Autoatendimento (Fluxo de Caixa):** terminal voltado ao cliente final, com adição e remoção de produtos, correção de quantidade, finalização da compra e geração de nota fiscal em arquivo.
- **Painel de Administração:** módulo restrito por senha para operadores, com cadastro e edição de produtos, categorias, visualização de estoque baixo, relatório de vendas por data e detalhamento de venda.

**Por que Hibernate sem Spring Data?** A escolha foi deliberada: gerenciar o `EntityManager` e as transações manualmente torna o contrato com o banco de dados explícito, sem a mediação de repositórios gerados por proxy. Isso é descrito em detalhe na seção [JPAManager](#jpamanager).

**Por que Swing?** A escolha do Swing com `JOptionPane` foi motivada pela facilidade de implementação e pelo foco em manter o projeto centrado nas decisões de arquitetura de backend. Para um terminal de autoatendimento em produção, interfaces mais robustas (JavaFX, Electron, ou aplicações web) seriam mais adequadas.

---

## Diagrama de Classes

Entidades JPA, chave primária composta e enums de domínio:

![Diagrama de Classes JPA](https://github.com/queirogaraffael/self-checkout/blob/main/assets/venda-produto-item-jpa-class-diagram.png?raw=true)

**Legenda:**

| Símbolo           | Significado                                           |
| ----------------- | ----------------------------------------------------- |
| 🔑                | Chave Primária (`@Id`)                                |
| 🔒                | Campo de versionamento (`@Version` — Optimistic Lock) |
| `<<Entity>>`      | Classe mapeada como tabela JPA                        |
| `<<Embeddable>>`  | Chave primária composta embutida                      |
| `<<enumeration>>` | Enum do domínio                                       |

**Notas de Mapeamento:**

- `ItemVenda` usa chave primária composta (`@EmbeddedId`) via `ItemVendaPK`, que referencia `Venda` e `Produto`.
- `Produto.version` é gerenciado automaticamente pelo Hibernate para Optimistic Locking.
- `Categoria` usa `CascadeType.ALL` sobre `Produto`.
- `StatusNotaFiscal` pertence à entidade `NotaFiscal` da camada de domínio, não mapeada diretamente como tabela JPA.
- `ResultadoFinalizacao` é um enum de resultado de operação, não persistido no banco.

## Estrutura do Projeto

A organização de pacotes do sistema reflete a arquitetura MVC e o isolamento claro das responsabilidades entre configurações, persistência, regras de negócio e interface com o usuário:

```text
src/main/java/gerenciador
├── config          # Configuração de IoC manual (ApplicationContext, ControllerRegistry) e Nota Fiscal
├── constant        # Constantes literais do sistema (textos de menu)
├── controller      # Controladores MVC (orquestram as chamadas entre View e Service)
├── dto             # Objetos de Transferência de Dados (Data Transfer Objects)
├── infrastructure  # Camada de infraestrutura técnica e conexão com o banco de dados
│   ├── exception   # Exceções personalizadas de negócio (ProdutoEsgotadoException, etc)
│   └── repository  # Interfaces de repositório e implementações concretas (Hibernate)
├── model           # Entidades JPA (@Entity), chaves primárias compostas e Enums de domínio
├── service         # Núcleo da lógica de negócio, cálculos (BigDecimal) e orquestração de persistência
├── session         # Gerenciamento de timeout de sessão do cliente e monitoramento multithread
├── util            # Classes utilitárias diversas (ex: AutenticadorDeSenha)
└── view            # Telas da Interface Gráfica do usuário (Swing)
```

---

## Retry com Exponential Backoff + Jitter

### Contexto e necessidade

Quando dois terminais de autoatendimento finalizam uma compra simultaneamente, o Hibernate detecta a colisão via `@Version` e lança `OptimisticLockException`. Sem um mecanismo de retry, o segundo terminal simplesmente falharia e devolveria o erro ao cliente, o que é inaceitável em um ponto de venda.

### Implementação

A lógica está em `FinalizarCompraRepositoryHibernate`:

```java
private static final int MAX_TENTATIVAS = 3;
private static final long DELAY_BASE_MS = 100;
private static final long DELAY_MAX_MS  = 1000;
private static final Random random = new Random();

// No loop de retry:
} catch (RollbackException | OptimisticLockException e) {
    tentativas++;
    aguardarComBackoffEJitter(tentativas);
}

private void aguardarComBackoffEJitter(int tentativa) {
    long backoff = Math.min(DELAY_MAX_MS, DELAY_BASE_MS * (1L << tentativa));
    long delay   = (long) (random.nextDouble() * backoff);
    Thread.sleep(delay);
}
```

> **Nota sobre `Thread.sleep` neste contexto:** O bloqueio da thread com `Thread.sleep` não representa problema de performance aqui porque cada terminal roda em sua própria instância de JVM, com recursos dedicados. Bloquear a thread por até 800 ms só afeta aquele terminal específico — nenhum outro terminal é impactado.
>
> Em uma aplicação Spring Boot servindo múltiplos clientes via HTTP, o cenário seria diferente. O servidor web (Tomcat, por exemplo) mantém um pool de threads compartilhado. Se várias requisições simultâneas executassem retries com `Thread.sleep`, o pool seria progressivamente esgotado, causando degradação de performance ou recusa de novas requisições. Vale destacar que `@Retryable` do Spring Retry tem o mesmo comportamento de bloqueio de thread — ele simplifica a sintaxe, mas não resolve o problema de esgotamento do pool.
>
> A solução adequada para esse cenário web seria o processamento assíncrono via mensageria (RabbitMQ, Kafka) ou retry não-bloqueante com Spring WebFlux (`Mono.retryWhen()`). No contexto deste sistema desktop, porém, `Thread.sleep` é a abordagem direta e correta.

**Cálculo do backoff por tentativa:**

| Tentativa | Backoff máximo | Delay efetivo |
| --------- | -------------- | ------------- |
| 1ª        | 200 ms         | [0, 200) ms   |
| 2ª        | 400 ms         | [0, 400) ms   |
| 3ª        | 800 ms         | [0, 800) ms   |

O backoff é exponencial (`DELAY_BASE_MS * 2^tentativa`), limitado a 1.000 ms pelo `Math.min`.

### Por que o Jitter foi necessário

O jitter (`random.nextDouble() * backoff`) distribui o delay dentro do intervalo `[0, backoff)` em vez de usar o valor exato. Sem ele, dois terminais que colidiram aguardariam exatamente o mesmo tempo e voltariam a colidir na retentativa, o chamado _thundering herd_ em miniatura. Com jitter, os retries são dessincronizados, reduzindo a probabilidade de nova colisão.

### Tratamento diferenciado por tipo de exceção

`ProdutoEsgotadoException` é re-lançada imediatamente sem retry — se o estoque acabou, nenhuma retentativa vai mudar isso. Somente `OptimisticLockException` e `RollbackException` acionam o mecanismo de retry. Após esgotar as tentativas, `SistemaOcupadoException` é lançada para a camada de serviço.

### Integridade de Estoque e Risco de Inconsistência

Considere o seguinte cenário: há apenas uma unidade de um produto em estoque e dois clientes o escaneiam em terminais diferentes, finalizando a compra ao mesmo tempo. Sem tratamento de concorrência, ambas as transações leriam `quantidade = 1`, ambas subtrairiam 1 e ambas commitariam, resultando em `quantidade = -1` no banco — uma inconsistência grave.

Isso não é apenas um cenário de tentativa deliberada de burlar o sistema. É uma condição de corrida legítima que pode acontecer naturalmente em qualquer loja com baixo estoque. O Optimistic Locking garante que apenas uma das transações seja commitada; o retry com leitura de dados frescos garante que a segunda detecte o estoque zerado e retorne `ProdutoEsgotadoException` ao cliente, mantendo a integridade do banco.

## Gerenciamento de Concorrência

### Optimistic Locking via `@Version`

`Produto` declara um campo `@Version`:

```java
@Version
private Integer version;
```

O Hibernate incrementa esse campo automaticamente a cada `UPDATE`. Se dois terminais leram `version = 5` e um deles commitou primeiro, o segundo receberá `OptimisticLockException` ao tentar commitar, pois o `WHERE version = 5` não encontrará mais a linha.

### Justificativa para uso de Lock Otimista

O lock pessimista foi considerado e descartado por um motivo técnico específico. O método de finalizar a compra busca múltiplos produtos em uma única query com `IN :ids`, onde os IDs são derivados de um `Set<ItemVenda>` sem ordenação garantida. Em um cenário de lock pessimista com `SELECT ... FOR UPDATE`, duas transações concorrentes que compartilham produtos em comum poderiam adquirir os locks em ordens diferentes, introduzindo risco de *deadlock* clássico sem que haja qualquer garantia estrutural de ordenação para preveni-lo.

O lock otimista elimina esse risco porque nenhum lock é adquirido no momento da leitura. A integridade é garantida pelo campo `@Version` na entidade `Produto`, que faz o MySQL rejeitar no commit qualquer transação que tente sobrescrever uma versão já modificada por outra transação concorrente. Quando isso ocorre, a transação é reexecutada com *backoff* exponencial e *jitter*, relendo o estado atual do banco.

Vale registrar que o argumento de *overhead* não é determinante nesse caso. Em um portal de autoatendimento com volume baixo por transação e baixa probabilidade de dois clientes disputarem o mesmo produto simultaneamente, o custo real de ambas as abordagens é simétrico. A escolha pelo lock otimista se justifica pela ausência de risco de deadlock, e não por ganho de performance.

### Concorrência no Painel Administrativo vs Autoatendimento

Enquanto o módulo de autoatendimento trata colisões de concorrência com retentativas automáticas (*retry* + *jitter*) para não interromper a experiência do cliente final, o painel administrativo adota uma estratégia de **Fail-Fast com intervenção manual**.

Se dois operadores tentarem editar o mesmo produto simultaneamente e ocorrer uma colisão (`OptimisticLockException`), a camada de persistência lança uma `ProdutoModificadoConcorrentementeException`. O *Controller* captura essa exceção e exibe um alerta informando ao funcionário que os dados foram alterados por outro usuário, exigindo que ele reabra o produto.

Essa distinção de design é intencional: no autoatendimento, a colisão é geralmente uma disputa de "quantidade de estoque", que pode ser resolvida de forma segura relendo o banco. No painel administrativo, o conflito pode envolver preços ou descrições, e uma retentativa automática poderia sobrescrever a decisão humana do outro operador silenciosamente. Ao notificar o usuário, garantimos que qualquer alteração administrativa seja baseada no estado mais recente e consistente do produto.

### Isolation Level

O projeto foi desenvolvido e validado com `REPEATABLE_READ`, que é o padrão do MySQL. Não é necessário alterar essa configuração. A consistência das transações concorrentes é garantida pelo mecanismo de lock otimista via `@Version` no nível da aplicação, mantendo a integridade dos dados de forma independente do *isolation level* configurado no servidor.

### Transação por tentativa com `EntityManager` isolado

Cada iteração do loop de retry cria e fecha um `EntityManager` independente:

```java
while (tentativas < MAX_TENTATIVAS) {
    EntityManager em = entityManagerFactory.createEntityManager();
    try {
        em.getTransaction().begin();
        // ... lógica de negócio
        em.getTransaction().commit();
        return venda;
    } catch (OptimisticLockException | RollbackException e) {
        tentativas++;
        aguardarComBackoffEJitter(tentativas);
    } finally {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
    }
}
```

Isso garante que cada retentativa parte de um contexto de persistência limpo, sem dados em cache do `EntityManager` anterior.

### Otimização: batch SELECT

Em vez de N queries (uma por produto no carrinho), todos os produtos são carregados em uma única query com `IN`:

```java
em.createQuery("SELECT p FROM Produto p WHERE p.id IN :ids", Produto.class)
  .setParameter("ids", ids)
  .getResultList();
```

Isso reduz o número de round-trips ao banco de O(n) para O(1) por tentativa de finalização.

### Thread safety na camada de controller

O `AutoatendimentoController` captura `Exception` genérica no loop principal, exibe `AlertaGeralView` e **continua no loop**, sem nunca propagar a exceção para o menu principal. Essa decisão é de segurança: um erro de runtime dentro de um módulo de autoatendimento não deve permitir que o terminal retorne ao menu administrativo sem autenticação.

---

## Gerenciamento de Sessão e Threads

### Modelo de sessão

Em um terminal de autoatendimento, um cliente pode começar a escanear produtos, montar o carrinho e, por qualquer motivo, abandonar o terminal sem finalizar a compra. Sem um mecanismo de limpeza automática, o próximo cliente encontraria produtos já adicionados na sacola, o que exigiria ação manual de um operador. A sessão com timeout de 2 minutos resolve isso: após inatividade, o carrinho é limpo automaticamente e o terminal fica pronto para o próximo cliente.

`SessaoAutoatendimento` encapsula o estado da sessão de um cliente no terminal:

```java
private static final long TIMEOUT_MS = 2 * 60 * 1000L; // 2 minutos

private final AtomicLong    ultimaAtividade        = new AtomicLong(System.currentTimeMillis());
private final AtomicBoolean noMenuAutoatendimento  = new AtomicBoolean(true);
```

`AtomicLong` e `AtomicBoolean` são usados porque o estado é lido e escrito por duas threads distintas: a thread principal (EDT do Swing, que executa as ações do usuário) e a thread do monitor de sessão.

A cada ação do usuário, `sessao.registrarAtividade()` atualiza o timestamp. `estaExpirada()` compara o tempo decorrido com `TIMEOUT_MS`.

### Thread do monitor de sessão

`MonitorSessao` cria uma thread daemon com `ScheduledExecutorService` que verifica a sessão a cada 10 segundos:

```java
this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
    Thread t = new Thread(r, "monitor-sessao");
    t.setDaemon(true); // não impede o JVM de encerrar
    return t;
});

tarefa = scheduler.scheduleWithFixedDelay(() -> {
    if (sessao.estaExpirada()) {
        if (!sacolaVazia.get() || !sessao.isNoMenuAutoatendimento()) {
            fecharDialogosAbertos();
            SwingUtilities.invokeLater(() -> {
                aoExpirar.run();  // limpa carrinho
                sessao.reiniciar();
            });
        } else {
            sessao.reiniciar(); // sem carrinho, silencioso
        }
    }
}, 10, 10, TimeUnit.SECONDS);
```

**Decisões de design:**

1. A thread é daemon para não manter a JVM viva após o encerramento normal do programa.
2. A ação de expiração (`aoExpirar.run()`) é despachada via `SwingUtilities.invokeLater` para garantir que modificações na UI ocorram na Event Dispatch Thread, evitando condições de corrida com o Swing.
3. A sessão só é expirada se o carrinho não estiver vazio ou o terminal não estiver no menu inicial. Se o cliente já finalizou e saiu, o timeout é reiniciado silenciosamente sem exibir alertas desnecessários. Se o cliente abandonou em algum submenu com o carrinho vazio, o sistema retorna ao menu principal automaticamente.
4. Quando a expiração é detectada com itens no carrinho, todos os diálogos abertos são fechados, o carrinho é limpo e o terminal retorna ao menu principal, pronto para o próximo cliente.
5. `monitor.parar()` é chamado explicitamente ao sair do módulo de autoatendimento, liberando o scheduler.

## Ciclos de Dependência e Gerenciamento Manual de Beans

### O problema

`EstoqueController` e `AutoatendimentoController` compartilham três dependências: `NotaFiscal`, `ItemVendaService` e `ProdutoService`. Sem um mecanismo de compartilhamento, cada chamada `create*Controller()` instanciaria novos objetos, quebrando o estado compartilhado (especialmente `NotaFiscal`, que armazena a configuração de nota fiscal ativada/desativada pelo operador de estoque e usada pelo módulo de autoatendimento).

Vale notar que, como o projeto roda um único processo por terminal, `ApplicationContext` é instanciado uma única vez em `Main` e os controllers só seriam criados mais de uma vez se o código explicitamente chamasse `create*Controller()` repetidamente. O padrão lazy singleton foi implementado de forma deliberada porque é exatamente o que o container do Spring faz internamente com beans de escopo singleton. Implementá-lo manualmente torna o comportamento explícito e demonstra compreensão do que o framework abstrai.

### Solução: lazy singleton manual no `ControllerRegistry`

```java
public ItemVendaService createItemVendaService() {
    if (itemVendaService == null) {
        itemVendaService = new ItemVendaService(...);
    }
    return itemVendaService;
}
```

Cada `create*` verifica se a instância já existe antes de criá-la. O resultado é um singleton por escopo de `ControllerRegistry`, garantindo que ambos os controllers recebam exatamente a mesma instância de `NotaFiscal`, `ItemVendaService` e `ProdutoService`.

### O que o Spring faria

Em um contexto Spring, beans com `@Bean` em uma `@Configuration` são singleton por padrão. O container resolve automaticamente qual instância injetar em cada ponto, sem a necessidade de checagem manual de nulos. Dependências circulares reais (A depende de B que depende de A) seriam detectadas em tempo de inicialização ou resolvidas com `@Lazy`.

Implementar esse padrão manualmente torna explícito o que o container de IoC do Spring faz implicitamente: manter um registro de instâncias e garantir que cada bean seja criado uma única vez.

---

## JPAManager

### O que é

`JPAManager` é a classe responsável pelo ciclo de vida do `EntityManagerFactory` — o objeto mais caro da stack JPA, que gerencia o pool de conexões com o banco e os metadados do mapeamento objeto-relacional.

### Por que foi necessário implementá-lo manualmente

Sem Spring, não há autoconfiguration que leia as propriedades do `application.properties` e instancie o `EntityManagerFactory`. É necessário chamar `Persistence.createEntityManagerFactory(...)` explicitamente, e garantir que isso aconteça uma única vez durante o ciclo de vida da aplicação.

### O que ele faz

```java
public EntityManagerFactory entityManagerFactory() {
    if (entityManagerFactory == null) {
        entityManagerFactory = Persistence.createEntityManagerFactory(
            "persistencia", carregarConfiguracoesBanco()
        );
    }
    return entityManagerFactory;
}

public void fechaEntityManagerFactory() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
        entityManagerFactory.close();
    }
}
```

- **Singleton:** O `EntityManagerFactory` é criado uma única vez (conexão ao pool é cara).
- **Externalização de credenciais:** `carregarConfiguracoesBanco()` lê `config.properties` em tempo de execução, sobrescrevendo as credenciais do `persistence.xml`. Isso permite que usuário e senha sejam configurados sem recompilar o projeto — alinhado ao princípio de configuração externa.
- **Fechamento explícito:** `MenuPrincipalController` chama `fechaEntityManagerFactory()` no bloco `finally` ao encerrar a aplicação, garantindo que o pool de conexões seja liberado corretamente.

### O que o Spring abstrairia

Com Spring Boot + Spring Data JPA, o `EntityManagerFactory` é criado automaticamente pela autoconfiguration ao detectar as dependências no classpath e as propriedades em `application.properties`. O `@Transactional` gerencia abertura e fechamento de `EntityManager` por método, sem código manual. `JpaRepository` elimina a necessidade de escrever JPQL para operações básicas.

---

## O Que Foi Feito Manualmente vs. O Que o Spring Abstrairia

| Responsabilidade                        | Implementação manual neste projeto                                        | Equivalente Spring                                           |
| --------------------------------------- | ------------------------------------------------------------------------- | ------------------------------------------------------------ |
| Container de IoC                        | `ApplicationContext.java` (instanciação manual em sequência)              | `@SpringBootApplication` + `@Configuration`                  |
| Registro de beans                       | `ControllerRegistry` com lazy singleton por null-check                    | `@Bean` com scope singleton                                  |
| Injeção de dependência                  | Passagem via construtor, wiring manual                                    | `@Autowired` / injeção por construtor                        |
| Ciclo de vida do `EntityManagerFactory` | `JPAManager` com singleton e `close()` explícito                          | Spring Data JPA autoconfiguration                            |
| Gestão de `EntityManager` por operação  | `em = emf.createEntityManager()` + `em.close()` no finally                | `@Transactional` + `EntityManager` gerenciado pelo container |
| Controle de transação                   | `em.getTransaction().begin()` / `commit()` / `rollback()` manual          | `@Transactional` (AOP proxy)                                 |
| Repositórios CRUD                       | Implementações Hibernate concretas com JPQL explícito                     | `JpaRepository<T, ID>` (gerado por proxy)                    |
| Retry em transação                      | Loop manual com backoff e jitter em `FinalizarCompraRepositoryHibernate`  | `@Retryable` (Spring Retry)                                  |
| Externalização de credenciais           | Leitura manual de `config.properties` em `JPAManager`                     | `application.properties` + `@Value`                          |
| Exceções de negócio                     | Hierarquia manual (`ProdutoEsgotadoException`, `SistemaOcupadoException`) | Mesmas, com `@ControllerAdvice` para APIs                    |

> **Nota sobre retry em aplicações web:** Em uma aplicação Spring Boot com múltiplos clientes simultâneos, o uso de `@Retryable` com sleep bloquearia threads do pool do Tomcat durante cada tentativa. Com carga suficiente, isso esgotaria o pool e causaria degradação de performance, pois as threads estariam dormindo em vez de processar novas requisições. A solução adequada para esse cenário seria o processamento assíncrono via mensageria (RabbitMQ, Kafka) ou retry não-bloqueante com `Mono.retryWhen()` no Spring WebFlux. No contexto deste sistema desktop, onde cada terminal tem sua própria JVM e recursos dedicados, o retry síncrono com sleep é a abordagem correta.

## Testes

### Estratégia

A suíte cobre a camada de serviço via testes unitários com Mockito. A decisão de não implementar testes de integração (com banco em memória H2) foi consciente: os repositórios delegam ao Hibernate operações que o framework já testa internamente. O valor marginal de um teste de integração de repositório simples não justifica o custo de configuração do schema H2 e seed de dados para cada execução.

O teste mais crítico é o de `FinalizarCompraService`, que cobre a lógica de negócio que orquestra o retry, a nota fiscal e o mapeamento de exceções para o enum `ResultadoFinalizacao`.

### Ferramentas

- **JUnit 4.12** — framework de testes
- **Mockito 1.10.19** — mocking de dependências
- **`maven-surefire-plugin` 2.22.2** com `--add-opens java.base/java.lang=ALL-UNNAMED` — necessário para o CGLIB do Mockito legado funcionar no Java 17+

### Cobertura por classe

| Classe de teste              | Testes | O que cobre                                                                                                                             |
| ---------------------------- | ------ | --------------------------------------------------------------------------------------------------------------------------------------- |
| `FinalizarCompraServiceTest` | 5      | Sucesso com NF desativada/ativada, `ProdutoEsgotadoException` com preservação de nome, `SistemaOcupadoException` após retries esgotados |
| `ProdutoServiceTest`         | 8      | CRUD de produto, validação de código de barras duplicado, relatórios de estoque baixo e por categoria                                   |
| `ItemVendaServiceTest`       | 7      | `contemProduto`, `somaPrecos` (BigDecimal), `retornaItemVendaPeloCodigo`, `geraRelatorioItemVenda`, `criaItemVendaPorCodigoProduto`     |
| `VendaServiceTest`           | 8      | CRUD de venda, `retornaRelatorioVendas`, `retornaRelatorioVendasPorData`, `gerarResumoVenda`                                            |
| `CategoriaServiceTest`       | 2      | `retornaCategorias`, conversão para DTO                                                                                                 |
| **Total**                    | **30** |                                                                                                                                         |

### Nota sobre `FinalizarCompraServiceTest` e concorrência

O teste valida a **lógica de processamento**, não a colisão física entre threads. O mock do `FinalizarCompraRepository` é configurado para lançar `SistemaOcupadoException` diretamente, simulando o efeito final de múltiplas colisões consecutivas no banco. Isso é suficiente para garantir que o `FinalizarCompraService` mapeia a exceção corretamente para `ResultadoFinalizacao.SISTEMA_OCUPADO`. Testar a colisão em si exigiria um teste de integração multithread com banco real, sujeito a flakiness por não-determinismo no scheduling de threads.

---

## Pré-Requisitos

- JDK 8 ou superior
- MySQL 8.0 configurado e acessível
- Maven 3.x

## Como Executar

```bash
# 1. Clone o repositório
git clone https://github.com/queirogaraffael/self-checkout-retail-desktop.git
cd self-checkout-retail-desktop

# 2. Configure as credenciais do banco e a senha de administrador
# Crie um arquivo config.properties na raiz do projeto:
echo "db.user=seu_usuario" > config.properties
echo "db.password=sua_senha" >> config.properties
echo "senha.admin=sua_senha_admin" >> config.properties

# 3. Execute o script SQL (pasta database/) para criar o schema
# 4. Compile e execute
mvn compile exec:java -Dexec.mainClass="gerenciador.Main"
```

A senha de acesso ao Painel de Administração é definida pelo próprio usuário na propriedade `senha.admin` do arquivo `config.properties`.

## Imagens do Sistema

### Menu Principal

![Menu Principal](https://github.com/queirogaraffael/self-checkout/blob/main/assets/Menu%20Principal.png?raw=true)

### Painel de Administração

![Painel de Administração I](https://github.com/queirogaraffael/self-checkout/blob/main/assets/Painel%20Administracao%20I.png?raw=true)
![Painel de Administração II](https://github.com/queirogaraffael/self-checkout/blob/main/assets/Painel%20Administracao%20II.png?raw=true)

### Autoatendimento

![Autoatendimento](https://github.com/queirogaraffael/self-checkout/blob/main/assets/Autoatendimento.png?raw=true)

### Validação de Senha

![Validação de Senha](https://github.com/queirogaraffael/self-checkout/blob/main/assets/Validacao%20Senha.png?raw=true)

## Licença

Este projeto está licenciado sob a [Licença MIT](https://github.com/queirogaraffael/self-checkout/blob/main/LICENSE)