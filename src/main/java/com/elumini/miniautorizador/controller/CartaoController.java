package com.elumini.miniautorizador.controller;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.dto.TransacaoDto;
import com.elumini.miniautorizador.exception.CartaoExistenteException;
import com.elumini.miniautorizador.exception.CartaoInexistenteException;
import com.elumini.miniautorizador.exception.SaldoInsuficienteException;
import com.elumini.miniautorizador.exception.SenhaIncorretaException;
import com.elumini.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/cartoes")
    public ResponseEntity<Object> criar(@RequestBody @Valid CartaoCriacaoDto cartaoDto) {
        try {
            return new ResponseEntity<>(cartaoService.criar(cartaoDto), HttpStatus.CREATED);
        } catch (CartaoExistenteException exception) {
            return new ResponseEntity<>(cartaoDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<Double> obterSaldo(@PathVariable(required = true) String numeroCartao) {
        return new ResponseEntity<>(cartaoService.obterSaldo(numeroCartao), HttpStatus.OK);
    }


    @PostMapping("/transacoes")
    public ResponseEntity<Void> realizarTransacao(@RequestBody @Valid TransacaoDto transacao) {
        cartaoService.realizarTransacao(transacao);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleSaldoInsuficienteException() {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("SALDO_INSUFICIENTE");
    }

    @ExceptionHandler(CartaoInexistenteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleCartaoInexistenteException() {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("CARTAO_INEXISTENTE ");
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleSenhaIncorretaException() {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("SENHA_INVALIDA ");
    }

}
