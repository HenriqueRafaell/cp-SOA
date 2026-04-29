package br.com.fiap.autoescola.client;

import br.com.fiap.autoescola.exception.RecursoNaoEncontradoException;
import br.com.fiap.autoescola.exception.RegraNegocioException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate;

    public ViaCepClient() {
        this.restTemplate = new RestTemplate();
    }

    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        if (cepLimpo.length() != 8) {
            throw new RegraNegocioException("CEP inválido: " + cep);
        }

        try {
            ViaCepResponse response = restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo);
            if (response == null || Boolean.TRUE.equals(response.erro())) {
                throw new RecursoNaoEncontradoException("CEP não encontrado: " + cep);
            }
            return response;
        } catch (RecursoNaoEncontradoException | RegraNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new RegraNegocioException("Erro ao consultar o CEP: " + e.getMessage());
        }
    }
}
