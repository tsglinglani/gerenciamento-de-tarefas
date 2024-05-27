package com.tsg.cbyk.gerenciamentodetarefas.controller;

import com.tsg.cbyk.gerenciamentodetarefas.config.ApiInfo;
import com.tsg.cbyk.gerenciamentodetarefas.controller.dto.UsuarioDTO;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosUsuarioForm;
import com.tsg.cbyk.gerenciamentodetarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "CBYK: GERENCIAMENTO DE TAREFAS - Usuários", description = "CBYK Gerenciamento de Tarefas - Usuários")
@RestController
@RequestMapping(ApiInfo.API_V1)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody @Valid DadosUsuarioForm form, UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.salvar(form);
        var usuarioDTO = UsuarioDTO.from(usuario);
        var uri = uriBuilder.path(String.format("%s/usuarios/{usuarioId}", ApiInfo.API_V1)).buildAndExpand(usuario.getUsuarioId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<UsuarioDTO> obeter(@PathVariable Long usuarioId) {
        var usuarioDTO = UsuarioDTO.from(usuarioService.obter(usuarioId));
        return ResponseEntity.ok(usuarioDTO);
    }
}
