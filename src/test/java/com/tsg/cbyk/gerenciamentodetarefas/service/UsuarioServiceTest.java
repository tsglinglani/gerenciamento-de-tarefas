package com.tsg.cbyk.gerenciamentodetarefas.service;

import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosUsuarioForm;
import com.tsg.cbyk.gerenciamentodetarefas.model.Usuario;
import com.tsg.cbyk.gerenciamentodetarefas.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private final String SENHA_ENCODER = "$2a$10$yNirT/T6V418oRKI8ULIxu07V/sySzbfQvUMdXo8/NJ5v9giW4vW6";

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    private DadosUsuarioForm form;
    private String email;
    private Long usuarioId;

    @Test
    void salvarUsuario() {
        dadoUmFormPreenchidoComDadosDeUsuario();
        quandoExecutarMetodoSalvar();
        deveSalvarUmUsuario();
    }

    @Test
    void lancarMensagemAoCadastrarUsuarioExistenteNoBanco() {
        dadoUmFormPreenchidoComDadosDeUsuario();
        deveLancarMensagemAoCadastrarUsuarioExistenteNoBanco();
    }

    @Test
    void findByEmail() {
        dadoUmEmail();
        quandoExecutarMetodoFinByEmail();
        deveExecutarUsuarioRepositoryFindByEmail();
    }

    @Test
    void lancarMensagemAoObterUsuarioNaoCadastradoNoBanco() {
        dadoUmUsuarioId();
        deveLancarMensagemAoObterUsuarioNaoCadastradoNoBanco();
    }

    @Test
    void obterUsuarioCadastrado() {
        dadoUmUsuarioId();
        quandoExecutarMetodoObter();
        deveObterUsuarioCadastrado();
    }

    private void dadoUmFormPreenchidoComDadosDeUsuario() {
        form = new DadosUsuarioForm("Teste Usuário",
                "testeusuario@teste.com",
                "teste123");
    }

    private void quandoExecutarMetodoSalvar() {
        Mockito.when(passwordEncoder.encode(form.senha())).thenReturn(SENHA_ENCODER);
        usuarioService.salvar(form);
    }

    private void deveSalvarUmUsuario() {
        Mockito.verify(usuarioRepository).save(usuarioCaptor.capture());
        Usuario usuario = usuarioCaptor.getValue();
        assertEquals(form.nome(), usuario.getNome());
        assertEquals(form.email(), usuario.getEmail());
        assertEquals(SENHA_ENCODER, usuario.getSenha());
    }

    private void dadoUmEmail() {
        email = "testeusuario@teste.com";
    }

    private void quandoExecutarMetodoFinByEmail() {
        usuarioService.findByEmail(email);
    }

    private void deveExecutarUsuarioRepositoryFindByEmail() {
        Mockito.verify(usuarioRepository).findByEmail(email);
    }

    private void deveLancarMensagemAoCadastrarUsuarioExistenteNoBanco() {
        getCenarioUsuariJaCadastrado();
        var ex = assertThrows(
                RuntimeException.class,
                () -> usuarioService.salvar(form));

        assertEquals("Usuário com e-mail: testeusuario@teste.com já está cadastrado.", ex.getMessage());
    }

    private void dadoUmUsuarioId() {
        usuarioId = 1L;
    }

    private void deveLancarMensagemAoObterUsuarioNaoCadastradoNoBanco() {
        var ex = assertThrows(
                EntityNotFoundException.class,
                () -> usuarioService.obter(usuarioId));

        assertEquals("Usuário: 1 não encontrado.", ex.getMessage());
    }

    private void quandoExecutarMetodoObter() {
        getCenarioUsuarioPorId();
        usuarioService.obter(usuarioId);
    }

    private void deveObterUsuarioCadastrado() {
        Mockito.verify(usuarioRepository).findById(usuarioId);
    }

    private void getCenarioUsuariJaCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        Mockito.when(usuarioRepository.findByEmail(form.email())).thenReturn(usuario);
    }

    private void getCenarioUsuarioPorId() {
        var usuario = new Usuario.UsuarioBuilder()
                .email("teste@teste.com")
                .build();
        usuario.setUsuarioId(usuarioId);
        Mockito.when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
    }
}