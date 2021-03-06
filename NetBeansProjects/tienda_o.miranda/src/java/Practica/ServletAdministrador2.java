package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author oscarmirandabravo
 */
@WebServlet(urlPatterns = {"/ServletAdministrador2"})
public class ServletAdministrador2 extends HttpServlet {

    
   @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ProductosDAO dao = new ProductosDAO(ds);
        PedidosDAO pdao = new PedidosDAO(ds);
        RelacionpedidoproductoDAO rdao = new RelacionpedidoproductoDAO(ds);
        
        try (PrintWriter out = response.getWriter()) {

            String busquedaParam = request.getParameter("busqueda");

            if (busquedaParam == null) {
            } else if (busquedaParam.equals("productos")) {                
                formularioProductos(out, request, response, dao);
            } else if (busquedaParam.equals("borrar")) {                
                formularioBorrar(out, request, response, dao);                
            } else if (busquedaParam.equals("pedidos")) {
                formularioPedidos(out, request, response, pdao, rdao);
            }
            
        } catch (FileUploadException ex) {
           Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
       } finally {
            out.close();
            dao.close();
            pdao.close();
            rdao.close();
        }
        
    }
 
    private void formularioProductos(PrintWriter out, HttpServletRequest request, HttpServletResponse response, ProductosDAO dao) throws IOException, ServletException, FileUploadException {
        try {
           response.sendRedirect("AdminProductos.jsp");
       } catch (IOException ex) {
           Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
       }
        request.getSession().setAttribute("listaProductos", dao.getTodosProductos());
    }
    
    private void formularioPedidos(PrintWriter out, HttpServletRequest request, HttpServletResponse response, PedidosDAO dao, RelacionpedidoproductoDAO rdao) {

            if(("Preparado".equals(request.getParameter("preparado")))){ 
                List<Pedido>pedidos = (List) request.getSession().getAttribute("listaPedidos");
                int posicion = parseInt(request.getParameter("contador"));
                int numero = pedidos.get(posicion).getNumero();
                dao.insertPreparado(numero);
                pedidos = null;
            }
            
            request.getSession().setAttribute("listaPedidos", dao.getPedidosPreparado(false));
            request.getSession().setAttribute("listaRelacion", rdao.getTodasRelaciones());
            try {
                response.sendRedirect("AdminPedidos.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void formularioBorrar(PrintWriter out, HttpServletRequest request, HttpServletResponse response, ProductosDAO dao) {

            if(("Borrar".equals(request.getParameter("borrar")))){ 
                List<Producto>productos = (List) request.getSession().getAttribute("listaProductos");
                int posicion = parseInt(request.getParameter("contador"));
                String nombre = productos.get(posicion).getNombre();
                dao.deleteProducto(nombre);
                productos = null;
            }
            
            request.getSession().setAttribute("listaProductos", dao.getTodosProductos());
            try {
                response.sendRedirect("AdminProductos.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
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
