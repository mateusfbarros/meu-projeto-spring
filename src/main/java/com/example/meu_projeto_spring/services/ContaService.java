package com.example.meu_projeto_spring.services;

import com.example.meu_projeto_spring.dtos.ContaDTO;
import com.example.meu_projeto_spring.entities.Conta;
import com.example.meu_projeto_spring.entities.Pessoa;
import com.example.meu_projeto_spring.repositories.ContaRepository;
import com.example.meu_projeto_spring.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final PessoaRepository pessoaRepository;

    // Mapa para armazenar as transações de cada conta
    private final Map<Long, Queue<Double>> transacoesPorConta = new HashMap<>();

    /**
     * Cria uma nova conta para uma pessoa existente.
     */
    public ContaDTO criarConta(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Conta conta = new Conta();
        conta.setPessoa(pessoa);
        conta.setSaldo(0.0);
        conta = contaRepository.save(conta);

        // Inicializa a fila de transações para essa conta
        transacoesPorConta.put(conta.getId(), new LinkedList<>());

        return converterParaDTO(conta);
    }

    /**
     * Deposita um valor na conta e armazena na fila de transações.
     */
    public boolean depositar(Long contaId, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }

        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        // Adiciona a transação à fila
        transacoesPorConta.get(contaId).add(valor);

        return true;
    }

    /**
     * Realiza um saque se houver saldo suficiente e armazena na fila de transações.
     */
    public boolean sacar(Long contaId, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }

        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            contaRepository.save(conta);

            // Armazena a transação como um valor negativo na fila
            transacoesPorConta.get(contaId).add(-valor);

            return true;
        }
        return false;
    }

    /**
     * Retorna o saldo atual da conta.
     */
    public double consultarSaldo(Long contaId) {
        return contaRepository.findById(contaId)
                .map(Conta::getSaldo)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    /**
     * Retorna o histórico de transações da conta.
     */
    public List<Double> consultarTransacoes(Long contaId) {
        if (!transacoesPorConta.containsKey(contaId)) {
            throw new RuntimeException("Conta não encontrada.");
        }
        return new ArrayList<>(transacoesPorConta.get(contaId));
    }

    /**
     * Converte uma entidade Conta para DTO.
     */
    private ContaDTO converterParaDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setPessoaId(conta.getPessoa().getId());
        dto.setSaldo(conta.getSaldo());
        return dto;
    }
}
