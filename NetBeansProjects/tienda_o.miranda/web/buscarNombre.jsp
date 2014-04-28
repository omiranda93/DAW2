<%-- 
    Document   : boscarNombre
    Created on : 23-abr-2014, 19:32:00
    Author     : oscarmirandabravo
--%>

<%@page import="Practica.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>En ca' Paqui</title>
    </head>
    <body>

        <b>Búsqueda por Nombre</b>
        <form action='ServletListaProductos'>
            <input type='text' name='query'>
            <input type='submit' name='Buscar' value='Buscar'>
            <input type='hidden' name='busqueda' value='nombre'>
        </form>
        <table>
            <tr><td><b>Nombre</b></td><td><b>Precio</b></td><td><b>Categoria</b></td><tr>
                <c:forEach var="producto" items="${listaProductosNombre}" varStatus="contador">
                <tr>
                    <td>${producto.nombre}</td>
                    <td>${producto.precio}
                    </td><td>${producto.categoria}
                    </td><td><img style="max-height: 200px; max-width: 200px" src="${producto.imagen}"/>
                    </td><td><form method="get" action='ServletCarrito?'>
                            Nº de unidades:
                            <input type="text" name="cantidad">
                            <input type='submit' name='agregar' value='Agregar' onclick="alert('Ha añadido este producto al carrito.')">
                            <input type='hidden' name='contador' value='${contador.index}'>
                            <input type='hidden' name='busqueda' value='nombre'>
                        </form>
                    </td></tr>
                </c:forEach>
        </table>
        <a href="index.jsp">Volver</a>
    <li><a href='carrito.jsp'>Acceder al carrito</a></li>
</body>
</html>
