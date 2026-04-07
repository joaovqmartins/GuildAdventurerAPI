package br.com.infnet.GuildaAventureiros.service;

import br.com.infnet.GuildaAventureiros.dto.AventureiroDTO;
import br.com.infnet.GuildaAventureiros.dto.CompanheiroDTO;
import br.com.infnet.GuildaAventureiros.exception.ResourceNotFoundException;
import br.com.infnet.GuildaAventureiros.model.Aventureiro;
import br.com.infnet.GuildaAventureiros.model.Classe;
import br.com.infnet.GuildaAventureiros.model.Companheiro;
import br.com.infnet.GuildaAventureiros.model.Especie;
import br.com.infnet.GuildaAventureiros.repository.AventureiroRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AventureiroService {

    private final AventureiroRepository repository;

    public AventureiroService(AventureiroRepository repository) {
        this.repository = repository;
    }

    public List<Aventureiro> listarTodosFiltrados(String classeFiltro, Boolean ativo, Integer nivelMinimo) {
        Classe classeEnum = null;
        if (classeFiltro != null && !classeFiltro.isBlank()) {
            try {
                classeEnum = Classe.valueOf(classeFiltro.toUpperCase());
            } catch (IllegalArgumentException e) {
                return List.of();
            }
        }

        final Classe filtroClasseFinal = classeEnum;

        return repository.getTodos().stream()
                .filter(a -> (filtroClasseFinal == null || a.getClasse().equals(filtroClasseFinal)))
                .filter(a -> (ativo == null || a.isAtivo() == ativo))
                .filter(a -> (nivelMinimo == null || a.getNivel() >= nivelMinimo))
                .sorted(Comparator.comparing(Aventureiro::getId))
                .collect(Collectors.toList());
    }

    public Aventureiro buscarPorId(Long id) {
        return repository.getTodos().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Aventureiro com ID " + id + " não encontrado."));
    }

    public Aventureiro criar(AventureiroDTO dto) {
        Classe classeEnum = Classe.valueOf(dto.getClasse().toUpperCase());
        Aventureiro novo = new Aventureiro(null, dto.getNome(), classeEnum, dto.getNivel(), true, null);
        return repository.salvar(novo);
    }

    public Aventureiro atualizar(Long id, AventureiroDTO dto) {
        Aventureiro existente = buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setClasse(Classe.valueOf(dto.getClasse().toUpperCase()));
        existente.setNivel(dto.getNivel());
        return existente;
    }

    public void inativar(Long id) {
        Aventureiro existente = buscarPorId(id);
        existente.setAtivo(false);
    }

    public void ativar(Long id) {
        Aventureiro existente = buscarPorId(id);
        existente.setAtivo(true);
    }

    public Aventureiro definirCompanheiro(Long idAventureiro, CompanheiroDTO dto) {
        Aventureiro aventureiro = buscarPorId(idAventureiro);
        Especie especieEnum = Especie.valueOf(dto.getEspecie().toUpperCase());
        Companheiro companheiro = new Companheiro(dto.getNome(), especieEnum, dto.getLealdade());

        aventureiro.setCompanheiro(companheiro);
        return aventureiro;
    }

    public void removerCompanheiro(Long idAventureiro) {
        Aventureiro aventureiro = buscarPorId(idAventureiro);
        aventureiro.setCompanheiro(null);
    }
}