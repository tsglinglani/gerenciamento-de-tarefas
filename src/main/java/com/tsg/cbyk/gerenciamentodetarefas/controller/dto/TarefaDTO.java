package com.tsg.cbyk.gerenciamentodetarefas.controller.dto;

import com.tsg.cbyk.gerenciamentodetarefas.model.Tarefa;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record TarefaDTO(Long tarefaId,
                        String titulo,
                        String descricao,
                        String status,
                        LocalDateTime dataCriacao,
                        LocalDateTime dataAtualizacao,
                        String nomeUsuario) {


    public static Page<TarefaDTO> fromPage(Page<Tarefa> tarefas) {
        return tarefas.map(TarefaDTO::from);
    }

    public static TarefaDTO from(Tarefa tarefa) {
        return new TarefaDTO(tarefa.getTarefaId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus().name(),
                tarefa.getDataCriacao(),
                tarefa.getDataAtualizacao(),
                tarefa.getNomeUsuario());
    }
}
