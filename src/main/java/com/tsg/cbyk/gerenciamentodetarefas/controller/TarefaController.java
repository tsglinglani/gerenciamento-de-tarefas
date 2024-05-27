package com.tsg.cbyk.gerenciamentodetarefas.controller;

import com.tsg.cbyk.gerenciamentodetarefas.config.ApiInfo;
import com.tsg.cbyk.gerenciamentodetarefas.controller.dto.TarefaDTO;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaAtualizacaoForm;
import com.tsg.cbyk.gerenciamentodetarefas.controller.form.DadosTarefaForm;
import com.tsg.cbyk.gerenciamentodetarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "CBYK: GERENCIAMENTO DE TAREFAS - Tarefas", description = "CBYK Gerenciamento de Tarefas - Tarefas")
@RestController
@RequestMapping(ApiInfo.API_V1)
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping("/tarefas")
    public ResponseEntity<TarefaDTO> salvar(@RequestBody DadosTarefaForm form, UriComponentsBuilder uriBuilder) {
        var tarefaDTO = TarefaDTO.from(tarefaService.salvar(form));
        var uri = uriBuilder.path(String.format("%s/tarefas/{tarefaId}", ApiInfo.API_V1)).buildAndExpand(tarefaDTO.tarefaId()).toUri();
        return ResponseEntity.created(uri).body(tarefaDTO);
    }

    @GetMapping("/tarefas")
    public ResponseEntity<Page<TarefaDTO>> lista(@RequestParam(required = false) Long tarefaId,
                                 @PageableDefault(sort = "tarefaId", direction = Sort.Direction.ASC, page = 0, size = 200) Pageable paginacao) {
        var tarefas = TarefaDTO.fromPage(tarefaService.listar(tarefaId, paginacao));
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/tarefas/{tarefaId}")
    public ResponseEntity<TarefaDTO> obterTarefa(@PathVariable Long tarefaId) {
        var tarefaDTO = TarefaDTO.from(tarefaService.obter(tarefaId));
        return ResponseEntity.ok(tarefaDTO);
    }

    @PatchMapping("/tarefas/{tarefaId}/{status}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable Long tarefaId, @PathVariable String status) {
        var tarefaDTO = TarefaDTO.from(tarefaService.atualizarStatus(tarefaId, status));
        return ResponseEntity.ok(tarefaDTO);
    }

    @PutMapping("/tarefas/{tarefaId}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable Long tarefaId, @RequestBody @Valid DadosTarefaAtualizacaoForm form) {
        var tarefaDTO = TarefaDTO.from(tarefaService.atualizar(tarefaId, form));
        return ResponseEntity.ok(tarefaDTO);
    }

    @DeleteMapping("/tarefas/{tarefaId}")
    public ResponseEntity<?> remover(@PathVariable Long tarefaId) {
        tarefaService.deletar(tarefaId);
        return ResponseEntity.noContent().build();
    }
}
