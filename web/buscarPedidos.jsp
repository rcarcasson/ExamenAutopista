<%@page import="cl.duoc.controlador.ctrlVenta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cl.duoc.modelo.Venta"%>
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
                background-color: rgba(255,255,255,0.75);
                width: calc(100% - 250px);
                padding: 20px;
                min-height: 100%;

            }

            .informacion{
                padding: 15px;
            }

            input[type="text"]
            {
                margin: 20px 0;
            }
            
            
        </style>
    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();
            ArrayList<Venta> venta = new ctrlVenta().ListarUsuarios();
        %>
        <%@include file="_menu.jspf" %>
        <section>
            <h2>Buscar Pedidos</h2>

            <form class="form-inline" name="frm_listar" action="./srvBuscarPedido" method="POST">

                <div class="form-group">
                    <label class="col-md-2 control-label" for="cmb_rut"><b>RUT</b> </label>  
                    <div class="col-md-6">
                        <input id="cmb_rut" name="cmb_rut" type="text" maxlength="10" pattern="[0-9]{1,8}-[0-9Kk]{1}" placeholder="12345678-9" class="form-control input-md" required="">

                    </div>

                </div>
                    <div class="col-md-4">
                        <button id="btn_buscar" type="submit" name="btn_buscar" class="btn btn-primary">Buscar</button>
                    </div>
            </form>

            <div class="table-responsive">
                <% if (sesion.getAttribute("no_rut") != null){ %>
                <%= sesion.getAttribute("no_rut").toString() %>
                <% sesion.removeAttribute("no_rut");} %>
                <% if (sesion.getAttribute("lista_ventas") != null) { %>
                <form name="frm_ventas" action="./srvBuscarPedido" method="POST">
                    <table class="table table-hover">
                        <tr>
                            <th>Pedido</th>
                            <th>Total</th>
                            <th>Pedir</th>
                        </tr>
                        <% for (Venta x : (ArrayList<Venta>) sesion.getAttribute("lista_ventas")) {%>
                        <tr>
                            <td><%=x.getCarretera()%></td>
                            <td><input type="text" name="txt_total" value="<%=x.getTotal()%>" readonly=""></td>
                            <td><button class="btn btn-primary btn-sm" name="btn_repetir" id="btn_repetir" value="<%= x.getId()%>">+</button></td>              
                        </tr>
                        <%}
                                sesion.removeAttribute("lista_ventas");
                            }%>
                    </table>
                </form>
            </div>
        </section>
    </body>
</html>