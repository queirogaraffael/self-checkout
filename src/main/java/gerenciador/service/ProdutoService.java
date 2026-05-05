package gerenciador.service;

import gerenciador.dto.produto.*;
import gerenciador.infrastructure.repository.ProdutoRepository;
import gerenciador.model.Produto;

import java.util.List;

public class ProdutoService {

    private final ProdutoRepository produtoDao;

    public ProdutoService(ProdutoRepository produtoDao) {
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

    public boolean existeProdutoPorCodigo(String codigo) {
        return produtoDao.retornaProdutoPorCodigo(codigo) != null;
    }

    public int retornaQuantidadeAtual(String codigo) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);
        return produto != null ? produto.getQuantidade() : 0;
    }

    public void decrementaEstoque(String codigo, int quantidade) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoDao.atualizaProduto(produto);
    }

    public void incrementaEstoque(String codigo, int quantidade) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);
        produto.setQuantidade(produto.getQuantidade() + quantidade);
        produtoDao.atualizaProduto(produto);
    }

    private String geraRelatorio(List<?> produtos){
        StringBuilder sb = new StringBuilder();

        for (Object produto : produtos) {
            sb.append(produto).append("\n");
        }
        return sb.toString();
    }

}
