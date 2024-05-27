package com.tsg.cbyk.gerenciamentodetarefas.model;

import com.tsg.cbyk.gerenciamentodetarefas.model.enumeration.StatusTarefaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tarefaId;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusTarefaEnum status;

    public Tarefa() {
    }

    private Tarefa(TarefaBuilder tarefaBuilder) {
        this.titulo = tarefaBuilder.titulo;
        this.descricao = tarefaBuilder.descricao;
        this.dataCriacao = tarefaBuilder.dataCriacao;
        this.usuario = tarefaBuilder.usuario;
        this.status = tarefaBuilder.status;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public StatusTarefaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTarefaEnum status) {
        this.status = status;
    }

    public String getNomeUsuario() {
        return this.usuario.getNome();
    }

    public static class TarefaBuilder {
        private String titulo;
        private String descricao;
        private LocalDateTime dataCriacao;
        private Usuario usuario;
        private StatusTarefaEnum status;

        public TarefaBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public TarefaBuilder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public TarefaBuilder dataCriacao(LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public TarefaBuilder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public TarefaBuilder status(StatusTarefaEnum status) {
            this.status = status;
            return this;
        }

        public Tarefa build() {
            return new Tarefa(this);
        }
    }
}
