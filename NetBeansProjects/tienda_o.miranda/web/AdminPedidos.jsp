<%-- 
    Document   : AdminPedidos
    Created on : 23-abr-2014, 16:27:21
    Author     : oscarmirandabravo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="Practica.Pedido"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>En ca' Paqui</title>
    </head>
    <body>
        
        <table>
                <c:forEach var="pedido" items="${listaPedidos}" varStatus="contador">
                <tr><td><b>Numero</b></td><td><b>Cliente</b></td><td><b>Preparado</b></td><tr>
                <tr><td>${pedido.numero}
                    </td><td>${pedido.cliente}
                    <td><form method="get" action='ServletAdministrador2?'>
                        <input type='submit' name='preparado' value='Preparado' onclick="alert('Has completado el pedido Paqui :).')">
                        <input type='hidden' name='contador' value='${contador.index}'>
                        <input type='hidden' name='busqueda' value='pedidos'>
                    </form>
                </td>
                <td>
                    <tr><td><b>Nombre</b></td><td><b>Cantidad</b></td><tr>
                        <c:forEach var="relacion" items="${listaRelacion}">
                        <tr>
                            <c:if test="${pedido.numero.equals(relacion.numero)}">
                                <td>${relacion.nombre}
                                </td><td>${relacion.cantidad}</td>
                                
                            </c:if>
                        </tr>
                        </c:forEach>
                </td>    
                </tr>
            </c:forEach>
        </table>
        
        <a href="Administracion.jsp">Volver</a>
    </body>
</html>
