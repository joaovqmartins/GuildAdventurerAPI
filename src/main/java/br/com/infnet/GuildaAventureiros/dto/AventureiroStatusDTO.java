package br.com.infnet.GuildaAventureiros.dto;

import br.com.infnet.GuildaAventureiros.model.Aventureiro;
import br.com.infnet.GuildaAventureiros.model.Classe;
import lombok.Getter;

@Getter
public class AventureiroStatusDTO {
    private Long id;
    private String nome;
    private Classe classe;
    private Integer nivel;
    private boolean ativo;

    public AventureiroStatusDTO(Aventureiro aventureiro) {
        this.id = aventureiro.getId();
        this.nome = aventureiro.getNome();
        this.classe = aventureiro.getClasse();
        this.nivel = aventureiro.getNivel();
        this.ativo = aventureiro.isAtivo();
    }
}
