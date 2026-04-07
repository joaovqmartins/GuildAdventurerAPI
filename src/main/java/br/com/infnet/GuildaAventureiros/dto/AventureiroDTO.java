package br.com.infnet.GuildaAventureiros.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AventureiroDTO {
    @NotBlank(message = "nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "classe inválida")
    private String classe;

    @NotNull(message = "nivel é obrigatório")
    @Min(value = 1, message = "nivel deve ser maior ou igual a 1")
    private Integer nivel;

}
