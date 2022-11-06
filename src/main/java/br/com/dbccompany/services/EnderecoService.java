package br.com.dbccompany.services;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

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
                        .pathParam("idPessoa", idPessoa)
                        .queryParam("idPessoa", idPessoa)
                .when()
                        .post(baseUrl + "endereco/{idPessoa}")
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO.class)
                ;
        return result;
    }

    public Response deletarEndereco(int idEndereco) {
        Response result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                .when()
                        .delete(baseUrl + "endereco/"+idEndereco)
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response()
                ;
        return result;
    }

    public EnderecoDTO buscarEndereco(int idEndereco) {
        EnderecoDTO result =
        given()
                .log().all()
                .header("Authorization", tokenAdm)
                .contentType(ContentType.JSON)
                .pathParam("idEndereco", idEndereco)
        .when()
            .get(baseUrl + "endereco/{idEndereco}")
        .then()
                .log().all()
                .extract().as(EnderecoDTO.class)
        ;
        return result;
    }

    public EnderecoDTO atualizarEndereco(Object jsonBody, int idEndereco) {
        EnderecoDTO result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                        .pathParam("idEndereco", idEndereco)
                .when()
                        .put(baseUrl + "endereco/{idEndereco}")
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO.class)
                ;
        return result;
    }

    public ListaEnderecoDTO listarUsuarios(Integer pagina, Integer tamanhoDasPaginas) {

        ListaEnderecoDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("pagina", pagina)
                        .queryParam("tamanhoDasPaginas", tamanhoDasPaginas)
                .when()
                        .get(baseUrl + "/endereco")
                .then()
                        .extract().as(ListaEnderecoDTO.class);
        return result;
    }

    public EnderecoDTO[] buscarEnderecoPorPais(String pais) {
        EnderecoDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .queryParam("País", pais)
                .when()
                        .get(baseUrl + "endereco/retorna-por-pais")
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO[].class)
                ;
        return result;
    }

    public EnderecoDTO[] buscarEnderecoPorPais(Integer idPessoa) {
        EnderecoDTO[] result =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .queryParam("idPessoa", idPessoa)
                .when()
                        .get(baseUrl + "endereco/retorna-por-id-pessoa")
                .then()
                        .log().all()
                        .extract().as(EnderecoDTO[].class)
                ;
        return result;
    }
}
