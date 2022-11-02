package com.elumini.miniautorizador.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClienteCriacaoDto {

    @NotEmpty
    @NotNull
    private String cpf;

    @NotNull
    private EnderecoCriacaoDto endereco;

    public ClienteCriacaoDto(String cpf, EnderecoCriacaoDto endereco) {
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public ClienteCriacaoDto() {
    }
}
