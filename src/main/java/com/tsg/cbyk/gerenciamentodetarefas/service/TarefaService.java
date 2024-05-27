package com.tsg.cbyk.gerenciamentodetarefas.service;

import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaAtualizacaoForm;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaForm;
import com.tsg.cbyk.gerenciamentodetarefas.model.Tarefa;
import com.tsg.cbyk.gerenciamentodetarefas.model.enumeration.StatusTarefaEnum;
import com.tsg.cbyk.gerenciamentodetarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;

    public TarefaService(TarefaRepository tarefaRepository,
                         UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Tarefa salvar(DadosTarefaForm form) {
       return tarefaRepository.save(criarTarefa(form));
    }

    public Page<Tarefa> listar(Long tarefaId, Pageable paginacao) {
        if (tarefaId == null) {
            Page<Tarefa> tarefas = tarefaRepository.findAll(paginacao);
            return tarefas;

        } else {
            Page<Tarefa> tarefas = tarefaRepository.findByTarefaId(tarefaId, paginacao);
            return tarefas;
        }
    }

    public Tarefa obter(Long tarefaId) {
        return getTarefa(tarefaId);
    }

    @Transactional
    public Tarefa atualizar(Long tarefaId, DadosTarefaAtualizacaoForm form) {
        var tarefa = getTarefa(tarefaId);
        tarefa.setDataAtualizacao(LocalDateTime.now());
        tarefa.setDescricao(form.descricao());
        tarefa.setTitulo(form.titulo());
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizarStatus(Long tarefaId, String status) {
        var tarefa = getTarefa(tarefaId);
        tarefa.setDataAtualizacao(LocalDateTime.now());
        tarefa.setStatus(StatusTarefaEnum.valueOf(status));
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public void deletar(Long tarefaId) {
        var tarefa = getTarefa(tarefaId);
        tarefaRepository.delete(tarefa);
    }

    private Tarefa criarTarefa(DadosTarefaForm form) {
        var usuario = usuarioService.obter(form.usuarioId());
        var tarefa = new Tarefa.TarefaBuilder()
                .descricao(form.descricao())
                .titulo(form.titulo())
                .status(StatusTarefaEnum.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .usuario(usuario)
                .build();

        usuario.addTarefa(tarefa);

        return tarefa;
    }

    private Tarefa getTarefa(Long tarefaId) {
        return tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tarefa: %d não foi encontrada!", tarefaId)));
    }
}
