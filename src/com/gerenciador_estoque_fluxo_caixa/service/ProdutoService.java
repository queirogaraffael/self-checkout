package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.factory.DaoFactory;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ProdutoDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class ProdutoService {

    private final ProdutoDao produtoDao;

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produtoCreateDTO) {
        return produtoDao.adicionaProduto(produtoCreateDTO);
    }

}
