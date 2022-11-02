package com.elumini.miniautorizador.dto;

import com.elumini.miniautorizador.model.Cartao;
import com.elumini.miniautorizador.utils.atributos.CartaoBaseAtributos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class CartaoDto extends CartaoBaseAtributos {

    private String dataCriacao = formatarData(LocalDate.now());
    private String validade = formatarData(LocalDate.now().plusYears(3).plusMonths(10));
    private String cpfCliente;

    public CartaoDto(Cartao cartao) {
        super(cartao.getNumeroCartao(), cartao.getSenha());
        this.dataCriacao = formatarData(cartao.getDataCriacao());
        this.validade = formatarData(cartao.getValidade());
        this.cpfCliente = cartao.getCliente().getCpf();
    }

    public static String formatarData(LocalDate data){
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public CartaoDto() {
    }
}
