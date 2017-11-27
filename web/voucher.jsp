<%-- 
    Document   : voucher
    Created on : Nov 27, 2017, 9:48:28 AM
    Author     : v-carica
--%>

<%@page import="cl.duoc.modelo.Venta"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% HttpSession sesion = request.getSession();%>
<% ArrayList<Venta> lv = (ArrayList<Venta>)sesion.getAttribute("LISTADOCOMPRA"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Highway - Venta de Peajes</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/master.css" type="text/css" >
    </head>
    <body>
        <form name="frmprincipal" method="post" action="./srvProcesarPedido">
            <div class="container-fluid" style="background-color: rgba(255,255,255, 0.9);">
            <div class="row">
                <div class="col-md-12">
                    <h3 class="text-center">VOUCHER</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <center>
                        <img alt="Highway Inc." src="img/logo.png" class="img-thumbnail" /><br>
                    <a href="index.jsp" >Inicio</a><br>
                    <a href="">Ver Pedidos</a><br>
                    <a href="">Ayuda</a>
                    </center>
                </div>
                <div class="col-md-4">
                    <h2>Pedido Número: <%=sesion.getAttribute("IDVENTA").toString() %></h2>
                    <div class="row">
                                  <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Carretera</th>
                                            <th>Cantidad</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            int suma = 0;
                                            for (Venta temp : lv){
                                                suma = suma + temp.getTotal();
                                            %>
                                        <tr class="active">
                                            <td><%=temp.getCarretera()%></td>
                                            <td><%=temp.getCantidad()%></td>
                                        </tr>
                                        <%
                                            }
                                            %>
                                    </tbody>
                                </table>
                            <center><h1><b>TOTAL A PAGAR: $ <%=suma%></b></h1></center>                  
                    </div>
                    <div class="row">
                        <%
                            String retiro = "";
                            if (sesion.getAttribute("OPRETIRO").equals("1")){
                                retiro = "RETIRO EN OFICINA";
                            }else{
                                retiro = "ENVIO A CLIENTE";
                            }
                            sesion.removeAttribute("NOMBRE");
                            sesion.removeAttribute("RUT");
                            sesion.removeAttribute("DIRECCION");
                            sesion.removeAttribute("COMPRADOR");
                            sesion.removeAttribute("OPPAGO");
                            sesion.removeAttribute("OPRETIRO");
                            sesion.removeAttribute("LISTADOCOMPRA");
                            sesion.removeAttribute("TOTALAPAGAR");
                            sesion.removeAttribute("IDVENTA");
                            sesion.removeAttribute("LISTADOCOMPRA");
                            %>
                            <center><h4><b>OPCION DE ENVIO: <%=retiro%></b></h4></center>
                    </div>
                    <div class="row">
                        <center><h6>Muchas gracias por preferirnos</h6></center>
                    </div>
                </div>
                <div class="col-md-4">
                    <center><h2>Ver Carreteras</h2></center>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4">

                </div>
                <div class="col-md-4"></div>
            </div>
                <div class="row">
                    <div class="col-md-12">
                    </div>
                </div>
        </div>
        <script src="js/bootstrap.js"></script>
        <script src="js/master.js"></script>        
    </body>
</html>
