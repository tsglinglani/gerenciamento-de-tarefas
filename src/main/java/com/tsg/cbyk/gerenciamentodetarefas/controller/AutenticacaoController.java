package com.tsg.cbyk.gerenciamentodetarefas.controller;

import com.tsg.cbyk.gerenciamentodetarefas.config.ApiInfo;
import com.tsg.cbyk.gerenciamentodetarefas.config.security.DadosTokenJWT;
import com.tsg.cbyk.gerenciamentodetarefas.config.security.TokenBlackList;
import com.tsg.cbyk.gerenciamentodetarefas.config.security.TokenService;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.AutenticacaoForm;
import com.tsg.cbyk.gerenciamentodetarefas.model.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CBYK: GERENCIAMENTO DE TAREFAS - Auntenticação", description = "CBYK Gerenciamento de Tarefas - Auntenticação")
@RestController
@RequestMapping(ApiInfo.API_V1)
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final TokenBlackList tokenBlackList;

    public AutenticacaoController(AuthenticationManager authenticationManager,
                                  TokenService tokenService,
                                  TokenBlackList tokenBlackList) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.tokenBlackList = tokenBlackList;
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid AutenticacaoForm form) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(form.email(), form.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var  principal = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(principal);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, principal.getUsuarioId()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        var token = tokenService.extractTokenFromRequest(request);
        tokenBlackList.salvarTokenBlackList(token);

        return ResponseEntity.ok("Logout realizado com sucesso.");
    }
}
