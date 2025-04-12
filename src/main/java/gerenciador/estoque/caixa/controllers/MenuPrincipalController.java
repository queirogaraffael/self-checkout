package main.java.gerenciador.estoque.caixa.controllers;

import main.java.gerenciador.estoque.caixa.constantes.ConstantesMenuPrincipal;
import main.java.gerenciador.estoque.caixa.hibernateConnection.EntityManagerFactoryService;
import main.java.gerenciador.estoque.caixa.ui.MenuPrincipalControllerView;
import main.java.gerenciador.estoque.caixa.ui.ValidaSenha;
import main.java.gerenciador.estoque.caixa.utils.AutenticadorDeSenha;

public class MenuPrincipalController {

    private final EntityManagerFactoryService entityManagerFactoryService;
    private final EstoqueController estoqueController;
    private final CaixaController caixaController;

    public MenuPrincipalController(EntityManagerFactoryService entityManagerFactoryService, EstoqueController estoqueController, CaixaController caixaController) {
        this.entityManagerFactoryService = entityManagerFactoryService;
        this.estoqueController = estoqueController;
        this.caixaController = caixaController;
    }

    public void exibirMenuPrincipal() {
        int opcaoMenuPrincipal;

        try {
            do {
                opcaoMenuPrincipal = MenuPrincipalControllerView.exibeViewMenuPrincipal();

                switch (opcaoMenuPrincipal) {
                    case ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE:
                        processarGerenciadorEstoque();
                        break;

                    case ConstantesMenuPrincipal.FLUXO_CAIXA:
                        processarFluxoCaixa();
                        break;

                    case ConstantesMenuPrincipal.ENCERRAR_PROGRAMA:
                        break;

                    default:
                        opcaoMenuPrincipal = ConstantesMenuPrincipal.ENCERRAR_PROGRAMA;
                        break;
                }
            } while (opcaoMenuPrincipal != ConstantesMenuPrincipal.ENCERRAR_PROGRAMA);

        } finally {
            entityManagerFactoryService.fechaEntityManagerFactory();
        }
    }

    private void processarGerenciadorEstoque() {
        String senhaDigitada = ValidaSenha.exibirValidaSenha();
        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (autenticacao) {
            estoqueController.gerenciadorEstoque();
        } else {
            ValidaSenha.exibirSenhaIncorreta();
        }
    }

    private void processarFluxoCaixa() {
        caixaController.fluxoDeCaixa();
    }

}

