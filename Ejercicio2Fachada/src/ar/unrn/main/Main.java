package ar.unrn.main;

import ar.unrn.model.DBFacade;
import ar.unrn.persistencia.JdbcFachada;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBFacade fachada = new JdbcFachada();
        fachada.open();
        List<Map<String, String>> listadoAsociado = fachada.queryResultAsAsociation("Select * from telefonos");
        List<String[]> listadoEnArreglo = fachada.queryResultAsArray("Select * from personas");
        fachada.close();


        listadoAsociado.forEach(System.out::println);
        for (String[] string : listadoEnArreglo) {
            System.out.println(Arrays.toString(string));
        }
    }
}
