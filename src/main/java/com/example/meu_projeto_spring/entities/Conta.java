package com.example.meu_projeto_spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Entity
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pessoa pessoa;

    private double saldo;

    /*@Transient
    private Queue<Double> transacoes = new LinkedList<>();

    public void depositar(double valor) {
        transacoes.add(valor);
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (saldo >= valor) {
            transacoes.add(-valor);
            saldo -= valor;
            return true;
        }
        return false;
    }*/
}
