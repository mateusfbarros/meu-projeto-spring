package com.example.meu_projeto_spring.repositories;

import com.example.meu_projeto_spring.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
