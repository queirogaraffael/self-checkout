package gerenciador.controller;

import gerenciador.constant.MenuPrincipalConstant;
import gerenciador.infrastructure.EntityManagerFactoryService;
import gerenciador.view.menu.MenuPrincipalView;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.util.AutenticadorDeSenha;

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
                opcaoMenuPrincipal = MenuPrincipalView.exibeViewMenuPrincipal();

                switch (opcaoMenuPrincipal) {
                    case MenuPrincipalConstant.GERENCIADOR_ESTOQUE:
                        processarGerenciadorEstoque();
                        break;

                    case MenuPrincipalConstant.FLUXO_CAIXA:
                        processarFluxoCaixa();
                        break;

                    case MenuPrincipalConstant.ENCERRAR_PROGRAMA:
                        break;

                    default:
                        opcaoMenuPrincipal = MenuPrincipalConstant.ENCERRAR_PROGRAMA;
                        break;
                }
            } while (opcaoMenuPrincipal != MenuPrincipalConstant.ENCERRAR_PROGRAMA);

        } finally {
            entityManagerFactoryService.fechaEntityManagerFactory();
        }
    }

    private void processarGerenciadorEstoque() {
        String senhaDigitada = ValidaSenhaView.exibirValidaSenha();
        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (autenticacao) {
            estoqueController.gerenciadorEstoque();
        } else {
            ValidaSenhaView.exibirSenhaIncorreta();
        }
    }

    private void processarFluxoCaixa() {
        caixaController.fluxoDeCaixa();
    }

}

