package br.com.fiap.autoescola.controller;

import br.com.fiap.autoescola.dto.request.AtualizacaoAlunoRequest;
import br.com.fiap.autoescola.dto.request.CadastroAlunoRequest;
import br.com.fiap.autoescola.dto.response.AlunoResponse;
import br.com.fiap.autoescola.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoController {

    private final AlunoService service;

    @PostMapping
    @Operation(summary = "Cadastrar aluno")
    public ResponseEntity<AlunoResponse> cadastrar(@RequestBody @Valid CadastroAlunoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    @GetMapping
    @Operation(summary = "Listar alunos ativos")
    public ResponseEntity<Page<AlunoResponse>> listar(@RequestParam(defaultValue = "0") int pagina) {
        return ResponseEntity.ok(service.listar(pagina));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    public ResponseEntity<AlunoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id,
                                                    @RequestBody @Valid AtualizacaoAlunoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar aluno (exclusão lógica)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
