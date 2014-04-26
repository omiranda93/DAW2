package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.sql.DataSource;

/**
 *
 * @author oscarmirandabravo
 */
@WebServlet(urlPatterns = {"/ServletListaProductos"})
public class ServletListaProductos extends HttpServlet {
    
    @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ProductosDAO dao = new ProductosDAO(ds);
        
        PrintWriter out = response.getWriter();
        try {
            
            
            final String busquedaParam = request.getParameter("busqueda");

            if (busquedaParam == null) {
                request.getSession().setAttribute("listaProductos", dao.getTodosProductos());
                response.sendRedirect("index.jsp");
            } else if (busquedaParam.equals("precio")) {                
                formularioPrecio(out, request, response, dao);                
            } else if (busquedaParam.equals("nombre")) {                
                formularioTitulo(out, request, response, dao);                
            }else if (busquedaParam.equals("categoria")) {                
                formularioCategoria(out, request, response, dao);                
            }
            
            
        } finally {
            out.close();
            dao.close();
        }
        
        
    }
        
    
    
    private void formularioPrecio(PrintWriter out, HttpServletRequest request, HttpServletResponse response, ProductosDAO dao) {
        double precioMin = 0;
        double precioMax = 0;
        
        if((request.getParameter("queryMin") != null)&&(request.getParameter("queryMax") != null)){   
            
            try {
                precioMin = Double.parseDouble(request.getParameter("queryMin"));
                precioMax = Double.parseDouble(request.getParameter("queryMax"));  
                
            } catch(NumberFormatException e){
                out.println("<b>Error al acceder al listado de productos</b>");
                e.printStackTrace();
            }
        }
        request.getSession().setAttribute("listaProductosPrecio", dao.getProductosPrecio(precioMin, precioMax));
        try {
            response.sendRedirect("buscarPrecio.jsp");
        } catch (IOException ex) {
            Logger.getLogger(ServletListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void formularioTitulo(PrintWriter out, HttpServletRequest request, HttpServletResponse response, ProductosDAO dao) {
        String titulo = "";
        
        if(request.getParameter("query") != null){            
            try {
                titulo = request.getParameter("query");                
            } catch(NumberFormatException e){
                out.println("<b>Error al acceder al listado de productos</b>");
                e.printStackTrace();
            }
        }
        
        request.getSession().setAttribute("listaProductosNombre", dao.getProductosNombre(titulo));
        try {
            response.sendRedirect("buscarNombre.jsp");
        } catch (IOException ex) {
            Logger.getLogger(ServletListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void formularioCategoria(PrintWriter out, HttpServletRequest request, HttpServletResponse response, ProductosDAO dao) {
       
        String nombre = "";
        if(request.getParameter("query") != null){
            try {
                nombre = request.getParameter("query");                
            } catch(NumberFormatException e){
                out.println("<b>Error al acceder al listado de productos</b>");
                e.printStackTrace();
            }
        }
        
        request.getSession().setAttribute("listaProductosCategoria", dao.getProductosCategoria(nombre));
        try {
            response.sendRedirect("buscarCategoria.jsp");
        } catch (IOException ex) {
            Logger.getLogger(ServletListaProductos.class.getName()).log(Level.SEVERE, null, ex);
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
