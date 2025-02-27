package com.example.meu_projeto_spring.controllers;

import com.example.meu_projeto_spring.services.ProdutoService;
import com.example.meu_projeto_spring.dtos.ProdutoDTO;
import com.example.meu_projeto_spring.entities.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.meu_projeto_spring.repositories.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody ProdutoDTO produtoDTO) {
        Produto novoProduto = produtoService.salvarProduto(produtoDTO);
        return ResponseEntity.ok(novoProduto);
    }

    @GetMapping
    public List<ProdutoDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()))
                .collect(Collectors.toList());
    }
}
