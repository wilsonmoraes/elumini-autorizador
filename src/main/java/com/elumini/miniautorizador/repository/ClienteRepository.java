package com.elumini.miniautorizador.repository;

import com.elumini.miniautorizador.model.Cliente;
import com.elumini.miniautorizador.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCpf(String cpf);

    @Query("SELECT e FROM Cliente c JOIN c.endereco e WHERE c.id = :idCliente")
    Optional<Endereco> obterEnderecoCliente(Integer idCliente);

}
