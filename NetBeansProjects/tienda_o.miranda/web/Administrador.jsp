<%-- 
    Document   : Administrador
    Created on : 23-abr-2014, 14:43:39
    Author     : oscarmirandabravo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Introduce nombre y contraseña</h1>
        <form  method="post" action='ServletAdministrador'>
            <b>Nombre:</b>
            <input type='text' name='query1' value='Paqui'>
            <b>Contraseña:</b>
            <input type='password' name='query2' value='Paqui'>
            <input type='submit' name='Buscar' value='Entrar'>
            
        </form>
        <div>
        <%String s="";
            if(request.getParameter("error")!=null){
            s = "Error en usuario o contraseña";
        }%>
        <%=s%>
        </div>
    </body>
</html>
