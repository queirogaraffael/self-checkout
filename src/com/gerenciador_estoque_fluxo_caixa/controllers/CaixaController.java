package com.gerenciador_estoque_fluxo_caixa.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuFluxoCaixa;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.utils.GeradorNotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.views.FluxoDeCaixaView;

public class CaixaController {

	private NotaFiscal notaFiscal;
	private EntityManagerFactoryService entityManagerFactory;
	private CategoriaDao categoriaDao;
	private ItemVendaDao itemVendaDao;
	private ProdutoDao produtoDao;
	private VendaDao vendaDao;

	public CaixaController(NotaFiscal notaFiscal, EntityManagerFactoryService entityManagerFactory) {
		this.notaFiscal = notaFiscal;
		this.entityManagerFactory = entityManagerFactory;
	}

	public void fluxoDeCaixa() {

		String opcaoMenuFluxoDeCaixa = "";

		Set<ItemVenda> listaCompras = new HashSet<>();

		do {
			try {
				opcaoMenuFluxoDeCaixa = FluxoDeCaixaView.exibirMenuFluxoDeCaixa();

				switch (opcaoMenuFluxoDeCaixa) {

				case (ConstantesMenuFluxoCaixa.ADICIONAR_PRODUTO):
					adicionaProduto(listaCompras);
					break;

				case (ConstantesMenuFluxoCaixa.SACOLA_COMPRAS):
					listarSacola(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.PRODUTOS_EM_ESTOQUE):
					listarEstoque();

					break;

				case (ConstantesMenuFluxoCaixa.REMOVER_DA_SACOLA):

					removerProduto(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.ALTERAR_QUANTIDADE):

					modificarQuantidade(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.FINALIZAR_COMPRA):

					finalizarCompra(listaCompras, notaFiscal);
					break;

				case (ConstantesMenuFluxoCaixa.LIMPAR_SACOLA):

					limparCarrinho(listaCompras);
					break;

				case (ConstantesMenuFluxoCaixa.MENU_PRINCIPAL):

					opcaoMenuFluxoDeCaixa = sair();

					break;

				}
			} catch (NumberFormatException erro) {
				JOptionPane.showMessageDialog(null,
						"Entrada invalida. Por favor, insira um numero correspondente a� opcao desejada.");
			}

		} while (!opcaoMenuFluxoDeCaixa.equals(ConstantesMenuFluxoCaixa.MENU_PRINCIPAL));
	}

	private void adicionaProduto(Set<ItemVenda> listaCompras) {
		String codigoProduto = JOptionPane
				.showInputDialog("Digite o codigo do produto que voce deseja adicionar a lista de compras");

		if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {

			Integer novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog(
					"Produto ja adicionado anteriormente, digite a nova quantidade que voce deseja: "));

			ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);

			Produto produtoEstoque = produtoDao.retornaProdutoPorCodigo(codigoProduto);

			int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

			if (quantidadeRealProduto >= novaQuantidade) {

				prod.setQuantidade(novaQuantidade);
				produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

				produtoDao.atualizaProduto(produtoEstoque);

			} else if (quantidadeRealProduto <= 0) {
				JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
			}

			else {

				Object[] opcoes = { "Sim", "Nao" };

				int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
						"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

				if (opcao == 0) {

					prod.setQuantidade(quantidadeRealProduto);
					produtoEstoque.setQuantidade(0);

					produtoDao.atualizaProduto(produtoEstoque);

				} else {
					JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
				}
			}

		} else {

			Produto produto = produtoDao.retornaProdutoPorCodigo(codigoProduto);

			if (produto != null) {
				Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

				if (produto.getQuantidade() >= quantidade) {

					ItemVenda item = new ItemVenda(produto, quantidade);

					listaCompras.add(item);

					produto.setQuantidade(produto.getQuantidade() - quantidade);

					produtoDao.atualizaProduto(produto);

				} else if (produto.getQuantidade() == 0) {
					JOptionPane.showMessageDialog(null, "Quantidade em estoque do produto igual a 0. Tente outro!");
				}

				else {

					Object[] opcoes = { "Sim", "Nao" };

					int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
							"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

					if (opcao == 0) {

						ItemVenda item = new ItemVenda(produto, produto.getQuantidade());

						listaCompras.add(item);

						produto.setQuantidade(0);
						produtoDao.atualizaProduto(produto);

					} else {
						JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao consta no estoque. Tente outro!");

			}
		}
	}

	private void listarSacola(Set<ItemVenda> listaCompras) {

		String compras = ItemVendaService.geraRelatorioItemVenda(listaCompras);

		double subtotal = ItemVendaService.somaPrecos(listaCompras);
		String subtotalFormatado = String.format("Subtotal: %.2f R$", subtotal);
		String resultado = "Produtos da sacola de compras: \n" + compras + "\n" + subtotalFormatado;

		JOptionPane.showMessageDialog(null, resultado);

	}

	private void listarEstoque() {

		if (produtoDao.tabelaProdutoEstaVazia()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {

			int categoria = categoriaDao.retornaIdCategoria();

			String resultado = produtoDao.geraRelatotioProdutos(categoria);

			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void removerProduto(Set<ItemVenda> listaCompras) {

		if (listaCompras.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Adicione primeiro um produto a sua sacola para poder remover.");
		} else {
			String codigoProdutoParaRemover = JOptionPane
					.showInputDialog("Digite o codigo do produto que voce seja remover:");

			if (!ItemVendaService.contemProduto(listaCompras, codigoProdutoParaRemover)) {
				JOptionPane.showMessageDialog(null,
						"Produto ja nao constava na sacola. Tente novamente com uma produto existente.");

			} else {

				ItemVenda ItemListaCompras = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras,
						codigoProdutoParaRemover);

				Produto produtoDoEstoque = produtoDao.retornaProdutoPorCodigo(codigoProdutoParaRemover);
				int quantidadeRealProduto = ItemListaCompras.getQuantidade() + produtoDoEstoque.getQuantidade();

				produtoDoEstoque.setQuantidade(quantidadeRealProduto);

				produtoDao.atualizaProduto(produtoDoEstoque);
				listaCompras.remove(ItemListaCompras);

				JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
			}
		}
	}

	private void modificarQuantidade(Set<ItemVenda> listaCompras) {
		if (listaCompras.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
		} else {
			String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

			if (ItemVendaService.contemProduto(listaCompras, codigo)) {
				Integer novaQuantidade = Integer
						.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

				ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);

				Produto produtoEstoque = produtoDao.retornaProdutoPorCodigo(codigo);

				int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

				if (quantidadeRealProduto >= novaQuantidade) {

					prod.setQuantidade(novaQuantidade);
					produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

					produtoDao.atualizaProduto(produtoEstoque);

				} else if (quantidadeRealProduto <= 0) {
					JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
				}

				else {

					Object[] opcoes = { "Sim", "Nao" };

					int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
							"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

					if (opcao == 0) {

						prod.setQuantidade(quantidadeRealProduto);
						produtoEstoque.setQuantidade(0);

						produtoDao.atualizaProduto(produtoEstoque);

					} else {
						JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
					}
				}

				JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Produto invalido, tente outro!");
			}
		}
	}

	private void finalizarCompra(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
		if (!listaCompras.isEmpty()) {

			Venda venda = new Venda();
			venda.setDataHora(LocalDateTime.now());

			vendaDao.adicionaVenda(venda);

			Double total = 0.0;

			for (ItemVenda itemVenda : listaCompras) {
				itemVenda.setVenda(venda);
				total += itemVenda.subTotal();
				itemVendaDao.adicionaItemVenda(itemVenda);
			}

			venda.setTotal(total);

			vendaDao.atualizaVenda(venda);

			if (notaFiscal.getStatusNotaFiscal()) {
				GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
			}

			listaCompras.clear();

			JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");
		}
	}

	private void limparCarrinho(Set<ItemVenda> listaCompras) {
		if (!listaCompras.isEmpty()) {
			for (ItemVenda item : listaCompras) {

				Produto produtoEmEstoque = produtoDao.retornaProdutoPorCodigo(item.getProduto().getCodigoDeBarra());

				int quantidadeReal = item.getQuantidade() + produtoEmEstoque.getQuantidade();

				produtoEmEstoque.setQuantidade(quantidadeReal);

				produtoDao.atualizaProduto(produtoEmEstoque);

			}
			listaCompras.clear();
			JOptionPane.showMessageDialog(null, "Sacola de compras limpada com sucesso.");

		}
	}

	private String sair() {

		String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
		boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

		if (!autenticacao) {
			JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
			return ConstantesMenuFluxoCaixa.CONTINUAR_NO_PROGRAMA;
		}
		return ConstantesMenuFluxoCaixa.MENU_PRINCIPAL;

	}

}
