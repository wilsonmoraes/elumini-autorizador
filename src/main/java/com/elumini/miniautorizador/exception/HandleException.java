package com.elumini.miniautorizador.exception;

import com.elumini.miniautorizador.utils.ValidacoesEnum;
import org.springframework.stereotype.Component;

@Component
public class HandleException {

    public Object throwExcecaoDeValidacao(ValidacoesEnum erro) {
       switch (erro){
           case CARTAO_EXISTENTE:
               throw new CartaoExistenteException();
           case CARTAO_INEXISTENTE:
               throw new CartaoInexistenteException();
           case SENHA_INVALIDA:
               throw new SenhaIncorretaException();
           case SALDO_INSUFICIENTE:
               throw new SaldoInsuficienteException();
           case CLIENTE_EXISTENTE:
               throw  new ClienteExistenteException();
           default:
               return null;
       }
    }
}
