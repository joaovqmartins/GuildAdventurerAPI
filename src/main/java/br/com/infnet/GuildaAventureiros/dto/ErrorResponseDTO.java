package br.com.infnet.GuildaAventureiros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private String mensagem;
    private List<String> detalhes;
}
