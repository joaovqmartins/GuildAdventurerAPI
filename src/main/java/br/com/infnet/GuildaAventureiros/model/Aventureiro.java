package br.com.infnet.GuildaAventureiros.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Aventureiro {
    private Long id;
    private String nome;
    private Classe classe;
    private Integer nivel;
    private boolean ativo;
    private Companheiro companheiro;
}
