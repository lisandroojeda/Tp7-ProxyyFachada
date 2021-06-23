package ar.unrn.persistencia;

import ar.unrn.model.Persona;
import ar.unrn.model.PersonaDao;
import ar.unrn.model.Telefono;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcPersonaDao implements PersonaDao { // objeto real

    private Connection obtenerConexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/dbpatronproxy?useSSL=false";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Persona personaPorId(int id) {
        String sql = "select p.nombre,t.numero "
                + "from personas p, telefonos t "
                + "where p.id = t.idpersona and p.id = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement statement = conn.prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Set<Telefono> telefonos = new HashSet<Telefono>();
            String nombrePersona = null;
            while (result.next()) {
                nombrePersona = result.getString(1);
                telefonos.add(new Telefono(result.getString(2)));
            }
            return new Persona(id, nombrePersona, telefonos);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Telefono> obtenerTelefonosPorIdPersona(int idPersona) {
        String sql = "select numero "
                + "from telefonos "
                + "where idPersona = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            ResultSet result = statement.executeQuery();
            Set<Telefono> telefonos = new HashSet<>();
            while (result.next()) {
                telefonos.add(new Telefono(result.getString("numero")));
            }
            return telefonos;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener la lista de telefonos.", e);
        }
    }
}



