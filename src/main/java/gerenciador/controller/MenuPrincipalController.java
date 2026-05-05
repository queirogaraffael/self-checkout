package gerenciador.controller;

import gerenciador.constant.MenuPrincipalConstant;
import gerenciador.infrastructure.JPAManager;
import gerenciador.view.menu.MenuPrincipalView;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.util.AutenticadorDeSenha;

public class MenuPrincipalController {

    private final JPAManager entityManagerFactoryService;
    private final EstoqueController estoqueController;
    private final AutoatendimentoController autoatendimentoController;

    public MenuPrincipalController(JPAManager entityManagerFactoryService, EstoqueController estoqueController, AutoatendimentoController autoatendimentoController) {
        this.entityManagerFactoryService = entityManagerFactoryService;
        this.estoqueController = estoqueController;
        this.autoatendimentoController = autoatendimentoController;
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

                    case MenuPrincipalConstant.AUTOATENDIMENTO:
                        iniciarAutoatendimento();
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

    private void iniciarAutoatendimento() {
        autoatendimentoController.fluxoDeCaixa();
    }

}

