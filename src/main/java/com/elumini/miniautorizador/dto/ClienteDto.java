package com.elumini.miniautorizador.dto;

import com.elumini.miniautorizador.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClienteDto extends ClienteCriacaoDto {

    public Integer id;
    public String data_inicio;
    List<CartaoDto> cartoes = new ArrayList<>();
    EnderecoDto endereco;

    public ClienteDto(String cpf, Integer id, String data_inicio, List<CartaoDto> cartoes, EnderecoDto endereco) {
        this.setCpf(cpf);
        this.id = id;
        this.data_inicio = data_inicio;
        this.cartoes = cartoes;
        this.endereco = endereco;
    }

    public ClienteDto(){}

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.setCpf(cliente.getCpf());
        this.data_inicio = cliente.getData_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.cartoes = cliente.getCartoes().stream().map(CartaoDto::new).collect(Collectors.toList());
        this.endereco = new EnderecoDto(cliente.getEndereco());
    }
}
