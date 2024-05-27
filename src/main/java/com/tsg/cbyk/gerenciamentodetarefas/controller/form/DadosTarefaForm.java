package com.tsg.cbyk.gerenciamentodetarefas.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosTarefaForm(@NotBlank String titulo,
                              @NotBlank String descricao,
                              @NotNull Long usuarioId) {
}
