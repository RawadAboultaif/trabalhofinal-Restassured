package br.com.dbccompany.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class JsonManipulation {
    public static void criarJsonPessoa() {
        String pathJson = "src/test/resources/data/pessoa.json";

        HashMap<String, Object> dadosFaker = MassaDeDados.criarPessoa();


        try(PrintWriter out = new PrintWriter(new FileWriter(pathJson))) {
            out.write(dadosFaker.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void criarJsonEndereco() {
        String pathJson = "src/test/resources/data/endereco.json";

        HashMap<String, Object> dadosFaker = MassaDeDados.criarEndereco();


        try(PrintWriter out = new PrintWriter(new FileWriter(pathJson))) {
            out.write(dadosFaker.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}