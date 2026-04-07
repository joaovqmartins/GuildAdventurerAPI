package br.com.infnet.GuildaAventureiros.exception;

import br.com.infnet.GuildaAventureiros.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponseDTO response = new ErrorResponseDTO("Solicitação inválida", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO("Recurso não encontrado", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

