package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class ListaCompletaUsuarioDTO extends PessoaEnderecoDTO {

    private List<ContatoDTO> contatos;
}
