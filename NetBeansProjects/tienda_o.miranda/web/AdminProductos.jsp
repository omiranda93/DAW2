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
            <tr><td><b>Nombre</b></td><td><b>Precio</b></td><td><b>Categoria</b></td><tr>
                <c:forEach var="producto" items="${listaProductos}" varStatus="contador">
                <tr><td>${producto.nombre}
                    </td><td>${producto.precio}
                    </td><td>${producto.categoria}
                    </td><td><img style="max-height: 200px; max-width: 200px" src="${producto.imagen}"/>
                    </td><td><form method="get" action='ServletAdministrador2' >
                                <input type='submit' name='borrar' value='Borrar' onclick="alert('Has borrado este producto')">
                                <input type='hidden' name='contador' value='${contador.index}'>
                                <input type='hidden' name='busqueda' value='borrar'>
                            </form>
                </td></tr>

            </c:forEach>
        </table>
        <b>Añade un nuevo producto:</b>
        <form method= "get" action='ServletAdministrador2' enctype="multipart/form-data">
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
            <input type='submit' name='Añadir' value='Agregar' onclick="alert('Has añadido este producto a los disponibles.')">
            <input type='hidden' name='busqueda' value='productos'>
        </form>
        <a href="Administracion.jsp">Volver</a>
    </body>
</html>
