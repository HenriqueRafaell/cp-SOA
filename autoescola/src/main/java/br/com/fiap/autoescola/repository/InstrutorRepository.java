package br.com.fiap.autoescola.repository;

import br.com.fiap.autoescola.model.Instrutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Page<Instrutor> findAllByAtivoTrueOrderByNomeAsc(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCnh(String cnh);

    @Query("""
        SELECT i FROM Instrutor i
        WHERE i.ativo = true
        AND i.id NOT IN (
            SELECT ins.instrutor.id FROM Instrucao ins
            WHERE ins.ativa = true AND ins.dataHora = :dataHora
        )
    """)
    List<Instrutor> findInstrutoresDisponiveisPorHorario(@Param("dataHora") LocalDateTime dataHora);
}
