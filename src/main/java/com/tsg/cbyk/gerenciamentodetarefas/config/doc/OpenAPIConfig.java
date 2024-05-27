package com.tsg.cbyk.gerenciamentodetarefas.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("tsglinglani@gmail.com");
        contact.setName("Thiago S. Glinglani");

        Info info = new Info()
                .title("CBYK GERENCIAMENTO DE TAREFAS API")
                .version("0.0.1")
                .contact(contact)
                .description("This API exposes endpoints to manage GERENCIAMENTO DE TAREFAS.");

        return new OpenAPI().info(info);
    }
}
