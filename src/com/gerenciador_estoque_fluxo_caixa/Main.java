package com.gerenciador_estoque_fluxo_caixa;

import com.gerenciador_estoque_fluxo_caixa.controllers.CaixaController;
import com.gerenciador_estoque_fluxo_caixa.controllers.EstoqueController;
import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;

public class Main {
	public static void main(String[] args) {
		final EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();

		final NotaFiscal notaFiscal = new NotaFiscal();

		final ItemVendaService itemVendaService = new ItemVendaService(entityManagerFactoryService.entityManagerFactory());
		final CategoriaService categoriaService = new CategoriaService(entityManagerFactoryService.entityManagerFactory());
		final ProdutoService produtoService = new ProdutoService(entityManagerFactoryService.entityManagerFactory());
		final VendaService vendaService = new VendaService(entityManagerFactoryService.entityManagerFactory());

		final EstoqueController estoqueController = new EstoqueController(notaFiscal, itemVendaService, categoriaService, produtoService, vendaService);
		final CaixaController caixaController = new CaixaController(notaFiscal, itemVendaService, categoriaService, produtoService, vendaService);

		MenuPrincipalController controller = new MenuPrincipalController(estoqueController,caixaController, entityManagerFactoryService);
		controller.exibirMenuPrincipal();
	}
}
