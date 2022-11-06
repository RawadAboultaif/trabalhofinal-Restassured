package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class PessoaEnderecoDTO extends PessoaDTO{

    private List<EnderecoDTO> enderecos;
}
