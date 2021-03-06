<%-- 
    Document   : buscarPrecio
    Created on : 23-abr-2014, 19:32:19
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
        <b>Búsqueda por Precio</b>
        <form action='ServletListaProductos?busqueda=precio'>
            <b>Desde:</b>
            <input type='text' name='queryMin'>
            <b>Hasta:</b>
            <input type='text' name='queryMax'>
            <input type='submit' name='Buscar' value='Buscar'>
            <input type='hidden' name='busqueda' value='precio'>
        </form>
        <% List<Producto> productos = (List)request.getSession().getAttribute("listaProductosPrecio"); %>
        <table>
            <tr><td><b>Nombre</b></td><td><b>Precio</b></td><td><b>Categoria</b></td><tr>
            <c:forEach var="producto" items="${listaProductosPrecio}" varStatus="contador">
                <tr><td>${producto.nombre}
                    </td><td>${producto.precio}
                    </td><td>${producto.categoria}
                    </td><td><img style="max-height: 200px; max-width: 200px" src="${producto.imagen}"/>
                </td><td><form method="get" action='ServletCarrito?'>
                        Nº de unidades:
                        <input type="text" name="cantidad">
                        <input type='submit' name='agregar' value='Agregar' onclick="alert('Ha añadido este producto al carrito.')">
                        <input type='hidden' name='contador' value='${contador.index}'>
                        <input type='hidden' name='busqueda' value='precio'>
                    </form>
                </td></tr>

            </c:forEach>
        </table>
        <a href="index.jsp">Volver</a>
        <li><a href='carrito.jsp'>Acceder al carrito</a></li>
    </body>
</html>
