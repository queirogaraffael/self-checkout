package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuPrincipal;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.dao.*;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.views.MenuPrincipalControllerView;

import javax.swing.*;

public class MenuPrincipalController {
    private final EntityManagerFactoryService entityManagerFactoryService;

    private EstoqueController estoqueController;
    private CaixaController caixaController;
    private NotaFiscal notaFiscal;
    private CategoriaDao categoriaDao;
    private ItemVendaDao itemVendaDao;
    private ProdutoDao produtoDao;
    private VendaDao vendaDao;

    public MenuPrincipalController(EntityManagerFactoryService entityManagerFactoryService) {
        this.entityManagerFactoryService = entityManagerFactoryService;

        this.notaFiscal = new NotaFiscal();

        this.categoriaDao = DaoFactory.createCategoriaDao();
        this.itemVendaDao = DaoFactory.createItemVendaDao();
        this.produtoDao = DaoFactory.createProdutoDao();
        this.vendaDao = DaoFactory.createVendaDao();

        this.estoqueController = new EstoqueController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);
        this.caixaController = new CaixaController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);

    }

    public void exibirMenuPrincipal() {
        int opcaoMenuPrincipal;

        try {
            do {

                opcaoMenuPrincipal = MenuPrincipalControllerView.exibeViewMenuPrincipal();

                switch (opcaoMenuPrincipal) {
                    case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
                        String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
                        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

                        if (autenticacao) {

                            estoqueController.gerenciadorEstoque();
                        } else {
                            JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
                        }

                        break;
                    case (ConstantesMenuPrincipal.FLUXO_CAIXA):
                        caixaController.fluxoDeCaixa();
                        break;
                    default:
                        break;
                }
            } while (opcaoMenuPrincipal != ConstantesMenuPrincipal.ENCERRAR_PROGRAMA);

        } finally {
            entityManagerFactoryService.fechaEntityManagerFactory();
        }
    }
}
