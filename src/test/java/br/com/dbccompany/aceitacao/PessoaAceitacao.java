package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.services.ContatoService;
import br.com.dbccompany.services.EnderecoService;
import br.com.dbccompany.services.PessoaService;

import br.com.dbccompany.utils.Util;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.lang.Integer.compare;

public class PessoaAceitacao {
    PessoaService servicePessoa = new PessoaService();

    ContatoService serviceContato = new ContatoService();

    EnderecoService serviceEndereco = new EnderecoService();
    public PessoaAceitacao() {
    }

    @Test
    public void deveAdicionarNovoUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();

        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        Assert.assertEquals(resultService.getCpf(), novaPessoa.getCpf());

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }
    @Test
    public void testeDeveDeletarUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();

        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        Response responseService = servicePessoa.deletarUser(resultService.getIdPessoa());

        Assert.assertEquals(responseService.getStatusCode(), 200);
    }

    @Test
    public void testeDeveAtualizarUsuario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        novaPessoa.setCpf("59874563254");
        novaPessoa.setEmail("update@email.com");
        PessoaDTO resultBody = servicePessoa.atualizarUsuario(novaPessoa, resultService.getIdPessoa());


        Assert.assertEquals(resultBody.getEmail(), novaPessoa.getEmail());
        Assert.assertEquals(resultBody.getCpf(), novaPessoa.getCpf());

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeDeveListarUsuarios() {
        ListaPessoasDTO resultservice = servicePessoa.listarUsuarios(0, 50);

        compare(resultservice.getSize(), 50);
    }
    @Test
    public void testeDeveBuscarCpf() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        PessoaDTO resultBody = servicePessoa.buscarPorCpf(novaPessoa.getCpf());

        Assert.assertEquals(resultService.getCpf(), resultBody.getCpf());

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeBuscarUsuarioPorNome() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        PessoaDTO[] resultBody = servicePessoa.buscarPorNome(novaPessoa.getNome());

        Assert.assertEquals(resultService.getEmail(), resultBody[0].getEmail());

        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void testeBuscarListaDeContatosUsuario() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultService = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultService.getIdPessoa());
        ContatoDTO responseContatos = serviceContato.adicionarContato(novoContato, resultService.getIdPessoa());

        PessoaContatosDTO[] resulResponse = servicePessoa.buscarListaComContatos(resultService.getIdPessoa());

        Assert.assertNotNull(resulResponse);

        serviceContato.deletarContato(responseContatos.getIdContato());
        servicePessoa.deletarUser(resultService.getIdPessoa());
    }

    @Test
    public void deveBuscarListaComEnderecosUsusario() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultEndereco = serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        PessoaEnderecoDTO[] resultService =
                servicePessoa.buscarListaEnderecosUsuario(resultServicePessoa.getIdPessoa());

        Assert.assertEquals(resultEndereco.getIdPessoa(), resultService[0].getEnderecos().get(0).getIdPessoa());
        serviceEndereco.deletarEndereco(resultEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }
    @Test
    public void testeDeveBuscarListaCompletaDoUsusario() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);

        EnderecoCreateDTO novoEndereco= Util.novoEndereco();
        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        ContatoCreateDTO novoContato = Util.novoContato();
        novoContato.setIdPessoa(resultServicePessoa.getIdPessoa());
        ContatoDTO responseContatos =
                serviceContato.adicionarContato(novoContato, resultServicePessoa.getIdPessoa());

        ListaCompletaUsuarioDTO[] responseService =
                servicePessoa.buscarListaCompletaUsuario(resultServicePessoa.getIdPessoa());

        Assert.assertNotNull(responseService);

        serviceContato.deletarContato(responseContatos.getIdContato());
        serviceEndereco.deletarEndereco(resultEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    // Cenários de Testes Negativos:

    @Test
    public void testeAdcionarUsuarioComCamposNulos() throws IOException {
    //não existe na API nenhuma informação de que para adicionar uma Pessoa, os campos são obrigatórios.

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        novaPessoa.setCpf(null);

        Response resultService = servicePessoa.adicionarUsuariocomCamposNulos(novaPessoa);

        Assert.assertEquals(resultService.getStatusCode(), 400);

    }
    @Test
    public void testeDeletarUsuarioInexistente() {

        Integer idPessoaInexistente = -80;

        Response responseService = servicePessoa.deletarUserInexistente(idPessoaInexistente);

        Assert.assertEquals(responseService.getStatusCode(), 404);
    }

    @Test
    public void testeAtualizarUsuarioInexitente() {

        Integer idPessoaInexistente = -1;

        Response responseService = servicePessoa.atualizarUserInexistente(idPessoaInexistente);


        Assert.assertEquals(responseService.getStatusCode(), 404);
    }

    @Test
    public void testeListarUsuariosComPaginaInvalida() {
        Response resultservice = servicePessoa.listarUsuariosEmPaginaInvalida(-3, 50);

        Assert.assertEquals(resultservice.getStatusCode(), 500);
    }

    @Test
    public void testeDeveBuscarCpfIncorreto() {

       int cpfIncorreto = 1;

        Response resultservice = servicePessoa.buscarPorCpfIncorreto(cpfIncorreto);

        Assert.assertEquals(resultservice.getStatusCode(), 400);
    }

    @Test
    public void testeBuscarListaDeContatosUsuarioPorIDInvalido() {
        Integer idInvalido = -32;


        Response resulResponse = servicePessoa.buscarListaComContatosPorIDInvalido(idInvalido);

        Assert.assertEquals(resulResponse.getStatusCode(), 404);

    }

    @Test
    public void buscarPessoaPassandoNumeroInvésDeString() {
        Integer numero = 3;

        Response resulResponse = servicePessoa.buscarPessoaPassandoNumeroInvesDeString(numero);

        Assert.assertEquals(resulResponse.getStatusCode(), 400);
    }
}
