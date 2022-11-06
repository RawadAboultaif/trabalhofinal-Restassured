package br.com.dbccompany.services;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.utils.Login;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ContatoService {

    String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdm = new Login().autenticacaoAdmin();

    @Test
    public ContatoDTO adicionarContato(Object jsonBody, Integer idPessoa) {
        ContatoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .post(baseUrl + "/contato/"+idPessoa)
                .then()
                        .log().all()
                        .extract().as(ContatoDTO.class)
                ;
        return result;
    }

    @Test
    public Response deletarContato(Integer idContato) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .delete(baseUrl + "/contato/"+idContato)
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response()
                ;
        return result;
    }

    public ContatoDTO[] buscarContatoPorIdPessoa(Integer idPessoa) {
        ContatoDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .get(baseUrl + "/contato/"+idPessoa)
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(ContatoDTO[].class)
                ;
        return result;
    }

    public ContatoDTO atualizarContato(Object jsonBody, Integer idContato) {
        ContatoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .put(baseUrl + "/contato/"+idContato)
                .then()
                        .log().all()
                        .extract().as(ContatoDTO.class)
                ;
        return result;
    }

    public ContatoDTO[] listarContatos() {

        ContatoDTO[] result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                .when()
                        .get(baseUrl + "/contato")
                .then()
                        .statusCode(200)
                        .extract().as(ContatoDTO[].class)
                ;
        return result;
    }

    //Cenários Negativos:

    @Test
    public Response adicionarContatoComCampoNulo(Object jsonBody, Integer idPessoa) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                .when()
                        .post(baseUrl + "/contato/"+idPessoa)
                .then()
                        .log().all()
                        .extract().response()
                ;
        return result;
    }

    @Test
    public Response deletarContatoInexistente(Integer idContato) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .delete(baseUrl + "/contato/"+idContato)
                .then()
                        .log().all()
                        .statusCode(404)
                        .extract().response()
                ;
        return result;
    }

    @Test
    public Response atualizarContatoInexistente(Integer idContato) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .put(baseUrl + "/contato/"+idContato)
                .then()
                        .log().all()
                        .extract().response()
                ;
        return result;
    }

    public Response listarContatosSemAutenticacao() {

        Response result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                .when()
                        .get(baseUrl + "/contato")
                .then()
                        .log().all()
                        .statusCode(403)
                        .extract().response()
                ;
        return result;
    }

    public Response buscarContatoPorIdPessoaInexistente(Integer idPessoa) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .get(baseUrl + "/contato/"+idPessoa)
                .then()
                        .log().all()
                        .statusCode(404)
                        .extract().response()
                ;
        return result;
    }
}
