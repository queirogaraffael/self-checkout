package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuPrincipal;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.views.MenuPrincipalControllerView;
import com.gerenciador_estoque_fluxo_caixa.views.ValidaSenha;

public class MenuPrincipalController {

    private final EntityManagerFactoryService entityManagerFactory;
    private final EstoqueController estoqueController;
    private final CaixaController caixaController;

    public MenuPrincipalController(EntityManagerFactoryService entityManagerFactory, NotaFiscal notaFiscal) {
        this.entityManagerFactory = entityManagerFactory;

        this.estoqueController = new EstoqueController(notaFiscal, entityManagerFactory);
        this.caixaController = new CaixaController(notaFiscal, entityManagerFactory);
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
            entityManagerFactory.fechaEntityManagerFactory();
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

