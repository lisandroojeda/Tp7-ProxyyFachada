package ar.unrn.persistencia;

import ar.unrn.model.DBFacade;


import java.sql.*;
import java.util.*;

public class JdbcFachada implements DBFacade { // objeto real

    private Connection connection;

    @Override
    public void open() {
        try {
            String url = "jdbc:mysql://localhost:3306/dbpatronproxy?useSSL=false";
            String user = "root";
            String password = "";
            this.connection= DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Map<String, String>> queryResultAsAsociation(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, String>> lista = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> filas = new HashMap<>();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int cantidadColumnas = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= cantidadColumnas; i++) {
                    filas.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                lista.add(filas);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener la lista.", e);
        }
    }

    @Override
    public List<String[]> queryResultAsArray(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (resultSet.next()) {
                int cantidadColumnas = resultSet.getMetaData().getColumnCount();
                String[] filas = new String[cantidadColumnas];
                for (int i = 1; i <= cantidadColumnas; i++) {
                    filas[i - 1] = resultSet.getString(i);
                }
                lista.add(filas);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener la lista.", e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Verifique no se puede cerrar la conexion", e);
        }
    }
}



