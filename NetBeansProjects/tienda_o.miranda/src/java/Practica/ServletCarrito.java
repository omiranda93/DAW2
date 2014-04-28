/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Practica;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author oscarmirandabravo
 */
@WebServlet(name = "ServletCarrito", urlPatterns = {"/ServletCarrito"})
public class ServletCarrito extends HttpServlet {

       @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        List <Producto> nueva= new ArrayList <Producto>();
        if(request.getSession().getAttribute("listaCarrito")==null){
            request.getSession().setAttribute("listaCarrito", nueva);
        }
        
        ProductosDAO dao = new ProductosDAO(ds);
        PedidosDAO pdao = new PedidosDAO(ds);
        RelacionpedidoproductoDAO rdao = new RelacionpedidoproductoDAO(ds);
        List<Producto>carrito = (List) request.getSession().getAttribute("listaCarrito");
        try (PrintWriter out = response.getWriter()) {
            
            final String busquedaParam = request.getParameter("busqueda");

            if (busquedaParam == null) {
            } else if (busquedaParam.equals("precio")) { 
                if(("Agregar".equals(request.getParameter("agregar")))){ 
                    List<Producto>productos = (List) request.getSession().getAttribute("listaProductosPrecio");
                    int posicion = parseInt(request.getParameter("contador"));
                    productos.get(posicion).setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                    carrito.add(productos.get(posicion));
                    request.getSession().setAttribute("listaCarrito", carrito);
                    productos=null;
                }
                response.sendRedirect("buscarPrecio.jsp");
            } else if (busquedaParam.equals("nombre")) {  
                if(("Agregar".equals(request.getParameter("agregar")))){ 
                    List<Producto>productos = (List) request.getSession().getAttribute("listaProductosNombre");
                    int posicion = parseInt(request.getParameter("contador"));
                    productos.get(posicion).setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                    carrito.add(productos.get(posicion));
                    request.getSession().setAttribute("listaCarrito", carrito);
                    productos=null;
                }
                response.sendRedirect("buscarNombre.jsp");
            }else if (busquedaParam.equals("categoria")) {   
                if(("Agregar".equals(request.getParameter("agregar")))){ 
                    List<Producto>productos = (List) request.getSession().getAttribute("listaProductosCategoria");
                    int posicion = parseInt(request.getParameter("contador"));
                    productos.get(posicion).setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                    carrito.add(productos.get(posicion));
                    request.getSession().setAttribute("listaCarrito", carrito);
                    productos=null;
                }
                response.sendRedirect("buscarCategoria.jsp");
                
            }else if (busquedaParam.equals("index")) {  
                if(("Agregar".equals(request.getParameter("agregar")))){ 
                    List<Producto>productos = (List) request.getSession().getAttribute("listaProductos");
                    int posicion = parseInt(request.getParameter("contador"));
                    productos.get(posicion).setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                    carrito.add(productos.get(posicion));
                    request.getSession().setAttribute("listaCarrito", carrito);
                    productos=null;
                }
                response.sendRedirect("index.jsp");
            }else if (busquedaParam.equals("compra")) {   
                if(("Comprar".equals(request.getParameter("comprar")))){ 
                    int numeroPedido=2;
                    for(Pedido p : pdao.getTodosPedidos()){
                        numeroPedido++;
                    }
                    pdao.insertPedido(numeroPedido, request.getParameter("nombre"), false);
                    for(Producto prod:carrito){
                        rdao.insertRelacion(prod.getNombre(), numeroPedido, prod.getCantidad());
                    }
                }
                response.sendRedirect("index.jsp");
            }
            
            
        }finally{
            rdao.close();
            dao.close();
            pdao.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
