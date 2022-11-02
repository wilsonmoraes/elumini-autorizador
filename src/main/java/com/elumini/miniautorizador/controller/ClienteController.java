package com.elumini.miniautorizador.controller;

import com.elumini.miniautorizador.dto.ClienteCriacaoDto;
import com.elumini.miniautorizador.dto.ClienteDto;
import com.elumini.miniautorizador.dto.EnderecoDto;
import com.elumini.miniautorizador.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cliente")
    public ResponseEntity<Object> criar(@RequestBody @Valid ClienteCriacaoDto clienteCriacaoDto){

        try {
            return new ResponseEntity<Object>(clienteService.criar(clienteCriacaoDto), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<Object>(clienteCriacaoDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ClienteDto> obterSaldo(@PathVariable(required = true) Integer idCliente){
        ClienteDto clienteDto = clienteService.obterCliente(idCliente);

        return clienteDto != null ? new ResponseEntity<ClienteDto>(clienteDto, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{idCliente}/endereco")
    public ResponseEntity<EnderecoDto> obterEnderecoCliente(@PathVariable(required = true) Integer idCliente){
        EnderecoDto enderecoDto = clienteService.obterEnderecoCliente(idCliente);
        return enderecoDto != null ? new ResponseEntity<EnderecoDto>(enderecoDto, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

}
