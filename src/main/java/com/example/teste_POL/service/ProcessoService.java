package com.example.teste_POL.service;

import com.example.teste_POL.model.Processo;
import com.example.teste_POL.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    public Processo salvarProcesso(Processo processo) {
        if (processoRepository.existsByNumeroProcesso(processo.getNumeroProcesso())) {
            throw new IllegalArgumentException("Número de processo já cadastrado.");
        }
        return processoRepository.save(processo);
    }

    public List<Processo> listarTodos() {
        return processoRepository.findAll();
    }

    public Optional<Processo> buscarPorId(Long id) {
        return processoRepository.findById(id);
    }

    public Optional<Processo> buscarPorNumero(String numeroProcesso) {
        return processoRepository.findByNumeroProcesso(numeroProcesso);
    }

    public boolean existeNumeroProcesso(String numeroProcesso) {
        return processoRepository.existsByNumeroProcesso(numeroProcesso);
    }

    public void deletarPorId(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new IllegalArgumentException("Processo não encontrado.");
        }
        processoRepository.deleteById(id);
    }

    public void deletarProcesso(String numeroProcesso) {
        Optional<Processo> processoOpt = processoRepository.findByNumeroProcesso(numeroProcesso);
        if (processoOpt.isPresent()) {
            processoRepository.delete(processoOpt.get());
        } else {
            throw new IllegalArgumentException("Processo não encontrado.");
        }
    }

    public Processo atualizar(Long id, Processo novoProcesso) {
        Processo processoExistente = processoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado"));

        if (novoProcesso.getNumeroProcesso() != null) {
        processoExistente.setNumeroProcesso(novoProcesso.getNumeroProcesso());
        }
        if (novoProcesso.getStatus() != null) {
        processoExistente.setStatus(novoProcesso.getStatus());
        }
        if (novoProcesso.getDescricao() != null) {
        processoExistente.setDescricao(novoProcesso.getDescricao());
        }

        return processoRepository.save(processoExistente);
    }

}
