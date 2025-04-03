package com.example.teste_POL.service;

import com.example.teste_POL.model.Processo;
import com.example.teste_POL.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessoServiceTest {

    @Mock
    private ProcessoRepository processoRepository;

    @InjectMocks
    private ProcessoService processoService;

    private Processo processo;

    @BeforeEach
    void setUp() {
        processo = new Processo();
        processo.setId(1L);
        processo.setNumeroProcesso("12345");
        processo.setStatus("Aberto");
        processo.setDescricao("Processo em andamento");
    }

    @Test
    void salvarProcesso_DeveSalvarComSucesso() {
        when(processoRepository.existsByNumeroProcesso(processo.getNumeroProcesso())).thenReturn(false);
        when(processoRepository.save(processo)).thenReturn(processo);

        Processo salvo = processoService.salvarProcesso(processo);

        assertNotNull(salvo);
        assertEquals("12345", salvo.getNumeroProcesso());
        verify(processoRepository, times(1)).save(processo);
    }

    @Test
    void salvarProcesso_DeveLancarExcecao_SeNumeroProcessoJaExiste() {
        when(processoRepository.existsByNumeroProcesso(processo.getNumeroProcesso())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> processoService.salvarProcesso(processo));
    }

    @Test
    void listarTodos_DeveRetornarListaDeProcessos() {
        when(processoRepository.findAll()).thenReturn(List.of(processo));

        List<Processo> processos = processoService.listarTodos();

        assertFalse(processos.isEmpty());
        assertEquals(1, processos.size());
    }

    @Test
    void buscarPorId_DeveRetornarProcessoSeEncontrado() {
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processo));

        Optional<Processo> encontrado = processoService.buscarPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals(1L, encontrado.get().getId());
    }

    @Test
    void atualizar_DeveAtualizarProcessoSeEncontrado() {
        Processo novoProcesso = new Processo();
        novoProcesso.setNumeroProcesso("54321");
        novoProcesso.setStatus("Concluído");
        novoProcesso.setDescricao("Processo finalizado");

        when(processoRepository.findById(1L)).thenReturn(Optional.of(processo));
        when(processoRepository.save(any(Processo.class))).thenReturn(novoProcesso);

        Processo atualizado = processoService.atualizar(1L, novoProcesso);

        assertEquals("54321", atualizado.getNumeroProcesso());
        assertEquals("Concluído", atualizado.getStatus());
    }

    @Test
    void atualizar_DeveLancarExcecaoSeProcessoNaoEncontrado() {
        when(processoRepository.findById(2L)).thenReturn(Optional.empty());

        Processo novoProcesso = new Processo();

        assertThrows(ResponseStatusException.class, () -> processoService.atualizar(2L, novoProcesso));
    }
}
