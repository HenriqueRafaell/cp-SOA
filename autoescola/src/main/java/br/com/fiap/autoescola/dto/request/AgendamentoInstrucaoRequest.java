package br.com.fiap.autoescola.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoInstrucaoRequest(
        @NotNull Long alunoId,
        Long instrutorId, // opcional
        @NotNull LocalDateTime dataHora
) {}
