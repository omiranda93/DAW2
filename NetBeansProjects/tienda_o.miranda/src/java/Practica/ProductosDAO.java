package Practica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class ProductosDAO {

    private Connection conn;

public ProductosDAO(DataSource ds) {
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

    public List<Producto> getProductosNombre(String nombre) {
        return getProductosQuery("nombre LIKE '" + nombre + "'");
    }

    public List<Producto> getProductosPrecio(double precioMin, double precioMax) {
        return getProductosQuery("precio >= " + precioMin + "AND precio <=" + precioMax );
    }
    
    public List<Producto> getProductosCategoria(String categoria) {
        return getProductosQuery("categoria = '" + categoria + "'");
    }

    public List<Producto> getTodosProductos() {
        return getProductosQuery(null);
    }
    
    public boolean insertProducto(String nombre, String categoria, String imagen, double precio){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO Productos VALUES ('"+nombre+"','"+categoria+"','"+imagen+"'," +precio+")";
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
    
    public boolean deleteProducto(String nombre){
        boolean insertado =false;
        Statement stm = null;
        
        try {
            stm = this.conn.createStatement();
            String sql1 = "DELETE FROM relacionpedidoproducto WHERE nombre = '"+nombre+"'";
            stm.executeUpdate(sql1);
            insertado = true;
            
            stm = this.conn.createStatement();
            String sql3 = "DELETE FROM Productos WHERE nombre = '"+nombre+"'";
            stm.executeUpdate(sql3);
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

    private List<Producto> getProductosQuery(String where) {

        List<Producto> productos = new ArrayList<Producto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT nombre, categoria, imagen, precio FROM Productos";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            productos = crearProductoListFromRS(rs);
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
        return productos;
    }

    private List<Producto> crearProductoListFromRS(ResultSet rs) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String categoria = rs.getString("categoria");
            String imagen = rs.getString("imagen");
            int precio = rs.getInt("precio");
            productos.add(new Producto(nombre, categoria, imagen, precio));
        }
        return productos;
    }
}
