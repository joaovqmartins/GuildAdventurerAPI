package br.com.infnet.GuildaAventureiros.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Companheiro    {
    private String nome;
    private Especie especie;
    private Integer lealdade;
}
