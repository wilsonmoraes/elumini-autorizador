package com.elumini.miniautorizador.exception;

import com.elumini.miniautorizador.dto.ErroValidacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacaoDto> handle(MethodArgumentNotValidException exception) {

        List<ErroValidacaoDto> erros = new ArrayList<>();
        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
        fieldErros.forEach(e ->
        {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroValidacaoDto erro = new ErroValidacaoDto(e.getField(), mensagem);
            erros.add(erro);
        });

        return erros;
    }

}
