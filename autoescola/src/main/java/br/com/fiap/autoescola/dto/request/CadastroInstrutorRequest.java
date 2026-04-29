package br.com.fiap.autoescola.dto.request;

import br.com.fiap.autoescola.model.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroInstrutorRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String telefone,
        @NotBlank String cnh,
        @NotNull Especialidade especialidade,
        @NotNull @Valid EnderecoRequest endereco
) {}
