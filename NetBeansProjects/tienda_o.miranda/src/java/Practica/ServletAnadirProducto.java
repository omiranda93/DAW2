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
@WebServlet(urlPatterns = {"/ServletAnadirProducto"})
@MultipartConfig
public class ServletAnadirProducto extends HttpServlet {

   @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ProductosDAO dao = new ProductosDAO(ds);
        PedidosDAO pdao = new PedidosDAO(ds);
        RelacionpedidoproductoDAO rdao = new RelacionpedidoproductoDAO(ds);
        
            try {
                String barra = System.getProperty("file.separator");
                String ruta = request.getServletContext().getRealPath("").concat(barra).concat("img").concat(barra);
                
                
                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1000000);
                //si es 1024, crea archivo temporal y también da error con dicho archivo
                //si se deja con un número alto, no guarda en temporal y da error con el nombre
                //del archivo subido
                factory.setRepository(new File(ruta));
                
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(request);
                String busquedaParam = "";

                // Configure a repository (to ensure a secure temp location is used)
                ServletContext servletContext = this.getServletConfig().getServletContext();
                File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);

                String imagen = "";
                
                
                String nombre = "";
                String categoria = "";
                int precio = 0;
                
                // Process the uploaded items
                for(FileItem item : items){
                    if (!(item.isFormField())) {
                        File file = new File( ruta, item.getName());
                        item.write(file);
                        imagen = "img"+barra+item.getName();
                    } else {
                        String campo = item.getFieldName();
                        if (campo.equals("nombre")) {
                            nombre = item.getString();
                        } else if (campo.equals("categoria")) {
                            categoria = item.getString();
                        } else if (campo.equals("precio")) {
                            precio = Integer.parseInt(item.getString());
                        }
                    }
                }
                
                
                dao.insertProducto(nombre, categoria, imagen, precio);   
                
            } catch(NumberFormatException e){
                out.println("<b>Error al acceder al listado de productos</b>");
                e.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
           response.sendRedirect("AdminProductos.jsp");
       } catch (IOException ex) {
           Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
       }
        request.getSession().setAttribute("listaProductos", dao.getTodosProductos());
        
        out.close();
        dao.close();
        pdao.close();
        rdao.close();
        
    }
    
    private String extractFileName (Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String [] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
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
