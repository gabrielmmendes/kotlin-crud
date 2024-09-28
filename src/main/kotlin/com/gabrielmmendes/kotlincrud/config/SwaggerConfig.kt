package com.gabrielmmendes.kotlincrud.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("API protótipo com operações CRUD de paciente")
                    .description("Documentação da API com OpenAPI 3")
                    .version("v1.0.0")
                    .contact(Contact().name("Gabriel M Mendes").url("https://github.com/gabrielmmendes").email("gabrielmatos@discente.ufg.br"))
                    .license(License().name("Licença MIT").url("https://opensource.org/licenses/MIT"))
            )
    }
}