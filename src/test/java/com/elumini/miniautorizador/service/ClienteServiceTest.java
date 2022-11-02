package com.elumini.miniautorizador.service;

import com.elumini.miniautorizador.dto.ClienteCriacaoDto;
import com.elumini.miniautorizador.dto.ClienteDto;
import com.elumini.miniautorizador.dto.EnderecoCriacaoDto;
import com.elumini.miniautorizador.dto.EnderecoDto;
import com.elumini.miniautorizador.model.Cliente;
import com.elumini.miniautorizador.model.Endereco;
import com.elumini.miniautorizador.exception.ClienteExistenteException;
import com.elumini.miniautorizador.repository.ClienteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    private void Inicializar(){
        clienteRepository.saveAndFlush(new Cliente("44433322211", LocalDate.now(), new ArrayList<>(),
                new Endereco("logradouro", "cidade", "Estado", "complemento")));
    }


    @Test
    public void deveriaRetornarClienteCriado(){
        ClienteCriacaoDto clienteRequest = new ClienteCriacaoDto("11122233344", new EnderecoCriacaoDto("logradouroUnico", "cidade", "Estado", "complemento"));

        ClienteDto clienteDto = clienteService.criar(clienteRequest);

        Cliente cliente = clienteRepository.findById(clienteDto.getId()).get();

        assertEquals(cliente.getId(), clienteDto.getId());
        assertEquals(cliente.getEndereco().getLogradouro(), clienteDto.getEndereco().getLogradouro());

    }

    @Test
    public void deveriaRetornarErroAoCriarUmClienteComCpfDuplicado(){
        ClienteCriacaoDto clienteRequest = new ClienteCriacaoDto("44433322211", new EnderecoCriacaoDto("logradouro", "cidade", "Estado", "complemento"));

        try{
            clienteService.criar(clienteRequest);
            ClienteDto clienteDto = clienteService.criar(clienteRequest);
            fail();
        }
        catch(Exception e){
            assertSame(e.getClass(), ClienteExistenteException.class);
        }
    }

    @Test
    public void deveriaRetornarSucessoParaBuscarDeClienteExistente(){
        Inicializar();
        Cliente cliente = clienteRepository.findByCpf("44433322211").get();
        ClienteDto clienteDto = clienteService.obterCliente(cliente.getId());
        assertEquals("44433322211", clienteDto.getCpf());
    }

    @Test
    public void deveriaRetornarErroParaBuscaDeClienteComIdInexistente(){
        ClienteDto clienteDto = clienteService.obterCliente(99);
        assertNull(clienteDto);
    }

    @Test
    public void deveriaRetornarEnderecoDoCliente(){
        Inicializar();
        Cliente cliente = clienteRepository.findByCpf("44433322211").get();
        EnderecoDto enderecoDto = clienteService.obterEnderecoCliente(cliente.getId());

        assertEquals(enderecoDto.getId(), cliente.getEndereco().getId());
    }

    @Test
    public void deveriaRetornarNullParaBuscaDeEnderecoDeClienteInexistente(){
        EnderecoDto enderecoDto = clienteService.obterEnderecoCliente(99);

        assertNull(enderecoDto);
    }

}
