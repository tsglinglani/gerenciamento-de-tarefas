package com.tsg.cbyk.gerenciamentodetarefas.controller.dto;

import com.tsg.cbyk.gerenciamentodetarefas.model.Usuario;

public record UsuarioDTO(String nome,
                         String email) {

    public static UsuarioDTO from(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail());
    }
}
