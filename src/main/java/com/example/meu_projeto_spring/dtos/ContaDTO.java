package com.example.meu_projeto_spring.dtos;

import lombok.Data;

@Data
public class ContaDTO {
    private Long id;
    private Long pessoaId;
    private double saldo;
}
