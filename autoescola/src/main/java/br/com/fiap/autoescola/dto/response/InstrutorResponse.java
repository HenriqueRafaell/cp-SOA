package br.com.fiap.autoescola.dto.response;

import br.com.fiap.autoescola.model.Endereco;
import br.com.fiap.autoescola.model.Especialidade;
import br.com.fiap.autoescola.model.Instrutor;

public record InstrutorResponse(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade,
        boolean ativo
) {
    public static InstrutorResponse from(Instrutor i) {
        return new InstrutorResponse(i.getId(), i.getNome(), i.getEmail(), i.getCnh(), i.getEspecialidade(), i.isAtivo());
    }
}
