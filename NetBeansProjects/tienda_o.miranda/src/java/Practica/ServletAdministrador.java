package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/ServletAdministrador"})
public class ServletAdministrador extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        AdministradorDAO admindao = new AdministradorDAO(ds);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            if((request.getParameter("query1") != null)&&(request.getParameter("query2") != null)){            
                try {
                    String nombre = request.getParameter("query1");
                    String contraseña = request.getParameter("query2");
                    if (admindao.getAdministradorNombreContraseña(nombre, contraseña).isEmpty()){
                        response.sendRedirect("Administrador.jsp?error=si");
                    }else{
                        response.sendRedirect("Administracion.jsp");
                        
                    }
                } catch(NumberFormatException e){
                    out.println("<b>Error al acceder al listado de administradores</b>");
                    e.printStackTrace();
                }
            }
            
            
            
        }finally{
            admindao.close();
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
