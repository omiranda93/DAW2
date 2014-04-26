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
@MultipartConfig
public class ServletAdministrador2 extends HttpServlet {

   private static final String SAVE_DIR = "img";
    
   @Resource(lookup = "jdbc/tienda_omiranda")
    private DataSource ds;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ProductosDAO dao = new ProductosDAO(ds);
        PedidosDAO pdao = new PedidosDAO(ds);
        RelacionpedidoproductoDAO rdao = new RelacionpedidoproductoDAO(ds);
        
        try (PrintWriter out = response.getWriter()) {


            
            final String busquedaParam = request.getParameter("busqueda");

            if (busquedaParam == null) {
            } else if (busquedaParam.equals("productos")) {                
                formularioProductos(out, request, response, dao);                
            } else if (busquedaParam.equals("pedidos")) {                
                formularioPedidos(out, request, response, pdao, rdao);                
            } else if (busquedaParam.equals("borrar")) {                
                formularioBorrar(out, request, response, dao);                
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

        if((request.getParameter("nombre") != null)&&(request.getParameter("categoria") != null)&&(request.getParameter("imagen") != null)&&(request.getParameter("precio") != null)){            
            try {
                String nombre = request.getParameter("nombre");
                String categoria = request.getParameter("categoria");
                double precio = Double.parseDouble(request.getParameter("precio"));

                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();

                // Configure a repository (to ensure a secure temp location is used)
                ServletContext servletContext = this.getServletConfig().getServletContext();
                File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(request);
                
                // Process the uploaded items
                Iterator<FileItem> iter = items.iterator();
                ArrayList <String> campos = new ArrayList();
                String barra = File.separator;
                String imagen = "";
                while (iter.hasNext()) {
                    FileItem item = iter.next();

                    if (item.isFormField()) {
                        String valor = item.getString();
                        campos.add(valor);
                    } else {
                        File file = new File(item.getName());
                        String ruta = request.getServletContext().getRealPath(barra+"img");
                        item.write(new File(ruta + barra, file.getName()));
                        imagen = SAVE_DIR + barra + file.getName();
                    }
                }
                
                dao.insertProducto(nombre, categoria, imagen, precio);   
                
            } catch(NumberFormatException e){
                out.println("<b>Error al acceder al listado de productos</b>");
                e.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(ServletAdministrador2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
