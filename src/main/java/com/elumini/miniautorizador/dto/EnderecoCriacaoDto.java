package com.elumini.miniautorizador.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoCriacaoDto {

    @NotEmpty
    @NotNull
    private String logradouro;
    @NotEmpty
    @NotNull
    private String cidade;
    @NotEmpty
    @NotNull
    private String estado;
    private String complemento;

    public EnderecoCriacaoDto(String logradouro, String cidade, String estado, String complemento) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
    }

    public EnderecoCriacaoDto() {
    }
}
