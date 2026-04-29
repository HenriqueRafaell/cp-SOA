package br.com.fiap.autoescola.dto.request;

import jakarta.validation.Valid;

public record AtualizacaoAlunoRequest(
        String nome,
        String telefone,
        @Valid EnderecoRequest endereco
) {}
