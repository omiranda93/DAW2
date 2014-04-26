package Practica;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscarmirandabravo
 */
public class AdministradorDAO {
    
    private Connection conn;

    public AdministradorDAO(DataSource ds) {
        try {
            conn = ds.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos",e);
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public List<Administrador> getAdministradorNombreContraseña(String nombre, String contraseña) {
        return getAdministradorQuery("nombre = '" + nombre + "' AND password = '"+contraseña+"'");
    }
    
    private List<Administrador> getAdministradorQuery(String where) {

        List<Administrador> administrador = new ArrayList<Administrador>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT nombre, password FROM Administrador";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            administrador = crearAdministradorListFromRS(rs);
            rs.close();
            
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta",e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return administrador;
        
    }
    
    private List<Administrador> crearAdministradorListFromRS(ResultSet rs) throws SQLException {
        List<Administrador> administrador = new ArrayList<Administrador>();
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String contraseña = rs.getString("password");
            administrador.add(new Administrador(nombre, contraseña));
        }
        return administrador;
    }
}
