package com.example.meu_projeto_spring.repositories;

import com.example.meu_projeto_spring.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
