package br.com.dbccompany.services;

import br.com.dbccompany.dto.EnderecoCreateDTO;
import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class EnderecoService {

    String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api/";
    String tokenAdm = new Login().autenticacaoAdmin();

    public EnderecoDTO adicionarEnderecoNovo(Object jsonBody, int idPessoa) {
        EnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .post(baseUrl + "endereco/" + idPessoa)
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO.class)
                ;

        return result;
    }

    public void deletarEndereco(int idEndereco) {

                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .delete(baseUrl + "endereco/"+idEndereco)
                .then()
                        .log().all()
                        .statusCode(200)
                ;
    }
}
