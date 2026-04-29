package br.com.fiap.autoescola.repository;

import br.com.fiap.autoescola.model.Instrucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InstrucaoRepository extends JpaRepository<Instrucao, Long> {

    @Query("""
        SELECT COUNT(i) FROM Instrucao i
        WHERE i.aluno.id = :alunoId
        AND i.ativa = true
        AND CAST(i.dataHora AS date) = :data
    """)
    long countInstrucoesByAlunoAndData(@Param("alunoId") Long alunoId, @Param("data") LocalDate data);

    boolean existsByInstrutorIdAndDataHoraAndAtivaTrue(Long instrutorId, LocalDateTime dataHora);
}
