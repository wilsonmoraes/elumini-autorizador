package com.elumini.miniautorizador.model;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.exception.SaldoInsuficienteException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

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

    public void validarAtribuirNovoSaldo(Double valor) {
        this.saldo = Objects.isNull(this.saldo) ? 0L : this.saldo;
        if (valor >= this.saldo) {
            throw new SaldoInsuficienteException();
        }
        this.saldo = this.saldo - valor;
    }


    public Cartao() {
    }

    public Cartao(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public static Cartao fromDTO(CartaoCriacaoDto cartaoCriacaoDto) {
        return new Cartao(cartaoCriacaoDto.getNumeroCartao(), cartaoCriacaoDto.getSenha());

    }

}
