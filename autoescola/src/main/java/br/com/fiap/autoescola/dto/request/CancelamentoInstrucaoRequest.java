package br.com.fiap.autoescola.dto.request;

import br.com.fiap.autoescola.model.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record CancelamentoInstrucaoRequest(
        @NotNull MotivoCancelamento motivo
) {}
