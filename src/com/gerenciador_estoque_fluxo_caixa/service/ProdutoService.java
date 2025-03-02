package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoBaixoEstoqueResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

import java.util.List;

public class ProdutoService {

    private final ProdutoDao produtoDao;

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public ProdutoCreateDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO) {
        return produtoDao.adicionaProduto(produtoCreateDTO);
    }

    public ProdutoDTO retornaProdutoPorCodigo(String codigoBarra) {
        return produtoDao.retornaProdutoPorCodigo(codigoBarra);
    }

    public void atualizaProduto(Produto produto) {
    }

    public boolean tabelaProdutoEstaVazia() {
        return false;
    }

    public String geraRelatotioProdutos(int idCategoria) {

        List<ProdutoResponseDTO> produtos = produtoDao.retornaProdutosPorCategoria(idCategoria);

        return geraRelatorio(produtos);

    }

    public String geraRelatorioProdutosEstoqueBaixo() {
        List<ProdutoBaixoEstoqueResponseDTO> produtos = produtoDao.retornaProdutosEstoqueBaixo();
        return geraRelatorio(produtos);
    }

    public boolean haProdutoComMesmoCodigoBarra(String codigoBarra) {
        return produtoDao.haProdutoComMesmoCodigoBarra(codigoBarra);

    }

    private String geraRelatorio(List<?> produtos){
        StringBuilder sb = new StringBuilder();

        for (Object produto : produtos) {
            sb.append(produto).append("\n");
        }
        return sb.toString();
    }
}
