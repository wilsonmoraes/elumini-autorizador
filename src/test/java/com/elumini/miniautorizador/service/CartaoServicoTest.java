package com.elumini.miniautorizador.service;

import com.elumini.miniautorizador.dto.CartaoCriacaoDto;
import com.elumini.miniautorizador.dto.CartaoDto;
import com.elumini.miniautorizador.dto.TransacaoDto;
import com.elumini.miniautorizador.model.Cartao;
import com.elumini.miniautorizador.model.Cliente;
import com.elumini.miniautorizador.model.Endereco;
import com.elumini.miniautorizador.exception.CartaoExistenteException;
import com.elumini.miniautorizador.exception.CartaoInexistenteException;
import com.elumini.miniautorizador.exception.SaldoInsuficienteException;
import com.elumini.miniautorizador.exception.SenhaIncorretaException;
import com.elumini.miniautorizador.repository.CartaoRepository;
import com.elumini.miniautorizador.repository.ClienteRepository;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartaoServicoTest {

    @Autowired(required = true)
    private CartaoService servicoDeCartao;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private void Inicializar(){
        Cliente cliente = new Cliente("44433322211", LocalDate.now(), new ArrayList<>(),
                new Endereco("logradouro", "cidade", "Estado", "complemento"));
        clienteRepository.saveAndFlush(cliente);
        Cartao cartao = new Cartao("123456789", "123456", 700.00, cliente);
        cartaoRepository.saveAndFlush(cartao);
    }

    @Test
    public void deveRetornarSucessoAoCriarCartao(){
        Inicializar();

        CartaoCriacaoDto cartao = new CartaoCriacaoDto("987654321", "123456", "44433322211");

        CartaoDto cartaoCriado = servicoDeCartao.criar(cartao);

        Cartao cartaoResultado = cartaoRepository.getById("987654321");

        assertEquals(500.00, cartaoRepository.findById(cartaoCriado.getNumeroCartao()).get().getSaldo());
        assertEquals(LocalDate.now(), cartaoResultado.getDataCriacao());
        assertEquals(LocalDate.now().plusYears(3).plusMonths(10), cartaoResultado.getValidade());
        assertTrue(cartaoResultado.getCliente().getCpf().equals("44433322211"));
    }

    @Test
    public void deveRetornarErroAoCriarCartaoComNumeroExistente(){
        Inicializar();
        CartaoCriacaoDto cartaoDuplicado = new CartaoCriacaoDto("123456789", "senha", "44433322211");

        try{
            CartaoDto cartaoCriado = servicoDeCartao.criar(cartaoDuplicado);
            fail();
        }
        catch(Exception e){
            assertSame(e.getClass(), CartaoExistenteException.class);
        }
    }

    @Test
    public void deveReotornarSaldoDoCartaoCadastradoParaNumeroExistente(){
        Inicializar();
        String numeroDeCartaoExistente = "123456789";

        Optional<Double> saldoCartao = servicoDeCartao.obterSaldo(numeroDeCartaoExistente);

        assertEquals(700.00, saldoCartao.get());
    }

    @Test
    public void deveReotornarErroParaNumeroInexistente(){
        Inicializar();
        String numeroDeCartaoInexistente = "111111111";

        Optional<Double> saldoCartao = servicoDeCartao.obterSaldo(numeroDeCartaoInexistente);

        assertFalse(saldoCartao.isPresent());

    }

    @Test
    public void deveRetornarTransacaoBemSucedida(){
        Inicializar();
        Double saldoCartao = cartaoRepository.findById("123456789").get().getSaldo();
        TransacaoDto transacao = new TransacaoDto("123456789", "123456", 100.00);

        String resultado = servicoDeCartao.realizarTransacao(transacao);

        Optional<Cartao> cartaoAtualziado = cartaoRepository.findById("123456789");

        assertEquals("OK", resultado);
        assertTrue(cartaoAtualziado.get().getSaldo() == (saldoCartao - transacao.getValor()));
    }

    @Test
    public void deveRetornarErroParaTransacaoUsandoCartaoInexistente(){

        TransacaoDto transacao = new TransacaoDto("123", "123456", 100.00);

        try {
            String resultado = servicoDeCartao.realizarTransacao(transacao);
            fail();
        }catch (Exception e){
            assertSame(e.getClass(), CartaoInexistenteException.class);
        }
    }

    @Test
    public void deveRetornarErroParaTransacaoUsandoSenhaIncorreta(){
        Inicializar();
        TransacaoDto transacao = new TransacaoDto("123456789", "00000", 100.00);

        try {
            String resultado = servicoDeCartao.realizarTransacao(transacao);
            fail();
        }catch (Exception e){
            assertSame(e.getClass(), SenhaIncorretaException.class);
        }
    }

    @Test
    public void deveRetornarErroParaTransacaoUsandoCartaoComSaldoInsuficiente(){
        Inicializar();
        TransacaoDto transacao = new TransacaoDto("123456789", "123456", 100000.00);

        try {
            String resultado = servicoDeCartao.realizarTransacao(transacao);
            fail();
        }catch (Exception e){
            assertSame(e.getClass(), SaldoInsuficienteException.class);
        }
    }

}
