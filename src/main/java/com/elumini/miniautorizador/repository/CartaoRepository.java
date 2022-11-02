package com.elumini.miniautorizador.repository;

import com.elumini.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

    @Query("SELECT c.saldo FROM Cartao c WHERE c.numeroCartao like :numeroDeCartaoExistente")
    Optional<Double> obterSaldoDoCartao(String numeroDeCartaoExistente);

    Optional<List<Cartao>> findByCliente_Cpf(String cpf);
    /*
    * Outra manei de criar a cunsulta de cartões por usuario
    *
    * @Query("SELECT c FROM Cartao c JOIN c.cliente e WHERE c.cliente.cpf = :cpf")
    * Optional<List<Cartao>> obterCartoesPorCliente(String cpf);
    * */
}
