package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

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

    public String geraRelatotioProdutos(int categoria) {
        return "";
    }

    public String geraRelatorioProdutosEstoqueBaixo() {
        return "";
    }

    public boolean haProdutoComMesmoCodigoBarra(String codigoBarra) {
        return produtoDao.haProdutoComMesmoCodigoBarra(codigoBarra);

    }
}
