package com.elumini.miniautorizador.dto;

import com.elumini.miniautorizador.model.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto extends EnderecoCriacaoDto {

    private Integer id;

    public EnderecoDto(String logradouro, String cidade, String estado, String complemento, Integer id) {
        super(logradouro, cidade, estado, complemento);
        this.id = id;
    }

    public EnderecoDto(Endereco endereco) {
        super(endereco.getLogradouro(), endereco.getCidade(), endereco.getEstado(), endereco.getComplemento());
        this.id = endereco.getId();
    }
}
