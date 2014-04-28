<%-- 
    Document   : AdminProductos
    Created on : 23-abr-2014, 16:27:34
    Author     : oscarmirandabravo
--%>

<%@page import="Practica.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>En ca' Paqui</title>
    </head>
    <body>
        <table>
            <tr><td><b>Productos</b></td></tr>
                <c:forEach var="producto" items="${listaProductos}" varStatus="contador">
                <tr><td><form method= "post" action='ServletAnadirProducto' enctype="multipart/form-data">
                            <input type='hidden' name='accion' value='editar'>
                            <input type='hidden' name='contador' value='${contador.index}'>
                            <input type='text' name='nombre' value="${producto.nombre}">
                            <input type='text' name='precio' value="${producto.precio}">
                            <select name='categoria'>
                                <option value="${producto.categoria}">${producto.categoria}</option>
                                <option value='Alimentacion'>Alimentación</option>
                                <option value='Ferreteria'>Ferretería</option>
                                <option value='Drogueria'>Droguería</option>
                                <option value='Prensa'>Prensa</option>
                            </select>
                            <input type='file' name='imagen' size='60'>
                            <input type='submit' name='editar' value='Editar' onclick="alert('Has editado este producto')">
                        </form>
                    </td><td><form method="get" action='ServletAdministrador2' >
                            <input type='hidden' name='busqueda' value='borrar'>
                            <input type='submit' name='borrar' value='Borrar' onclick="alert('Has borrado este producto')">
                            <input type='hidden' name='contador' value='${contador.index}'>
                        </form>
                    </td>
                </tr>

            </c:forEach>
        </table>
        <form method= "post" action='ServletAnadirProducto' enctype="multipart/form-data">
            <b>Nombre:</b>
            <input type='text' name='nombre'>
            <b>Categoria:</b>
            <select name='categoria'>
                <option value='Alimentacion'>Alimentación</option>
                <option value='Ferreteria'>Ferretería</option>
                <option value='Drogueria'>Droguería</option>
                <option value='Prensa'>Prensa</option>
            </select>
            <b>Imagen:</b>
            <input type='file' name='imagen' size='60'>
            <b>Precio:</b>
            <input type='text' name='precio'>
            <input type='hidden' name='accion' value='anadir'>
            <input type='submit' name='Añadir' value='Agregar' onclick="alert('Has añadido este producto a los disponibles.')">
        </form>
        <a href="Administracion.jsp">Volver</a>
    </body>
</html>
