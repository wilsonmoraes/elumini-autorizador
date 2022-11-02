package com.elumini.miniautorizador.service;

import com.elumini.miniautorizador.dto.ClienteCriacaoDto;
import com.elumini.miniautorizador.dto.ClienteDto;
import com.elumini.miniautorizador.dto.EnderecoDto;
import com.elumini.miniautorizador.exception.HandleException;
import com.elumini.miniautorizador.model.Cliente;
import com.elumini.miniautorizador.model.Endereco;
import com.elumini.miniautorizador.repository.ClienteRepository;
import com.elumini.miniautorizador.utils.ValidacoesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HandleException handleException;

    public ClienteDto criar(ClienteCriacaoDto clienteRequest) {

        Object clienteExistente = clienteRepository.findByCpf(clienteRequest.getCpf()).isPresent() ? handleException.throwExcecaoDeValidacao(ValidacoesEnum.CLIENTE_EXISTENTE) : true;

        Cliente cliente = clienteRepository.save(new Cliente(clienteRequest));

        return new ClienteDto(cliente);
    }

    public ClienteDto obterCliente(Integer idCliente) {

        Optional<Cliente> clienteDto = clienteRepository.findById(idCliente);

        return clienteDto.isPresent() ? new ClienteDto(clienteDto.get()) : null;
    }

    public EnderecoDto obterEnderecoCliente(Integer idCliente) {

        Optional<Endereco> endereco = clienteRepository.obterEnderecoCliente(idCliente);

       return endereco.isPresent() ? new EnderecoDto(endereco.get()) : null;
    }
}
