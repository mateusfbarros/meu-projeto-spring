package com.example.meu_projeto_spring.repositories;

import com.example.meu_projeto_spring.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
