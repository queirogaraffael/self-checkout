package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.*;
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

    public ProdutoDTO retornaProdutoDTO(String codigo){
        return produtoDao.retornaProdutoDTOPorCodigo(codigo);
    }

    public boolean haProduto() {
        return produtoDao.haProduto();
    }

    public String geraRelatorioProdutosPorCategoria(int idCategoria) {
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

    public boolean atualizaPrecoProduto(String codigo, ProdutoAtualizarPrecoDTO atualizarPrecoDTO) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);
        produto.setPreco(atualizarPrecoDTO.getPreco());

        return produtoDao.atualizaProduto(produto);

    }

    public ProdutoAtualizarQuantidadeDTO retornaProdutoAtualizarQuantidadeDTO(String codigoBarra){
        return produtoDao.retornaProdutoAtualizarQuantidadeDTO(codigoBarra);
    }

    public boolean atualizaQuantidadeProduto(String codigo, ProdutoAtualizarQuantidadeDTO atualizarQuantidadeDTO) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);
        produto.setQuantidade(atualizarQuantidadeDTO.getQuantidade());

        return produtoDao.atualizaProduto(produto);

    }


    private String geraRelatorio(List<?> produtos){
        StringBuilder sb = new StringBuilder();

        for (Object produto : produtos) {
            sb.append(produto).append("\n");
        }
        return sb.toString();
    }

}
