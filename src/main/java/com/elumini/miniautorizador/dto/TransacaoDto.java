package com.elumini.miniautorizador.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class TransacaoDto {

    @NotNull
    @NotEmpty
    private String numeroCartao;

    @NotNull
    @NotEmpty
    private String senha;

    @NotNull
    @Min(value = 1)
    private Double valor;

    public TransacaoDto(String numeroCartao, String senha, Double valor) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.valor = valor;
    }

    public TransacaoDto() {
    }
}
