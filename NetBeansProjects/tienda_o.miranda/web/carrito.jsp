<%-- 
    Document   : carrito
    Created on : 24-abr-2014, 16:00:40
    Author     : oscarmirandabravo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Practica.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>En ca' Paqui</title>
    </head>
    <body>
        <table>
            <tr><td><b>Nombre</b></td><td><b>Precio</b></td><td><b>Categoria</b></td><tr>
            <c:forEach var="producto" items="${listaCarrito}">
                <tr><td>${producto.nombre}
                    </td><td>${producto.precio}
                    </td><td>${producto.categoria}
                    </td><td><img style="max-height: 200px; max-width: 200px" src="${producto.imagen}"/>
                </td></tr>
            </c:forEach>
        </table>
        <form method="get" action='ServletCarrito?'>
            <b>Introduce tu nombre:</b>
            <input type='text' name='nombre'>
            <input type='submit' name='comprar' value='Comprar' onclick="alert('Has pedido a En ca\' Paqui')">
            <input type='hidden' name='busqueda' value='compra'>
        </form>
        <a href="index.jsp">Volver</a>
    </body>
</html>
