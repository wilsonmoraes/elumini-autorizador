package com.elumini.miniautorizador.dto;

import lombok.Getter;

@Getter
public class ErroValidacaoDto {

    private String campo;
    private String erro;

    public ErroValidacaoDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

}
