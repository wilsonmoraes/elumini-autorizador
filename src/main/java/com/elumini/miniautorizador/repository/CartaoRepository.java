package com.elumini.miniautorizador.repository;

import com.elumini.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

    @Query("SELECT c.saldo FROM Cartao c WHERE c.numeroCartao like :numeroDeCartaoExistente")
    Double obterSaldoDoCartao(String numeroDeCartaoExistente);
}
