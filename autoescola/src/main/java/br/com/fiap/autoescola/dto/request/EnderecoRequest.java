package br.com.fiap.autoescola.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(
        @NotBlank String logradouro,
        String numero,
        String complemento,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @NotBlank @Size(min = 8, max = 9) String cep
) {}
