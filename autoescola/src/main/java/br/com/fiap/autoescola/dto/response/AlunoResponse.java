package br.com.fiap.autoescola.dto.response;

import br.com.fiap.autoescola.model.Aluno;

public record AlunoResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        boolean ativo
) {
    public static AlunoResponse from(Aluno a) {
        return new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getCpf(), a.isAtivo());
    }
}
