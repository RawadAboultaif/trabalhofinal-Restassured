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


import static java.lang.Integer.compare;



public class PessoaAceitacao {
    PessoaService service = new PessoaService();

    ContatoService serviceContato = new ContatoService();
    public PessoaAceitacao() {
    }

    @Test
    public void deveAdicionarNovoUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();

        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        Assert.assertEquals(resultService.getCpf(), novaPessoa.getCpf());

        service.deletarUser(resultService.getIdPessoa());
    }
    @Test
    public void testeDeveDeletarUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();

        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        Response responseService = service.deletarUser(resultService.getIdPessoa());

        Assert.assertEquals(responseService.getStatusCode(), 200);
    }

    @Test
    public void testeDeveAtualizarUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        novaPessoa.setCpf("59874563254");
        novaPessoa.setEmail("update@email.com");
        PessoaDTO resultBody = service.atualizarUsuario(novaPessoa, resultService.getIdPessoa());


        Assert.assertEquals(resultBody.getEmail(), novaPessoa.getEmail());
        Assert.assertEquals(resultBody.getCpf(), novaPessoa.getCpf());

        service.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeDeveListarUsusarios() {
        ListaPessoasDTO resultservice = service.listarUsuarios(0, 50);

        compare(resultservice.getSize(), 50);
    }
    @Test
    public void testeDeveBuscarCpf() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        PessoaDTO resultBody = service.buscarPorCpf(novaPessoa.getCpf());

        Assert.assertEquals(resultService.getCpf(), resultBody.getCpf());

        service.deletarUser(resultService.getIdPessoa());
    }

//    @Test
//    public void testeDeveBuscarPorDataNascimento() {
//        PessoaDTO[] resultService = service.buscarDataNascimento("10/10/1990", "10/10/2010");
//
//        Assert.assertNotNull(resultService);
//    }

    @Test
    public void testeBuscarUsuarioPorNome() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        PessoaDTO[] resultBody = service.buscarPorNome(novaPessoa.getNome());

        Assert.assertEquals(resultService.getEmail(), resultBody[0].getEmail());

        service.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeBuscarListaDeContatosUsusario() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = service.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        ContatoDTO responseContatos = serviceContato.adicionarContato(novoContato, resultService.getIdPessoa());

        PessoaContatosDTO[] resulResponse = service.buscarListaComContatos(resultService.getIdPessoa());

        Assert.assertNotNull(resulResponse);

        serviceContato.deletarContato(responseContatos.getIdContato());
        service.deletarUser(resultService.getIdPessoa());
    }
}
