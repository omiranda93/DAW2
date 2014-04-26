/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Practica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author oscarmirandabravo
 */
public class RelacionpedidoproductoDAO {
    private Connection conn;

    public RelacionpedidoproductoDAO(DataSource ds) {
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
    
    public List<Relacionpedidoproducto> getRelacionNumero(int numero) {
        return getRelacionQuery("Numero = " + numero);
    }

    public List<Relacionpedidoproducto> getRelacionProducto(String producto) {
        return getRelacionQuery("Nombre = '" + producto + "'" );
    }
    
    public List<Relacionpedidoproducto> getRelacionCantidad(int cantidad) {
        return getRelacionQuery("Cantidad = '" + cantidad + "'");
    }

    public List<Relacionpedidoproducto> getTodasRelaciones() {
        return getRelacionQuery(null);
    }
    
    public boolean insertRelacion(String nombre, int numero, int cantidad){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO relacionpedidoproducto VALUES ('"+nombre+"',"+numero+","+cantidad+")";
            stm.executeUpdate(sql);
            insertado = true;

        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la insercion",e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la insercion: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return insertado;
    }
    
    public boolean sumaCantidad(String nombre){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql = "UPDATE relacionpedidoproducto SET cantidad =cantidad+1 WHERE nombre ='"+nombre+"'";
            stm.executeUpdate(sql);
            insertado = true;

        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la insercion",e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la insercion: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return insertado;
    }

    private List<Relacionpedidoproducto> getRelacionQuery(String where) {

        List<Relacionpedidoproducto> relaciones = new ArrayList<Relacionpedidoproducto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT nombre, numero, cantidad FROM relacionpedidoproducto";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            relaciones = crearRelacionListFromRS(rs);
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
        return relaciones;
    }

    private List<Relacionpedidoproducto> crearRelacionListFromRS(ResultSet rs) throws SQLException {
        List<Relacionpedidoproducto> relacion = new ArrayList<Relacionpedidoproducto>();
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            int numero = rs.getInt("numero");
            int cantidad = rs.getInt("cantidad");
            relacion.add(new Relacionpedidoproducto(nombre, numero, cantidad));
        }
        return relacion;
    }
    
}
