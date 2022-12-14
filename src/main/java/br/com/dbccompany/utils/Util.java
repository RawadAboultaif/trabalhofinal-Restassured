package br.com.dbccompany.utils;

import br.com.dbccompany.dto.*;
import com.google.gson.Gson;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;



public class Util {
    public static String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public static PessoaCreateDTO novaPessoa() throws IOException {

        JsonManipulation.criarJsonPessoa();
        String jsonBody = lerJson("src/test/resources/data/pessoa.json");
        Gson gson = new Gson();
        PessoaCreateDTO objJson = gson.fromJson(jsonBody, PessoaCreateDTO.class);

        return objJson;
    }

    public static EnderecoCreateDTO novoEndereco() throws IOException {

        JsonManipulation.criarJsonEndereco();
        String jsonBody = lerJson("src/test/resources/data/endereco.json");

        Gson gson = new Gson();
        EnderecoCreateDTO objJson = gson.fromJson(jsonBody, EnderecoCreateDTO.class);

        return objJson;
    }

    public static  ContatoCreateDTO novoContato() throws IOException {

        JsonManipulation.criarJsonContato();
        String jsonBody = lerJson("src/test/resources/data/contato.json");

        Gson gson = new Gson();
        ContatoCreateDTO objJson = gson.fromJson(jsonBody, ContatoCreateDTO.class);

        return objJson;
    }
}