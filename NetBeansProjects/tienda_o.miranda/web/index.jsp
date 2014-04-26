<%-- 
    Document   : index
    Created on : 23-abr-2014, 14:39:50
    Author     : oscarmirandabravo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>En ca' Paqui</title>
    </head>
    <body>
        <h1>En ca' Paqui</h1>
        <ul>
            <li><a href='buscarCategoria.jsp'>Buscar productos por categoría</a></li>
            <li><a href='buscarPrecio.jsp'>Buscar productos por precio</a></li>
            <li><a href='buscarNombre.jsp'>Buscar productos por nombre</a></li>
            <li><a href='Administrador.jsp'>Acceder como administrador</a></li>
            <li><a href='carrito.jsp'>Acceder al carrito</a></li>
            
            <table>
            <tr><td><b>Nombre</b></td><td><b>Precio</b></td><td><b>Categoria</b></td><tr>
            <c:forEach var="producto" items="${listaProductos}" varStatus="contador">
                <tr><td>${producto.nombre}
                    </td><td>${producto.precio}
                    </td><td>${producto.categoria}
                    </td><td><img style="max-height: 200px; max-width: 200px" src="${producto.imagen}"/>
                </td><td><form method="get" action='ServletCarrito?'>
                        <input type='submit' name='agregar' value='Agregar' onclick="alert('Ha añadido este producto al carrito.')">
                        <input type='hidden' name='contador' value='${contador.index}'>
                        <input type='hidden' name='busqueda' value='index'>
                    </form>
                </td></tr>
            </c:forEach>
        </table>
        </ul>
    </body>
</html>
