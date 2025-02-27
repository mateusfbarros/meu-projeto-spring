package com.example.meu_projeto_spring.controllers;

import com.example.meu_projeto_spring.dtos.ContaDTO;
import com.example.meu_projeto_spring.services.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/criar/{pessoaId}")
    public ResponseEntity<ContaDTO> criarConta(@PathVariable Long pessoaId) {
        return ResponseEntity.ok(contaService.criarConta(pessoaId));
    }

    @PostMapping("/depositar/{contaId}")
    public ResponseEntity<String> depositar(@PathVariable Long contaId, @RequestParam double valor) {
        if (contaService.depositar(contaId, valor)) {
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Erro ao realizar depósito.");
    }

    @PostMapping("/sacar/{contaId}")
    public ResponseEntity<String> sacar(@PathVariable Long contaId, @RequestParam double valor) {
        if (contaService.sacar(contaId, valor)) {
            return ResponseEntity.ok("Saque realizado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Saldo insuficiente ou erro.");
    }

    @GetMapping("/saldo/{contaId}")
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long contaId) {
        return ResponseEntity.ok(contaService.consultarSaldo(contaId));
    }

    @GetMapping("/transacoes/{contaId}")
    public ResponseEntity<List<Double>> consultarTransacoes(@PathVariable Long contaId) {
        return ResponseEntity.ok(contaService.consultarTransacoes(contaId));
    }
}
