package br.com.infnet.GuildaAventureiros.controller;

import br.com.infnet.GuildaAventureiros.dto.AventureiroDTO;
import br.com.infnet.GuildaAventureiros.dto.AventureiroStatusDTO;
import br.com.infnet.GuildaAventureiros.dto.CompanheiroDTO;
import br.com.infnet.GuildaAventureiros.model.Aventureiro;
import br.com.infnet.GuildaAventureiros.service.AventureiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aventureiros")
public class AventureiroController {

    private final AventureiroService service;

    public AventureiroController(AventureiroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AventureiroStatusDTO>> listar(
            @RequestParam(required = false) String classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0) page = 0;
        if (size < 1) size = 1;
        if (size > 50) size = 50;

        List<Aventureiro> filtrados = service.listarTodosFiltrados(classe, ativo, nivelMinimo);

        int totalRegistros = filtrados.size();
        int totalPages = (int) Math.ceil((double) totalRegistros / size);
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalRegistros);

        List<AventureiroStatusDTO> resumoPaginado;
        if (startIndex >= totalRegistros) {
            resumoPaginado = List.of();
        } else {
            resumoPaginado = filtrados.subList(startIndex, endIndex).stream()
                    .map(AventureiroStatusDTO::new)
                    .collect(Collectors.toList());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalRegistros));
        headers.add("X-Page", String.valueOf(page));
        headers.add("X-Size", String.valueOf(size));
        headers.add("X-Total-Pages", String.valueOf(totalPages));

        return ResponseEntity.ok().headers(headers).body(resumoPaginado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aventureiro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Aventureiro> criar(@Valid @RequestBody AventureiroDTO dto) {
        Aventureiro criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aventureiro> atualizar(@PathVariable Long id, @Valid @RequestBody AventureiroDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> definirCompanheiro(@PathVariable Long id, @Valid @RequestBody CompanheiroDTO dto) {
        Aventureiro aventureiro = service.definirCompanheiro(id, dto);
        return ResponseEntity.ok(aventureiro);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}