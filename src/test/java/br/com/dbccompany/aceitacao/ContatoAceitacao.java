package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.services.ContatoService;
import br.com.dbccompany.services.PessoaService;
import br.com.dbccompany.utils.Util;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ContatoAceitacao {

    ContatoService service = new ContatoService();

    public ContatoAceitacao() {
    }

    PessoaService servicePessoa = new PessoaService();


    @Test
    public void deveAdicionarNovoContato() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        ContatoDTO resultContato = service.adicionarContato(novoContato, resultService.getIdPessoa());

        Assert.assertEquals(resultContato.getDescricao(), novoContato.getDescricao());

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void deveDeletarContatoExistente() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        ContatoDTO resultContato = service.adicionarContato(novoContato, resultService.getIdPessoa());

        Response responseService = service.deletarContato(resultContato.getIdContato());

        Assert.assertEquals(responseService.getStatusCode(), 200);

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void deveListarContatosPorIdPessoa() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        service.adicionarContato(novoContato, resultService.getIdPessoa());

        ContatoDTO[] resultContato = service.buscarContatoPorIdPessoa(novoContato.getIdPessoa());

        Assert.assertEquals(novoContato.getDescricao(), resultContato[0].getDescricao());

        service.deletarContato(resultContato[0].getIdContato());
        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void deveAtualizarContatoExistente() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        ContatoDTO responseContato = service.adicionarContato(novoContato, resultService.getIdPessoa());
        novoContato.setDescricao("VAI PASSAR DE PRIMEIRA !!");

        ContatoDTO resulresponse = service.atualizarContato(novoContato, responseContato.getIdContato());

        Assert.assertEquals(resulresponse.getDescricao(), novoContato.getDescricao());

        service.deletarContato(responseContato.getIdContato());
        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void deveListarTodosContatos() {
        ContatoDTO[] resultResponse = service.listarContatos();

        Assert.assertNotNull(resultResponse);
    }
}
