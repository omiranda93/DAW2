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
public class PedidosDAO {
    
    private Connection conn;

    public PedidosDAO(DataSource ds) {
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

    public List<Pedido> getPedidosNumero(int numero) {
        return getPedidosQuery("Numero = " + numero);
    }

    public List<Pedido> getPedidosCliente(String cliente) {
        return getPedidosQuery("Cliente = '" + cliente + "'" );
    }
    
    public List<Pedido> getPedidosPreparado(boolean preparado) {
        return getPedidosQuery("preparado = '" + preparado + "'");
    }

    public List<Pedido> getTodosPedidos() {
        return getPedidosQuery(null);
    }
    
    public boolean insertPreparado(int numero){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql = "UPDATE Pedido SET preparado ='true' WHERE numero ="+numero;
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
    
    public boolean insertPedido(int numero, String nombre, boolean preparado){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO Pedido VALUES ("+numero+",'"+nombre+"','"+preparado+"')";
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

    private List<Pedido> getPedidosQuery(String where) {

        List<Pedido> pedidos = new ArrayList<Pedido>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT numero, cliente, preparado FROM Pedido";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            pedidos = crearPedidoListFromRS(rs);
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
        return pedidos;
    }

    private List<Pedido> crearPedidoListFromRS(ResultSet rs) throws SQLException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        while (rs.next()) {
            int numero = rs.getInt("numero");
            String cliente = rs.getString("cliente");
            boolean preparado = rs.getBoolean("preparado");
            pedidos.add(new Pedido(numero, cliente, preparado));
        }
        return pedidos;
    }
}

    
