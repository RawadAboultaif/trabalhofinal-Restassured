package br.com.dbccompany.services;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

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
                        .header("Authorization", tokenAdm)
                        .body(jsonBody)
                .when()
                        .put(baseUrl + "/pessoa/" + idPessoa)
                .then()
                        .statusCode(200)
                        .extract().as(PessoaDTO.class)
                ;
        return result;
    }

    public ListaPessoasDTO listarUsuarios(Integer pagina, Integer tamanhoDasPaginas) {

        ListaPessoasDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("pagina", pagina)
                        .queryParam("tamanhoDasPaginas", tamanhoDasPaginas)
                .when()
                        .get(baseUrl + "/pessoa")
                .then()
                        .extract().as(ListaPessoasDTO.class)
                ;
        return result;
    }

    public PessoaDTO buscarPorCpf(String cpf) {

        PessoaDTO result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                    .when()
                        .get(baseUrl + "/pessoa/"+cpf+"/cpf")
                    .then()
                        .extract().as(PessoaDTO.class)
                ;
        return result;
    }

    public PessoaDTO[] buscarPorNome(String nome) {

        PessoaDTO[] result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("nome", nome)
                  .when()
                        .get(baseUrl + "/pessoa/byname")
                .then()
                        .extract().as(PessoaDTO[].class)
                ;
        return result;
    }

    public PessoaContatosDTO[] buscarListaComContatos(Integer IdPessoa) {

        PessoaContatosDTO[] result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", IdPessoa)
                .when()
                        .get(baseUrl + "/pessoa/lista-com-contatos")
                .then()
                        .extract().as(PessoaContatosDTO[].class)
                ;
        return result;
    }

//    public PessoaDTO[] buscarDataNascimento(String dtInicial, String dtFinal) {
//
//        PessoaDTO[] result =
//                given()
//                        .log().all()
//                        .contentType(ContentType.JSON)
//                        .header("Authorization", tokenAdm)
//                .when()
//                        .get(baseUrl + "/pessoa/data-nascimento?data="+dtInicial+"&dtFinal="+dtFinal)
//                .then()
//                        .statusCode(200)
//                        .extract().as(PessoaDTO[].class)
//                ;
//        return result;
//    }



    // Cenários Negativos:

    public Response adicionarUsuariocomCamposNulos(Object jsonBody) {
        Response response =
                given()
                        .log().all()
                        .header("Authorization", tokenAdm)
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                        .when()
                        .post(baseUrl + "/pessoa")
                        .then()
                        .log().all()
                        .statusCode(400)
                        .extract().response()
                ;

        return response;
    }

    public Response deletarUserInexistente(Integer idPessoa) {
        Response response =
                given()
                        .header("Authorization", tokenAdm)
                        .when()
                        .delete(baseUrl + "/pessoa/"+idPessoa)
                        .then()
                        .log().all()
                        .statusCode(404)
                        .extract().response()
                ;
        return response;
    }

    public Response atualizarUserInexistente(Integer idPessoaInexistente) {
        Response response =
                given()
                        .header("Authorization", tokenAdm)
                        .when()
                        .put(baseUrl + "/pessoa/"+idPessoaInexistente)
                        .then()
                        .log().all()
                        .statusCode(404)
                        .extract().response()
                ;
        return response;
    }

        public Response listarUsuariosEmPaginaInvalida(Integer pagina, Integer tamanhoDasPaginas) {

            Response response =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("pagina", pagina)
                        .queryParam("tamanhoDasPaginas", tamanhoDasPaginas)
                        .when()
                        .get(baseUrl + "/pessoa")
                        .then()
                        .log().all()
                        .statusCode(500)
                        .extract().response()
                ;
            return response;
    }
    public Response buscarPorCpfIncorreto(int cpf) {

        Response result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .when()
                        .get(baseUrl + "/pessoa/"+cpf+"/cpf")
                        .then()
                        .log().all()
                        .statusCode(500)
                        .extract().response()
                ;
        return result;
    }
    public Response buscarListaComContatosPorIDInvalido (Integer idPessoa) {

        Response result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("idPessoa", idPessoa)
                        .when()
                        .get(baseUrl + "/pessoa/lista-com-contatos")
                        .then()
                        .log().all()
                        .statusCode(404)
                        .extract().response()
                ;
        return result;
    }

    public Response buscarPessoaPassandoNumeroInvesDeString(Integer numero) {

        Response result =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", tokenAdm)
                        .queryParam("numero", numero)
                        .when()
                        .get(baseUrl + "/pessoa/byname")
                        .then()
                        .log().all()
                        .statusCode(400)
                        .extract().response()
                ;
        return result;
    }
}
