package com.elumini.miniautorizador.service;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.dto.CartaoDto;
import com.elumini.miniautorizador.dto.TransacaoDto;
import com.elumini.miniautorizador.exception.CartaoExistenteException;
import com.elumini.miniautorizador.exception.CartaoInexistenteException;
import com.elumini.miniautorizador.exception.SenhaIncorretaException;
import com.elumini.miniautorizador.model.Cartao;
import com.elumini.miniautorizador.repository.CartaoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.elumini.miniautorizador.model.Cartao.fromDTO;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoDto criar(CartaoCriacaoDto cartaoCriacaoDto) {


        Cartao cartao = fromDTO(cartaoCriacaoDto);
        boolean cartaoCriado = cartaoRepository.existsById(cartaoCriacaoDto.getNumeroCartao());
        if (cartaoCriado) {
            throw new CartaoExistenteException();
        }
        cartao.setSaldo(500.00);
        cartao.setDataCriacao(LocalDate.now());
        cartaoRepository.saveAndFlush(cartao);

        return new CartaoDto(cartao.getNumeroCartao(), cartao.getSenha());
    }

    public Double obterSaldo(String numeroDeCartaoExistente) {
        cartaoRepository.findById(numeroDeCartaoExistente).orElseThrow(CartaoInexistenteException::new);
        return cartaoRepository.obterSaldoDoCartao(numeroDeCartaoExistente);
    }


    @Transactional
    public void realizarTransacao(TransacaoDto transacao) {

        Cartao cartaoValido = cartaoRepository.findById(transacao.getNumeroCartao()).orElseThrow(CartaoInexistenteException::new);
        if (StringUtils.compare(cartaoValido.getSenha(), transacao.getSenhaCartao()) != 0) {
            throw new SenhaIncorretaException();
        }
        cartaoValido.validarAtribuirNovoSaldo(transacao.getValor());
        cartaoRepository.save(cartaoValido);
    }
}
