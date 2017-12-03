<%@page import="cl.duoc.modelo.Venta"%>
<%@page import="cl.duoc.controlador.ctrlCarretera"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cl.duoc.modelo.Carretera"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% HttpSession sesion = request.getSession();%>
<% ArrayList<Carretera> c = new ArrayList<Carretera>();%>
<% ctrlCarretera ctrlC = new ctrlCarretera();%>
<% c = ctrlC.ObtenerCarreteras();%>
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
                background-color: rgba(255,255,255,0.85);
                width: calc(100% - 250px);
                padding: 20px 40px;
                min-height: 100%;
            }

            table{
                align: center;
                width: 100%;
                margin: 20px;
                padding: 20px;                
            }
            
            td{
                padding: 5px;
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
            <h3 class="text-center">Datos Empresa</h3>
            <form name="frmprincipal" method="post" action="./srvProcesarPedido" class="form-inline">
                <table>
                    <tr>
                        <td>
                            Rut:
                        </td>
                        <td>
                            <input type="text" name="txtRut" id="txtRut" required="" class="form-control" <%if (sesion.getAttribute("RUT") != null) {%> value="<%=sesion.getAttribute("RUT")%>" readonly<%}%>/> 
                        </td>
                        <td></td>
                        <td>
                            Nombre:
                        </td>
                        <td>
                            <input type="text" name="txtNombre" id="txtNombre" required="" class="form-control" <%if (sesion.getAttribute("NOMBRE") != null) {%> value="<%=sesion.getAttribute("NOMBRE")%>" readonly<%}%>/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Direccion:
                        </td>
                        <td>
                            <input type="text" name="txtDireccion" id="txtDireccion" required="" class="form-control" <%if (sesion.getAttribute("DIRECCION") != null) {%> value="<%=sesion.getAttribute("DIRECCION")%>" readonly<%}%>/>
                        </td>
                        <td></td>
                        <td>
                            Comprado por:
                        </td>
                        <td>
                            <input type="text" name="txtComprador" id="txtComprador" required="" class="form-control" <%if (sesion.getAttribute("COMPRADOR") != null) {%> value="<%=sesion.getAttribute("NOMBRE")%>" readonly<%}%>/>
                        </td>
                    </tr>
                </table>

                <table>
                    <tr>
                        <td>
                            <b> Opciones de pago: </b>
                        </td>
                        <td rowspan="2" valign="top">
                            Seleccione carretera, indique la cantidad y agregue el pedido: <br>
                            <select name="cmbAutopista" id="cmbAutopista" required class="btn btn-secondary dropdown-toggle">
                                <option value="">Seleccione carretera...</option>
                                <%
                                    for (Carretera temp : c) {
                                %>
                                <option value="<%=temp.getId()%>"><%=temp.getNombre()%> ($<%=temp.getValor()%> c/u)</option>
                                <%
                                    }
                                %>
                            </select>
                        </td>

                    </tr>
                    <td>
                        <div class="custom-radio">
                            <input type="radio" name="opPago" value="1" id="Transferencia" required=""<%if (sesion.getAttribute("OPPAGO") != null && sesion.getAttribute("OPPAGO").equals("1")) {%> checked="checked" <%} else if (sesion.getAttribute("OPPAGO") != null && !sesion.getAttribute("OPPAGO").equals("1")) { %> disabled <%}%> >
                            Transferencia                        
                        </div>
                        <div class="custom-radio">
                            <input type="radio" name="opPago" value="2" id="Linea"<%if (sesion.getAttribute("OPPAGO") != null && sesion.getAttribute("OPPAGO").equals("2")) {%> checked="checked" <%} else if (sesion.getAttribute("OPPAGO") != null && !sesion.getAttribute("OPPAGO").equals("2")) { %> disabled <%}%> >
                            Pago en Linea                        
                        </div>
                        <div class="custom-radio">
                            <input type="radio" name="opPago" value="3" id="OC"<%if (sesion.getAttribute("OPPAGO") != null && sesion.getAttribute("OPPAGO").equals("3")) {%> checked="checked" <%} else if (sesion.getAttribute("OPPAGO") != null && !sesion.getAttribute("OPPAGO").equals("3")) { %> disabled <%}%> >
                            Orden de Compra                        
                        </div>
                    </td>
                    <td>

                    </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Opciones de retiro:</b>
                        </td>
                        <td rowspan="2" valign="top">

                            Cantidad:<br>
                            <input type="number" name="txtCantidad" id="txtCantidad" class="form-control" pattern="[0-9]+" required="" />
                            <input type="submit" class="btn btn-success" value="Agregar" name="btnAgregar"/>

                        </td>
                    </tr>
                    <tr>

                        <td>
                            <div class="custom-radio">
                                <input type="radio" name="opRetiro" value="1" id="Oficina" required=""<%if (sesion.getAttribute("OPRETIRO") != null && sesion.getAttribute("OPRETIRO").equals("1")) {%> checked="checked" <%} else if (sesion.getAttribute("OPRETIRO") != null && !sesion.getAttribute("OPRETIRO").equals("1")) { %> disabled <%}%> >
                                Oficina                        
                            </div>
                            <div class="custom-radio">
                                <input type="radio" name="opRetiro" value="2" id="EnvioCliente"<%if (sesion.getAttribute("OPRETIRO") != null && sesion.getAttribute("OPRETIRO").equals("2")) {%> checked="checked" <%} else if (sesion.getAttribute("OPRETIRO") != null && !sesion.getAttribute("OPRETIRO").equals("2")) { %> disabled <%}%> >
                                Envio Cliente                        
                            </div>                           
                        </td>
                        <td></td>
                    </tr>

                </table>


            </form>
            <div class="row">
                <div class="col-md-12">
                    <%
                        if (sesion.getAttribute("LISTADOCOMPRA") != null) {
                            ArrayList<Venta> v = (ArrayList<Venta>) sesion.getAttribute("LISTADOCOMPRA");
                            if (v.size() > 0) {
                    %>
                    <center>
                        <form name="frmListado" id="frmListado" method="POST" action="./srvProcesarPedido">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Carretera</th>
                                        <th>Cantidad</th>
                                        <th>Total</th>
                                        <th>Opciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Venta temp : v) {
                                    %>
                                <fieldset>
                                    <tr class="active">
                                        <td><%=temp.getCarretera()%></td>
                                        <td><input type="text" name="txtCantidad<%=temp.getId()%>" value="<%=temp.getCantidad()%>" required="true" pattern="[0-9]*"></td>
                                        <td>$ <%=temp.getTotal()%></td>
                                        <td><button type="submit" class="btn btn-success" name="btnModificarCantidad" value="<%=temp.getId()%>" >Modificar</button>&nbsp;<button type="submit" class="btn btn-danger" name="btnEliminar" value="<%=temp.getId()%>" >Eliminar</button></td>
                                    </tr>
                                </fieldset>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                            <h2>Total a Pagar: <b>$ <%=sesion.getAttribute("TOTALAPAGAR")%></b></h2>
                            <input type="submit" name="btnHacerPedido" value="Hacer pedido" class="btn btn-success" form="frmListado">&nbsp;
                        </form>
                    </center>
                    <%
                            }
                        }
                        if (sesion.getAttribute("NOMBRE") != null) {
                    %>
                    <center><input type="submit" name="resetTodo" value="Reiniciar Formulario" class="btn btn-warning" form="frmListado"></center>
                        <%
                            }
                        %>
                </div>
            </div>
        </section>     
    </body>
</html>
