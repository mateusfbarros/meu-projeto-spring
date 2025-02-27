package com.example.meu_projeto_spring.services;

import com.example.meu_projeto_spring.dtos.ProdutoDTO;
import com.example.meu_projeto_spring.entities.Produto;
import com.example.meu_projeto_spring.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        return produtoRepository.save(produto);
    }
}