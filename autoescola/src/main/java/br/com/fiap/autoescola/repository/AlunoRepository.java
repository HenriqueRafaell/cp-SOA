package br.com.fiap.autoescola.repository;

import br.com.fiap.autoescola.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Page<Aluno> findAllByAtivoTrueOrderByNomeAsc(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
