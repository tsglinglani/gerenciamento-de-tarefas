package com.tsg.cbyk.gerenciamentodetarefas.service;

import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaAtualizacaoForm;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaForm;
import com.tsg.cbyk.gerenciamentodetarefas.model.Tarefa;
import com.tsg.cbyk.gerenciamentodetarefas.model.Usuario;
import com.tsg.cbyk.gerenciamentodetarefas.model.enumeration.StatusTarefaEnum;
import com.tsg.cbyk.gerenciamentodetarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<Tarefa> tarefaCaptor;

    private DadosTarefaForm dadosTarefaForm;
    private Tarefa tarefa;
    private Pageable paginacao;
    private Long tarefaId;
    private DadosTarefaAtualizacaoForm dadosTarefaAtualizacaoForm;
    private Long usuarioId;
    private String novoStatus;

    @Test
    void salvarTarefa() {
        dadoUmFormPreenchidoComDadosDeTarefa();
        quandoExecutarMetodoSalvar();
        deveSalvarTarefa();
    }

    @Test
    void listarTodosTarefas() {
        dadoInformacoesDePaginacao();
        dadoTarefaIdNulo();
        quandoExecutarMetodoListar();
        deveExecutarMetodoFindAll();
    }

    @Test
    void listarTarefasPorTareId() {
        dadoInformacoesDePaginacao();
        dadoTarefaIdPreenchido();
        quandoExecutarMetodoListar();
        deveExecutarMetodoFindByTarefaId();
    }

    @Test
    void obter() {
        dadoTarefaIdPreenchido();
        quandoExecutarMetodoObter();
        deveExecutarMetodoFindById();
    }

    @Test
    void retornarMensagemMetodoObter() {
        dadoTarefaIdPreenchido();
        deveRetornarMensagemQuandoTarefaNaoForEncontradaPeloMetodoObter();
    }

    @Test
    void atualizar() {
        dadoTarefaIdPreenchido();
        dadoUmFormPreenchidoComDadosParaAtualizacaoDeTarefa();
        quandoExecutarMetodoAtualizar();
        deveAtualizarUmaTarefa();
    }

    @Test
    void atualizarStatus() {
        dadoTarefaIdPreenchido();
        dadoNovoStatusParaTarefa();
        quandoExecutarMetodoAtualizarStatus();
        deveAtualizarStatusDaTarefa();
    }

    @Test
    void deletar() {
        dadoTarefaIdPreenchido();
        quandoExecutarMetodoDeletar();
        deveDeletarUmaTarefa();
    }

    private void quandoExecutarMetodoSalvar() {
        getCenarioUsuario();
        tarefaService.salvar(dadosTarefaForm);
    }

    private void deveSalvarTarefa() {
        Mockito.verify(tarefaRepository).save(tarefaCaptor.capture());
        tarefa = tarefaCaptor.getValue();
        assertEquals(dadosTarefaForm.descricao(), tarefa.getDescricao());
        assertEquals(dadosTarefaForm.titulo(), tarefa.getTitulo());
        assertEquals(StatusTarefaEnum.PENDENTE, tarefa.getStatus());
        assertNotNull(tarefa.getDataCriacao());
        assertEquals(dadosTarefaForm.usuarioId(), tarefa.getUsuario().getUsuarioId());
    }

    private void dadoInformacoesDePaginacao() {
        paginacao = PageRequest.of(0, 200, Sort.by("tarefaId").descending());
    }

    private void dadoTarefaIdNulo() {
        tarefaId = null;
    }

    private void quandoExecutarMetodoListar() {
        tarefaService.listar(tarefaId, paginacao);
    }

    private void deveExecutarMetodoFindAll() {
        Mockito.verify(tarefaRepository).findAll(paginacao);
    }

    private void dadoTarefaIdPreenchido() {
        tarefaId = 10L;
    }

    private void deveExecutarMetodoFindByTarefaId() {
        Mockito.verify(tarefaRepository).findByTarefaId(tarefaId, paginacao);
    }

    private void quandoExecutarMetodoObter() {
        usuarioId = 77L;
        getCenarioTarefa();
        tarefaService.obter(tarefaId);
    }

    private void deveExecutarMetodoFindById() {
        Mockito.verify(tarefaRepository).findById(tarefaId);
    }

    private void deveRetornarMensagemQuandoTarefaNaoForEncontradaPeloMetodoObter() {
        var ex = assertThrows(
                EntityNotFoundException.class,
                () -> tarefaService.obter(tarefaId));

        assertEquals("Tarefa: 10 não foi encontrada!", ex.getMessage());
    }

    private void dadoUmFormPreenchidoComDadosParaAtualizacaoDeTarefa() {
        usuarioId = 77L;
        getCenarioTarefa();
        dadosTarefaAtualizacaoForm = new DadosTarefaAtualizacaoForm("Novo Titulo", "Nova Descrição", usuarioId);
    }

    private void quandoExecutarMetodoAtualizar() {
        tarefaService.atualizar(tarefaId, dadosTarefaAtualizacaoForm);
    }

    private void deveAtualizarUmaTarefa() {
        Mockito.verify(tarefaRepository).save(tarefaCaptor.capture());
        tarefa = tarefaCaptor.getValue();
        assertEquals(dadosTarefaAtualizacaoForm.descricao(), tarefa.getDescricao());
        assertEquals(dadosTarefaAtualizacaoForm.titulo(), tarefa.getTitulo());
        assertNotNull(tarefa.getDataCriacao());
        assertNotNull(tarefa.getDataAtualizacao());
        assertEquals(dadosTarefaAtualizacaoForm.usuarioId(), tarefa.getUsuario().getUsuarioId());
    }

    private void dadoNovoStatusParaTarefa() {
        usuarioId = 77L;
        getCenarioTarefa();
        novoStatus = "CONCLUIDO";
    }

    private void quandoExecutarMetodoAtualizarStatus() {
        tarefaService.atualizarStatus(tarefaId, novoStatus);
    }

    private void deveAtualizarStatusDaTarefa() {
        Mockito.verify(tarefaRepository).save(tarefaCaptor.capture());
        tarefa = tarefaCaptor.getValue();
        assertEquals(novoStatus, tarefa.getStatus().name());
        assertNotNull(tarefa.getDataAtualizacao());
    }

    private void dadoUmFormPreenchidoComDadosDeTarefa() {
        usuarioId = 77L;
        dadosTarefaForm = new DadosTarefaForm("Tarefa teste", "Excução de testes", usuarioId);
    }

    private void quandoExecutarMetodoDeletar() {
        usuarioId = 77L;
        getCenarioTarefa();
        tarefaService.deletar(tarefaId);
    }

    private void deveDeletarUmaTarefa() {
        Mockito.verify(tarefaRepository).delete(tarefa);
    }

    private void getCenarioTarefa() {
        var usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);

        tarefa = new Tarefa();
        tarefa.setTarefaId(tarefaId);
        tarefa.setDataAtualizacao(null);
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setStatus(StatusTarefaEnum.PENDENTE);
        tarefa.setTitulo("Título do tarefa atual");
        tarefa.setDescricao("Descrição do tarefa atual");
        tarefa.setUsuario(usuario);
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
    }

    private void getCenarioUsuario() {
        var usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);
        usuario.setNome("Teste Usuário");
        when(usuarioService.obter(usuarioId)).thenReturn(usuario);
    }
}