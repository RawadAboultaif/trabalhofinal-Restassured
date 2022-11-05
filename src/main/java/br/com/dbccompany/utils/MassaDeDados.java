package br.com.dbccompany.utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MassaDeDados {

    public static HashMap<String, Object> criarPessoa() {
        Faker faker = new Faker();

        HashMap<String, Object> params = new HashMap<>();

        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");

        params.put("nome", faker.name().firstName());
        params.put("dataNascimento", data.format(faker.date().birthday()));
        params.put("cpf", faker.number().digits(11));
        params.put("email", faker.internet().emailAddress());

        return params;
    }

    public static HashMap<String, Object> criarEndereco() {
        Faker faker = new Faker();

        HashMap<String, Object> params = new HashMap<>();


        params.put("tipo", "COMERCIAL");
        params.put("logradouro", (faker.address().streetName()).replaceAll(" ", ""));
        params.put("numero", faker.address().streetAddressNumber());
        params.put("complemento", faker.address().lastName().replaceAll(" ", ""));
        params.put("cep", "99998888");
        params.put("cidade", faker.address().cityPrefix().replaceAll(" ", ""));
        params.put("estado", faker.address().state().replaceAll(" ", ""));
        params.put("pais", faker.address().country().replaceAll(" ", ""));



        return params;
    }


    public static HashMap<String, Object> criarContatos() {
        Faker faker = new Faker();

        HashMap<String, Object> params = new HashMap<>();


        params.put("tipoContato", "COMERCIAL");
        params.put("telefone", faker.number().digits(9));
        params.put("descricao", "whatsapp");


        return params;
    }
}
