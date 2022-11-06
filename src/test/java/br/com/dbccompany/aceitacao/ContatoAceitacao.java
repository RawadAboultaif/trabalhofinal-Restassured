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


    //Cenários Negativos
    @Test
    public void testeAdicionarNovoContatoComCamposNulos() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        novoContato.setTelefone(null);
        Response resultContato = service.adicionarContatoComCampoNulo(novoContato, resultService.getIdPessoa());


        Assert.assertEquals(resultContato.getStatusCode(), 404);

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeDeletarContatoInexistente() throws IOException {
        Integer idContatoInexistente = -8;

        Response responseService = service.deletarContatoInexistente(idContatoInexistente);

        Assert.assertEquals(responseService.getStatusCode(), 404);
    }

    @Test
    public void testeAtualizarContatoInexistente() throws IOException {
        Integer idContatoInexistente = 3458;

        Response responseService = service.atualizarContatoInexistente(idContatoInexistente);

        Assert.assertEquals(responseService.getStatusCode(), 404);
    }

    @Test
    public void testeListarTodosContatosSemAutenticacao() {
        Response resultResponse = service.listarContatosSemAutenticacao();

        Assert.assertEquals(resultResponse.getStatusCode(), 403);
    }
    @Test
    public void deveListarContatosPorIdPessoaInexistente() throws IOException {
        Integer idPessoaInexistente = -36;


        Response resultResponse = service.buscarContatoPorIdPessoaInexistente(idPessoaInexistente);

        Assert.assertEquals(resultResponse.getStatusCode(), 404);
    }
}
