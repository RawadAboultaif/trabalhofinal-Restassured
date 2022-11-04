package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.PessoaCreateDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.services.PessoaService;

import br.com.dbccompany.utils.Util;
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
}
