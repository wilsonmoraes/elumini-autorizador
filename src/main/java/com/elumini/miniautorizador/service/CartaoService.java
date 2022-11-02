package com.elumini.miniautorizador.service;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.dto.CartaoDto;
import com.elumini.miniautorizador.dto.TransacaoDto;
import com.elumini.miniautorizador.exception.HandleException;
import com.elumini.miniautorizador.model.Cartao;
import com.elumini.miniautorizador.model.Cliente;
import com.elumini.miniautorizador.repository.CartaoRepository;
import com.elumini.miniautorizador.repository.ClienteRepository;
import com.elumini.miniautorizador.utils.ValidacoesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private HandleException handleException;
    @Autowired
    private ClienteRepository clienteRepository;

    public CartaoDto criar(CartaoCriacaoDto cartaoCriacaoDto) {

        Optional<Cliente> cliente = clienteRepository.findByCpf(cartaoCriacaoDto.getCpfCliente());

        Object clienteExistente = cliente.isPresent() ? true : handleException.throwExcecaoDeValidacao(ValidacoesEnum.CLIENTE_INEXISTENTE);

        Cartao cartao = new Cartao(cartaoCriacaoDto, cliente.get());
        Object cartaoCriado = cartaoRepository.existsById(cartaoCriacaoDto.getNumeroCartao()) ? handleException.throwExcecaoDeValidacao(ValidacoesEnum.CARTAO_EXISTENTE)
                : cartaoRepository.saveAndFlush(cartao);

        return new CartaoDto((Cartao) cartaoCriado);
    }

    public Optional<Double> obterSaldo(String numeroDeCartaoExistente) {

        Optional<Double> saldoDoCartao = cartaoRepository.obterSaldoDoCartao(numeroDeCartaoExistente);

        return saldoDoCartao;
    }

    public List<CartaoDto> obterCartaoPorCliente(String cpf){
        Optional<List<Cartao>> listaCartoesCliente = cartaoRepository.findByCliente_Cpf(cpf);
        return  listaCartoesCliente.isPresent() ? listaCartoesCliente.get().stream().map(CartaoDto::new).collect(Collectors.toList()) : null;
    }

    @Transactional
    public String realizarTransacao(TransacaoDto transacao) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(transacao.getNumeroCartao());

        Object cartaoValido = cartaoOptional.isPresent() ? true : handleException.throwExcecaoDeValidacao(ValidacoesEnum.CARTAO_INEXISTENTE);
        Cartao cartao = cartaoOptional.get();
        cartaoValido = cartao.getSenha().equals(transacao.getSenha()) ? true : handleException.throwExcecaoDeValidacao(ValidacoesEnum.SENHA_INVALIDA);
        cartao.validarAtribuirNovoSaldo(transacao.getValor(), handleException);

        return "OK";
    }
}
