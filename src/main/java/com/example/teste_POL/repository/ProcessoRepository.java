package com.example.teste_POL.repository;

import com.example.teste_POL.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Optional<Processo> findByNumeroProcesso(String numeroProcesso);
    boolean existsByNumeroProcesso(String numeroProcesso);
}
