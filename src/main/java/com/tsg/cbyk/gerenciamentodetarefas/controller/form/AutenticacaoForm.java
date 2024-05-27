package com.tsg.cbyk.gerenciamentodetarefas.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoForm(@NotBlank @Email String email,
                               @NotBlank String senha) {
}
