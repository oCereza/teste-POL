package com.example.teste_POL.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "processo")
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @Column(nullable = false, unique = true)
    private String numeroProcesso;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @Column(nullable = false)
    private String status;

    public Processo() {
        this.dataCriacao = LocalDateTime.now(); 
    }

    public Processo(String numeroProcesso, String descricao, String status) {
        this.numeroProcesso = numeroProcesso;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = LocalDateTime.now(); 
    }

    @PrePersist
    protected void onCreate() {
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
