package br.com.fiap.autoescola.controller;

import br.com.fiap.autoescola.dto.request.AgendamentoInstrucaoRequest;
import br.com.fiap.autoescola.dto.request.CancelamentoInstrucaoRequest;
import br.com.fiap.autoescola.dto.response.InstrucaoResponse;
import br.com.fiap.autoescola.service.InstrucaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrucoes")
@RequiredArgsConstructor
@Tag(name = "Instruções", description = "Agendamento e cancelamento de instruções")
public class InstrucaoController {

    private final InstrucaoService service;

    @PostMapping
    @Operation(summary = "Agendar instrução")
    public ResponseEntity<InstrucaoResponse> agendar(@RequestBody @Valid AgendamentoInstrucaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agendar(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas as instruções")
    public ResponseEntity<List<InstrucaoResponse>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instrução por ID")
    public ResponseEntity<InstrucaoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar instrução")
    public ResponseEntity<InstrucaoResponse> cancelar(@PathVariable Long id,
                                                       @RequestBody @Valid CancelamentoInstrucaoRequest request) {
        return ResponseEntity.ok(service.cancelar(id, request));
    }
}
