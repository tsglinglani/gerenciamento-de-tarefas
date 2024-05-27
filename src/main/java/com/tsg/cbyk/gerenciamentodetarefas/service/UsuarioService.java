package com.tsg.cbyk.gerenciamentodetarefas.service;

import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosUsuarioForm;
import com.tsg.cbyk.gerenciamentodetarefas.model.Usuario;
import com.tsg.cbyk.gerenciamentodetarefas.repository.UsuarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario salvar(DadosUsuarioForm form) {
        validarSeUsuarioJaEstaCadastrado(form);
        return usuarioRepository.save(criarUsuario(form));
    }

    public UserDetails findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obter(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário: %d não encontrado.", usuarioId)));
    }

    private Usuario criarUsuario(DadosUsuarioForm form) {
        var senhaCriptografada = passwordEncoder.encode(form.senha());
        return new Usuario.UsuarioBuilder()
                .nome(form.nome())
                .email(form.email())
                .senha(senhaCriptografada)
                .build();
    }

    private void validarSeUsuarioJaEstaCadastrado(DadosUsuarioForm form) {
        UserDetails usuario = usuarioRepository.findByEmail(form.email());
        if (usuario != null) {
            throw new EntityExistsException(String.format("Usuário com e-mail: %s já está cadastrado.", form.email()));
        }
    }

}
