package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuPrincipal;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.ui.MenuPrincipalControllerView;
import com.gerenciador_estoque_fluxo_caixa.ui.ValidaSenha;

public class MenuPrincipalController {

    private final EntityManagerFactoryService entityManagerFactoryService;
    private final EstoqueController estoqueController;
    private final CaixaController caixaController;

    public MenuPrincipalController(EstoqueController estoqueController, CaixaController caixaController, EntityManagerFactoryService entityManagerFactoryService) {
        this.estoqueController = estoqueController;
        this.caixaController = caixaController;
        this.entityManagerFactoryService = entityManagerFactoryService;
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

