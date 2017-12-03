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
    background: transparent;
    border: none;
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

                <div class="form-group col-md-3 informacion">
                    <select id="cmb_perfil" name="cmb_rut" class="form-control" required>
                        <option value=""> --- RUT CLIENTE --- </option>
                        <% for (Venta x : venta) {%>
                        <option value="<%=x.getRut()%>">
                            <%=x.getRut()%>
                        </option>
                        <%}%>
                    </select>
                </div>
                <button id="btn_buscar" name="btn_buscar" class="btn btn-primary col-md-2">Buscar</button>
          </form>

            <div class="table-responsive">
                <form name="frm_ventas" action="./srvProcesarPedido" method="POST">
                    <table class="table table-hover">
                        <tr>
                            <th>Pedido</th>
                            <th>Total</th>
                            <th>Pedir</th>
                        </tr>
                        <% if (sesion.getAttribute("lista_ventas") != null) {
                                    for (Venta x : (ArrayList<Venta>) sesion.getAttribute("lista_ventas")) {%>
                        <tr>
                            <td><%=x.getCarretera()%></td>
                            <td><input type="text" name="txt_total" value="<%=x.getTotal()%>" readonly=""></td>
                            <td><button class="btn btn-primary btn-sm" name="btn_repetir" id="btn_repetir" value="<%= x.getId() %>">+</button></td>              
                        </tr>
                        <%}
                                }%>
                    </table>
                </form>
            </div>
        </section>
    </body>
</html>