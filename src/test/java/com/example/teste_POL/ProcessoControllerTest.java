package com.example.teste_POL.controller;

import com.example.teste_POL.model.Processo;
import com.example.teste_POL.service.ProcessoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(ProcessoController.class)
class ProcessoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;

    @Test
    void listarTodos_DeveRetornarListaDeProcessos() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("123");
        Mockito.when(processoService.listarTodos()).thenReturn(Arrays.asList(processo));

        mockMvc.perform(get("/processos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroProcesso").value("123"));
    }

    @Test
    void buscarPorNumero_DeveRetornarProcesso_QuandoExiste() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("123");
        Mockito.when(processoService.buscarPorNumero("123")).thenReturn(Optional.of(processo));

        mockMvc.perform(get("/processos/numero/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroProcesso").value("123"));
    }

    @Test
    void buscarPorNumero_DeveRetornarNotFound_QuandoNaoExiste() throws Exception {
        Mockito.when(processoService.buscarPorNumero("999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/processos/numero/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void criarProcesso_DeveRetornarStatusOk_QuandoSucesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("123");
        Mockito.when(processoService.salvarProcesso(Mockito.any(Processo.class))).thenReturn(processo);

        mockMvc.perform(post("/processos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroProcesso\":\"123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deletarProcesso_DeveRetornarNoContent_QuandoSucesso() throws Exception {
        Mockito.when(processoService.existeNumeroProcesso("123")).thenReturn(true);
        Mockito.doNothing().when(processoService).deletarProcesso("123");

        mockMvc.perform(delete("/processos/numero/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void atualizarProcesso_DeveRetornarStatusOk_QuandoSucesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("123");
        Mockito.when(processoService.atualizar(Mockito.eq(1L), Mockito.any(Processo.class)))
                .thenReturn(processo);

        mockMvc.perform(put("/processos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroProcesso\":\"123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroProcesso").value("123"));
    }
}
