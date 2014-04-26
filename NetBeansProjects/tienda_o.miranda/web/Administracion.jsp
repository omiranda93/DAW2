<%-- 
    Document   : Administracion
    Created on : 23-abr-2014, 15:26:13
    Author     : oscarmirandabravo
--%>

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
        <h1>En ca' Paqui</h1>
        <ul>
            <li><a href='ServletAdministrador2?busqueda=productos'>Administrar productos</a></li>
            <li><a href='ServletAdministrador2?busqueda=pedidos'>Administrar pedidos</a></li>
        </ul>
        <a href="index.jsp">Volver</a>
    </body>
</html>
