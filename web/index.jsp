<%-- 
    Document   : index
    Created on : Nov 24, 2017, 9:51:56 AM
    Author     : v-carica
--%>

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
    </head>
    <body>
        <form name="frmprincipal" method="post" action="./srvProcesarPedido">
            <div class="container-fluid" style="background-color: rgba(255,255,255, 0.9);">
            <div class="row">
                <div class="col-md-12">
                    <h3 class="text-center">Datos Empresa</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <center>
                        <img alt="Highway Inc." src="img/logo.png" class="img-thumbnail" /><br>
                    Inicio<br>
                    <a href="">Ver Pedidos</a><br>
                    <a href="">Ayuda</a>
                    </center>
                </div>
                <div class="col-md-4">
                        <div class="form-group">
                            <label for="txtRut">Rut:</label>
                            <input type="text" name="txtRut" id="txtRut" required="true" class="form-control" <%if (sesion.getAttribute("RUT")!=null){%> value="<%=sesion.getAttribute("RUT")%>" readonly<%}%>/>
                        </div>
                        <div class="form-group">
                            <label for="txtNombre">Nombre:</label>
                            <input type="text" name="txtNombre" id="txtNombre" required="true" class="form-control" <%if (sesion.getAttribute("NOMBRE")!=null){%> value="<%=sesion.getAttribute("NOMBRE")%>" readonly<%}%>/>
                        </div>
                        <div class="form-group">
                            <label for="txtRut">Direccion:</label>
                            <input type="text" name="txtDireccion" id="txtDireccion" required="true" class="form-control" <%if (sesion.getAttribute("DIRECCION")!=null){%> value="<%=sesion.getAttribute("DIRECCION")%>"<%}%>/>
                        </div>
                        <div class="form-group">
                            <label for="txtComprador">Comprado por:</label>
                            <input type="text" name="txtComprador" id="txtComprador" required="true" class="form-control" <%if (sesion.getAttribute("COMPRADOR")!=null){%> value="<%=sesion.getAttribute("NOMBRE")%>"<%}%>/>
                        </div>
                </div>
                <div class="col-md-4">
                    <center><h2>Ver Carreteras</h2></center>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <h4>Opciones de pago:</h4>
                    <div class="custom-radio">
                        <input type="radio" name="opPago" value="1" id="Transferencia" required="true"<%if (sesion.getAttribute("OPPAGO")!=null && sesion.getAttribute("OPPAGO").equals("1")){%> checked="checked" <%}%> >
                        <label for="Transferencia">Transferencia</label>                        
                    </div>
                    <div class="custom-radio">
                        <input type="radio" name="opPago" value="2" id="Linea"<%if (sesion.getAttribute("OPPAGO")!=null && sesion.getAttribute("OPPAGO").equals("2")){%> checked="checked" <%}%> >
                        <label for="Linea">Pago en Linea</label>                        
                    </div>
                    <div class="custom-radio">
                        <input type="radio" name="opPago" value="3" id="OC"<%if (sesion.getAttribute("OPPAGO")!=null && sesion.getAttribute("OPPAGO").equals("3")){%> checked="checked" <%}%> >
                        <label for="OC">Orden de Compra</label>                        
                    </div>
                    <h4>Opciones de retiro:</h4>
                    <div class="custom-radio">
                        <input type="radio" name="opRetiro" value="1" id="Oficina" required="true"<%if (sesion.getAttribute("OPRETIRO")!=null && sesion.getAttribute("OPRETIRO").equals("1")){%> checked="checked" <%}%> >
                        <label for="Oficina">Oficina</label>                        
                    </div>
                    <div class="custom-radio">
                        <input type="radio" name="opRetiro" value="2" id="EnvioCliente"<%if (sesion.getAttribute("OPRETIRO")!=null && sesion.getAttribute("OPRETIRO").equals("2")){%> checked="checked" <%}%> >
                        <label for="EnvioCliente">Envio Cliente</label>                        
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="cmbAutopista">Seleccione carretera, indique la cantidad y agregue el pedido:</label>
                    <select name="cmbAutopista" id="cmbAutopista" required class="form-control">
                        <option value="0">Seleccione carretera...</option>
                        <%
                            for (Carretera temp : c){
                                %>
                                <option value="<%=temp.getId()%>"><%=temp.getNombre()%></option>
                                <%
                            }
                        %>
                    </select>
                    <label for="txtCantidad">Cantidad:</label>
                    <input type="text" name="txtCantidad" id="txtCantidad" class="form-control" pattern="[0-9]+" maxlenght="4" required />
                    <input type="submit" class="btn btn-success" value="Agregar" name="btnAgregar"/>
                    </form>
                </div>
                <div class="col-md-4"></div>
            </div>
                <div class="row">
                    <div class="col-md-12">
                        <%
                            if (sesion.getAttribute("LISTADOCOMPRA")!=null){
                                ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute("LISTADOCOMPRA");
                                if (v.size()>0)
                                {
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
                                            for(Venta temp : v){
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
                            if (sesion.getAttribute("NOMBRE")!= null){
                            %>
                            <center><input type="submit" name="resetTodo" value="Reiniciar Formulario" class="btn btn-warning" form="frmListado"></center>
                            <%
}
                            %>
                    </div>
                </div>
        </div>
        <script src="js/bootstrap.js"></script>
        <script src="js/master.js"></script>        
    </body>
</html>
