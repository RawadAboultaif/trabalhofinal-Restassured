package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.EnderecoCreateDTO;
import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PessoaCreateDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.services.EnderecoService;
import br.com.dbccompany.services.PessoaService;
import br.com.dbccompany.utils.Util;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class EnderecoAceitacao {

    EnderecoService serviceEndereco = new EnderecoService();
    PessoaService servicePessoa = new PessoaService();

    public EnderecoAceitacao() {
    }

    @Test
    public void deveAdicionarNovoUsuario() throws IOException {

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
}
