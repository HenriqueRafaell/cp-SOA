package br.com.fiap.autoescola.controller;

import br.com.fiap.autoescola.client.ViaCepClient;
import br.com.fiap.autoescola.client.ViaCepResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
@RequiredArgsConstructor
@Tag(name = "CEP", description = "Consulta de endereço via API externa ViaCEP")
public class CepController {

    private final ViaCepClient viaCepClient;

    @GetMapping("/{cep}")
    @Operation(summary = "Buscar endereço pelo CEP usando a API ViaCEP")
    public ResponseEntity<ViaCepResponse> buscarPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(viaCepClient.buscarEnderecoPorCep(cep));
    }
}
