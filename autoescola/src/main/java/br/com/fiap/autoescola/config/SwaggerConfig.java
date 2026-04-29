package br.com.fiap.autoescola.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Auto-Escola")
                        .version("1.0")
                        .description("API REST para agendamento de instruções de auto-escola — Checkpoint 5 SOA e WebServices FIAP")
                        .contact(new Contact()
                                .name("FIAP")
                                .email("pf2308@fiap.com.br")));
    }
}
