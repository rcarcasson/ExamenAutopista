<%-- 
    Document   : ayuda
    Created on : Nov 30, 2017, 2:39:52 PM
    Author     : v-carica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <h3 class="text-center">AYUDA</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <center>
                        <img alt="Highway Inc." src="img/logo.png" class="img-thumbnail" /><br>
                    <a href="index.jsp" >Inicio</a><br>
                    <a href="">Ver Pedidos</a><br>
                    <a href="ayuda.jsp">Ayuda</a>
                    </center>
                </div>
                <div class="col-md-4">
                    <div class="row">
                        <h2>Página principal</h2>
                        <p>1.- Complete el formulario con la información de la empresa: rut, nombre, dirección y persona que retirará el pedido.</p>
                        <p>2.- Seleccione el método de pago y envío.</p>
                        <p>3.- Seleccione del listado la carretera con la que desea comprar el peaje. El listado le mostrará las carreteras junto con su valor unitario.</p>
                        <p>4.- Una vez seleccionada la carretera ingrese la cantidad de pases a comprar.</p>
                        <p>5.- Haga click en AGREGAR para agregar su pedido al listado. Puede repetir los pasos 2 y 4 cuantas veces quiera para agregar más carreteras al pedido.</p>
                        <p><b>NOTA: Tras agregar la primera carretera seleccionada los campos del formulario quedarán deshabilitados para asegurar la integridad de los datos. Si por alguna razón 
                            desea modificar el formulario tendrá que realizar todo el proceso mediante el boton REINICIAR FORMULARIO.</b></p>
                            <p>6.- Puede modificar la cantidad de peajes de cualquiera de las pistas que haya seleccionado o eliminarlas desde las opciones adicionales que muestra la tabla.</p>
                            <p>7.- Una vez verificada las selecciones haga click en HACER PEDIDO para procesar la compra y recibir su voucher.</p>
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
