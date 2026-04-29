package br.com.fiap.autoescola.dto.request;

import jakarta.validation.Valid;

public record AtualizacaoInstrutorRequest(
        String nome,
        String telefone,
        @Valid EnderecoRequest endereco
) {}
