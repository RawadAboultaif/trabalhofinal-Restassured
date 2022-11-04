package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.PessoaCreateDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.ResponseDTO;
import br.com.dbccompany.services.PessoaService;

import br.com.dbccompany.utils.Util;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class PessoaAceitacao {
    PessoaService service = new PessoaService();

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


        Assert.assertEquals(resultBody.getEmail(), novaPessoa.getDataNascimento());
        Assert.assertEquals(resultBody.getCpf(), novaPessoa.getCpf());

        service.deletarUser(resultService.getIdPessoa());
    }
}
