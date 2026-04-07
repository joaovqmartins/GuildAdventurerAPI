package br.com.infnet.GuildaAventureiros.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanheiroDTO {
    @NotBlank(message = "nome do companheiro não pode ser vazio")
    private String nome;

    @NotBlank(message = "espécie inválida")
    private String especie;

    @NotNull(message = "lealdade é obrigatória") //teste123
    @Min(value = 0, message = "lealdade não pode ser menor que 0")
    @Max(value = 100, message = "lealdade não pode ser maior que 100")
    private Integer lealdade;


}
