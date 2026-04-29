package br.com.fiap.autoescola.dto.response;

import br.com.fiap.autoescola.model.Instrucao;
import br.com.fiap.autoescola.model.MotivoCancelamento;

import java.time.LocalDateTime;

public record InstrucaoResponse(
        Long id,
        Long alunoId,
        String alunoNome,
        Long instrutorId,
        String instrutorNome,
        LocalDateTime dataHora,
        boolean ativa,
        MotivoCancelamento motivoCancelamento,
        LocalDateTime dataCancelamento
) {
    public static InstrucaoResponse from(Instrucao i) {
        return new InstrucaoResponse(
                i.getId(),
                i.getAluno().getId(),
                i.getAluno().getNome(),
                i.getInstrutor().getId(),
                i.getInstrutor().getNome(),
                i.getDataHora(),
                i.isAtiva(),
                i.getMotivoCancelamento(),
                i.getDataCancelamento()
        );
    }
}
