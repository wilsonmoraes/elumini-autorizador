package com.elumini.miniautorizador.dto;

import com.elumini.miniautorizador.utils.atributos.CartaoBaseAtributos;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartaoCriacaoDto extends CartaoBaseAtributos {

    @NotNull
    @NotEmpty
    private String cpfCliente;

    public CartaoCriacaoDto(String numeroCartao, String senha, String cpfCliente) {
        super(numeroCartao,senha);
        this.cpfCliente = cpfCliente;
    }

    public CartaoCriacaoDto() {
    }
}
