package com.elumini.miniautorizador.model;

import com.elumini.miniautorizador.dto.ClienteCriacaoDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String cpf;
    public LocalDate data_inicio;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    public Cliente( String cpf, LocalDate data_inicio, List<Cartao> cartoes, Endereco endereco) {
        this.cpf = cpf;
        this.data_inicio = data_inicio;
        this.cartoes = cartoes;
        this.endereco = endereco;
    }

    public Cliente(ClienteCriacaoDto clienteRequest) {
        this.cpf = clienteRequest.getCpf();
        this.data_inicio = LocalDate.now();
        this.endereco = new Endereco(clienteRequest.getEndereco());
    }
    public Cliente() {
    }
}
