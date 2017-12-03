<%@page import="cl.duoc.modelo.Venta"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% HttpSession sesion = request.getSession();%>
<% ArrayList<Venta> lv = (ArrayList<Venta>) sesion.getAttribute("LISTADOCOMPRA");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Highway - Venta de Peajes</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/master.css" type="text/css" >
        <style>
            h2{
                text-align: center;
            }

            section{
                margin-left: 250px;
                margin-top: 0px;
                background-color: rgba(255,255,255,0.8);
                width: calc(100% - 250px);
                padding: 20px 40px;
                min-height: 100%;
            }

            table{
                align: center;
            }

            .centered{
                margin: auto;
                padding: 20px;
                background-color: white;
            }

        </style>
    </head>
    <body>
        <%@include file="_menu.jspf" %>
        <section>
            <h3 class="text-center">VOUCHER</h3>
            <form name="frmprincipal" method="post" action="./srvProcesarPedido">
                <fieldset>
                    <div class="row">
                        <div class="col-md-8 centered">
                            <h4>Pedido Número: <%=sesion.getAttribute("IDVENTA").toString()%></h4>
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
                                            for (Venta temp : lv) {
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
                                    <tr><td></td></tr>
                                    <tr>
                                        <td colspan="2">
                                            <h5><b>TOTAL A PAGAR: $ <%=suma%></b></h5>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <%
                                                String retiro = "";
                                                if (sesion.getAttribute("OPRETIRO").equals("1")) {
                                                    retiro = "RETIRO EN OFICINA";
                                                } else {
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
                                            <h5><b>OPCION DE ENVIO: <%=retiro%></b></h5>
                                        </td>
                                    </tr>
                                </table>                 
                            </div>
                            <div class="row centered">
                                <h6>Muchas gracias por preferirnos</h6>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </form>
        </section>
    </body>
</html>
