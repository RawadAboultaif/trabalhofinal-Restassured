package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.services.EnderecoService;
import br.com.dbccompany.services.PessoaService;
import br.com.dbccompany.utils.Util;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.lang.Integer.compare;

public class EnderecoAceitacao {

    EnderecoService serviceEndereco = new EnderecoService();
    PessoaService servicePessoa = new PessoaService();

    public EnderecoAceitacao() {
    }

    @Test
    public void deveAdicionarNovoEndereco() throws IOException {

        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        Assert.assertEquals(resultServiceEndereco.getCidade(), novoEndereco.getCidade());

        serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    @Test
    public void deveBuscarEnderecoPorid() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        EnderecoDTO resultResponseEndereco = serviceEndereco.buscarEndereco(resultServiceEndereco.getIdEndereco());

        Assert.assertEquals(novoEndereco.getCidade(), resultResponseEndereco.getCidade());

        serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    @Test
    public void deveAtualizarEnderecoPorIdEndereco() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        novoEndereco.setCidade("Brasilia");
        EnderecoDTO resultResponseEndereco =
                serviceEndereco.atualizarEndereco(novoEndereco, resultServiceEndereco.getIdEndereco());

        Assert.assertEquals(novoEndereco.getCidade(), resultResponseEndereco.getCidade());

        serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    @Test
    public void deveDeletarEnderecoPorIdEndereco() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        Response responseService = serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());

        Assert.assertEquals(responseService.getStatusCode(), 200);

        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    @Test
    public void testeDeveListarUsuarios() {
        ListaEnderecoDTO resultservice = serviceEndereco.listarUsuarios(0, 50);

        compare(resultservice.getSize(), 50);
    }

    @Test void testeDeveBuscarEnderecoPorPais() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        EnderecoDTO[] resultListaEndereco = serviceEndereco.buscarEnderecoPorPais(novoEndereco.getPais());

        Assert.assertNotNull(resultListaEndereco);

        serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }

    @Test void testeDeveBuscarEnderecoPorIdPessoa() throws IOException {
        PessoaCreateDTO novaPessoa = Util.novaPessoa();
        PessoaDTO resultServicePessoa = servicePessoa.adicionarUsuarioNovo(novaPessoa);
        EnderecoCreateDTO novoEndereco= Util.novoEndereco();

        novoEndereco.setIdPessoa(resultServicePessoa.getIdPessoa());
        EnderecoDTO resultServiceEndereco =
                serviceEndereco.adicionarEnderecoNovo(novoEndereco, novoEndereco.getIdPessoa());

        EnderecoDTO[] resultListaEndereco = serviceEndereco.buscarEnderecoPorPais(novoEndereco.getIdPessoa());

        Assert.assertNotNull(resultListaEndereco);

        serviceEndereco.deletarEndereco(resultServiceEndereco.getIdEndereco());
        servicePessoa.deletarUser(resultServicePessoa.getIdPessoa());
    }
}
