package br.com.fiap.autoescola.controller;

import br.com.fiap.autoescola.dto.request.AtualizacaoInstrutorRequest;
import br.com.fiap.autoescola.dto.request.CadastroInstrutorRequest;
import br.com.fiap.autoescola.dto.response.InstrutorResponse;
import br.com.fiap.autoescola.service.InstrutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrutores")
@RequiredArgsConstructor
@Tag(name = "Instrutores", description = "Gerenciamento de instrutores")
public class InstrutorController {

    private final InstrutorService service;

    @PostMapping
    @Operation(summary = "Cadastrar instrutor")
    public ResponseEntity<InstrutorResponse> cadastrar(@RequestBody @Valid CadastroInstrutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    @GetMapping
    @Operation(summary = "Listar instrutores ativos")
    public ResponseEntity<Page<InstrutorResponse>> listar(@RequestParam(defaultValue = "0") int pagina) {
        return ResponseEntity.ok(service.listar(pagina));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instrutor por ID")
    public ResponseEntity<InstrutorResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar instrutor")
    public ResponseEntity<InstrutorResponse> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid AtualizacaoInstrutorRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar instrutor (exclusão lógica)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
