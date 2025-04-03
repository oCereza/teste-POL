package com.example.teste_POL.controller;

import com.example.teste_POL.model.Processo;
import com.example.teste_POL.service.ProcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService processoService;

    public ProcessoController(ProcessoService processoService) {
        this.processoService = processoService;
    }

    @GetMapping
    public ResponseEntity<List<Processo>> listarTodos() {
        List<Processo> processos = processoService.listarTodos();
        return ResponseEntity.ok(processos);
    }

    @GetMapping("/existe/{numeroProcesso}")
    public ResponseEntity<String> verificarSeExiste(@PathVariable String numeroProcesso) {
        if (processoService.existeNumeroProcesso(numeroProcesso)) {
            return ResponseEntity.ok("Processo encontrado.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/numero/{numeroProcesso}")
    public ResponseEntity<Processo> buscarPorNumero(@PathVariable String numeroProcesso) {
        return processoService.buscarPorNumero(numeroProcesso)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarProcesso(@RequestBody Processo processo) {
        if (processoService.existeNumeroProcesso(processo.getNumeroProcesso())) {
            return ResponseEntity.status(409).body("Já existe um processo com este número.");
        }
        Processo novoProcesso = processoService.salvarProcesso(processo);
        return ResponseEntity.ok(novoProcesso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarPorId(@PathVariable Long id) {
        return processoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/numero/{numeroProcesso}")
    public ResponseEntity<Void> deletarProcesso(@PathVariable String numeroProcesso) {
        if (!processoService.existeNumeroProcesso(numeroProcesso)) {
            return ResponseEntity.notFound().build();
        }
        processoService.deletarProcesso(numeroProcesso);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizarProcesso(@PathVariable Long id, @RequestBody Processo processo) {
        Processo atualizado = processoService.atualizar(id, processo);
        return ResponseEntity.ok(atualizado);
    }
}
