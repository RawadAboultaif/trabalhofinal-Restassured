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

        params.put("tipo", "Comercial");
        params.put("logradouro", faker.address().streetName());
        params.put("numero", faker.address().streetAddressNumber());
        params.put("complemento", faker.address().lastName());
        params.put("cep", faker.address().zipCode());
        params.put("cidade", faker.address().cityName());
        params.put("estado", faker.address().state());
        params.put("pais", faker.address().country());

        return params;
    }
}
