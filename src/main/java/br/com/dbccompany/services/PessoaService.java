package br.com.dbccompany.services;

import br.com.dbccompany.dto.PessoaCreateDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.ResponseDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PessoaService {

    String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdm = new Login().autenticacaoAdmin();
    public PessoaDTO adicionarUsuarioNovo(Object jsonBody) {
        PessoaDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .post(baseUrl + "/pessoa")
                .then()
                        .log().all()
                        .extract().as(PessoaDTO.class)
                ;

        return result;
    }

    public Response deletarUser(Integer idPessoa) {
        Response response =
                given()
                        .header("Authorization", tokenAdm)
                .when()
                        .delete(baseUrl + "/pessoa/"+idPessoa)
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response()
                ;
        return response;
    }
    public PessoaDTO atualizarUsuario(Object jsonBody, Integer idPessoa) {

        PessoaDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                        .when()
                        .put(baseUrl + "/pessoa/" + idPessoa)
                        .then()
                        .statusCode(200)
                        .extract().as(PessoaDTO.class)
                ;
        return result;
    }
}
