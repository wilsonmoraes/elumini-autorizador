package com.elumini.miniautorizador.utils.atributos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartaoBaseAtributos {

    @NotNull
    @NotEmpty
    private String numeroCartao;

    @NotNull
    @NotEmpty
    private String senha;

    public CartaoBaseAtributos(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public CartaoBaseAtributos() {
    }
}
