package br.com.dbccompany.services;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class EnderecoService {

    String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api/endereco/";
    String tokenAdm = new Login().autenticacaoAdmin();

    public EnderecoDTO adicionarEnderecoNovo(Object jsonBody, Integer idPessoa) {
        EnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .post(baseUrl + "/pessoa")
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO.class)
                ;

        return result;
    }
}
