package com.elumini.miniautorizador.model;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.dto.CartaoDto;
import com.elumini.miniautorizador.exception.HandleException;
import com.elumini.miniautorizador.utils.ValidacoesEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Entity
public class Cartao {

    @Id
    @Column(unique = true)
    private String numeroCartao;
    private String senha;
    private Double saldo;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    private LocalDate validade;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;

    public Cartao(CartaoCriacaoDto cartaoCriacaoDto) {
        this.numeroCartao = cartaoCriacaoDto.getNumeroCartao();
        this.senha = cartaoCriacaoDto.getSenha();
        this.saldo = 500.00;
        this.dataCriacao = LocalDate.now();
        this.validade = LocalDate.now().plusYears(3).plusMonths(10);
    }

    public Cartao(String numeroCartao, String senha, Double saldo, Cliente cliente) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = saldo;
        this.dataCriacao = LocalDate.now();
        this.validade = LocalDate.now().plusYears(3).plusMonths(10);
        this.cliente = cliente;
    }

    public Cartao(CartaoCriacaoDto cartaoCriacaoDto, Cliente cliente) {
        this.numeroCartao = cartaoCriacaoDto.getNumeroCartao();
        this.senha = cartaoCriacaoDto.getSenha();
        this.saldo = 500.00;
        this.dataCriacao = LocalDate.now();
        this.validade = LocalDate.now().plusYears(3).plusMonths(10);
        this.cliente = cliente;
    }

    public void validarAtribuirNovoSaldo(Double valor, HandleException handleException) {
        this.saldo = this.saldo >= valor ? this.saldo - valor :
                (Double) handleException.throwExcecaoDeValidacao(ValidacoesEnum.SALDO_INSUFICIENTE);
    }

    public Cartao(CartaoDto cartaoDto) {
        this.numeroCartao = cartaoDto.getNumeroCartao();
        this.senha = cartaoDto.getSenha();
        this.saldo = 500.00;
        this.dataCriacao = formatarString(cartaoDto.getDataCriacao());
        this.validade = formatarString(cartaoDto.getValidade());
    }

    public static LocalDate formatarString(String data) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate dataFormatada = null;
        try {
            dataFormatada = formater.parse(data).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataFormatada;
    }

    public Cartao() {
    }


}
