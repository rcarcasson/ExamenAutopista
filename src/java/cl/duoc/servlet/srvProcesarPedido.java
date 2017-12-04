/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.servlet;

import cl.duoc.controlador.EscribirLog;
import cl.duoc.controlador.ctrlCarretera;
import cl.duoc.controlador.ctrlOpcion;
import cl.duoc.controlador.ctrlVenta;
import cl.duoc.modelo.Carretera;
import cl.duoc.modelo.Opcion;
import cl.duoc.modelo.Venta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ricardo Carcassón
 */
@WebServlet(name = "srvProcesarPedido", urlPatterns = {"/srvProcesarPedido"})
public class srvProcesarPedido extends HttpServlet {

    /**
     * Este servlet se encarga de procesar cada una de las solicitudes que se 
     * hagan desde la página de compras del sitio, ya sea agregar una carretera 
     * al pedido, modificar una cantidad de peajes de una carretera ingresada, 
     * eliminar una carretera en específico, procesar el pedido completo o 
     * reiniciar el formulario para una nueva compra en caso de errores a la 
     * hora de ingresar los datos del cliente en el formulario.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String txtRut = request.getParameter("txtRut");
            String txtNombre = request.getParameter("txtNombre");
            String txtDireccion = request.getParameter("txtDireccion");
            String txtComprador = request.getParameter("txtComprador");
            String opPago = request.getParameter("opPago");
            String opRetiro = request.getParameter("opRetiro");
            String cmbAutopista = request.getParameter("cmbAutopista");
            String txtCantidad = request.getParameter("txtCantidad");
            String btnAgregar = request.getParameter("btnAgregar");
            String btnHacerPedido = request.getParameter("btnHacerPedido");
            String resetTodo = request.getParameter("resetTodo");
            
            String btnModificarCantidad = request.getParameter("btnModificarCantidad");
            String btnEliminar = request.getParameter("btnEliminar");
            
            HttpSession sesion = request.getSession();
            RequestDispatcher dispatcher;
            ArrayList<Venta> listado;
            /**
             * Si el usuario agregar una nueva carretera al listado el sistema
             * guardara la información de los campos del formulario en variables 
             * de sesión para mantener la persistencia de los datos.
             * Tambien se inicializa un objeto log para el registro del log de
             * sucesos.
             */
            if(btnAgregar != null){
                EscribirLog log = new EscribirLog();
                sesion.setAttribute("RUT", txtRut);
                sesion.setAttribute("NOMBRE", txtNombre);
                sesion.setAttribute("DIRECCION", txtDireccion);
                sesion.setAttribute("COMPRADOR", txtComprador);
                sesion.setAttribute("OPPAGO", opPago);
                sesion.setAttribute("OPRETIRO", opRetiro);
                /**La sesion LISTADOCOMPRA guarda todas las carreteras que haya agregado el 
                 * cliente en el formulario de compra. Si la sesión no existe 
                 * se creará un nuevo listado, y en caso contrario, se asignará 
                 * desde la variable de sesion los valores al atributo listado.
                 */
                if (sesion.getAttribute("LISTADOCOMPRA")==null){
                    listado = new ArrayList<Venta>();
                }else{
                    listado = (ArrayList<Venta>)sesion.getAttribute("LISTADOCOMPRA");
                }
                ctrlCarretera ctrlC = new ctrlCarretera();
                Carretera c = ctrlC.ObtenerCarretera(Integer.parseInt(cmbAutopista));
                boolean encontrado = false;
                /**
                 * Como el sistema permite agregar una carretera por pedido, el 
                 * sistema verificará si la carretera agregada no se encuentra ya 
                 * en el listado previo. Si la carretera ya existe en el listado 
                 * modificará la cantidad previa con la nueva cantidad y actualizará 
                 * los valores correspondientes y registrará en el log dicha
                 * operación.
                 */
                for(int i=0;i<listado.size();i++){
                    if (listado.get(i).getCarretera().equals(c.getNombre())){
                        listado.get(i).setCantidad(Integer.parseInt(txtCantidad));
                        listado.get(i).setTotal(c.getValor()*Integer.parseInt(txtCantidad));
                        encontrado = true;
                        log.RegistroLog("Cliente " + sesion.getAttribute("RUT") + " modifica cantidad de un pedido. Carretera: " + c.getNombre() +  " - Nueva cantidad: " + txtCantidad);
                        break;
                    }
                }
                /**
                 * En caso de que no se encuentré la carretera seleccionada dentro 
                 * del listado de la sesión lo agregará como una nueva carretera al listado 
                 * y registrará en el log dicha operación.
                 */
                if (!encontrado){
                    Venta v = new Venta();
                    v.setId(Integer.parseInt(cmbAutopista));
                    v.setRut(txtRut);
                    v.setCarretera(c.getNombre());
                    v.setTotal(c.getValor()*Integer.parseInt(txtCantidad));
                    v.setCantidad(Integer.parseInt(txtCantidad));
                    listado.add(v);
                    log.RegistroLog("Cliente " + sesion.getAttribute("RUT") + " agrega un nuevo pedido al listado. Carretera: " + c.getNombre() + " - Cantidad: " + txtCantidad);
                }
                /**
                 * Actualización del total a pagar en base a los valores y cantidades 
                 * del listado.
                 */
                int sumartotales=0;
                for(Venta temp : listado){
                    sumartotales=sumartotales+temp.getTotal();
                }
                sesion.setAttribute("TOTALAPAGAR", sumartotales);
                sesion.setAttribute("LISTADOCOMPRA", listado);
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else if (btnModificarCantidad != null){
                /**
                 * Si el usuario ha decidido modificar la cantidad de una de las carreteras 
                 * ya ingresadas en su listado el sistema recorrerá en búsqueda del id 
                 * de la carretera a modificar, y al encontrarla actualizará las cantidades 
                 * actualizará los totales y guardará en el log dicha acción.
                 */
                ctrlCarretera ctrlC = new ctrlCarretera();
                int cantidad = Integer.parseInt(request.getParameter("txtCantidad"+btnModificarCantidad));
                ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute(("LISTADOCOMPRA"));
                String carretera = "";
                for (int i=0;i<v.size();i++){
                    if (v.get(i).getId()==Integer.parseInt(btnModificarCantidad)){
                        v.get(i).setCantidad(cantidad);
                        v.get(i).setTotal(ctrlC.ObtenerValor(Integer.parseInt(btnModificarCantidad))*cantidad);
                        carretera = v.get(i).getCarretera();
                        break;
                    }
                }
                int sumartotales=0;
                for(Venta temp : v){
                    sumartotales=sumartotales+temp.getTotal();
                }
                sesion.setAttribute("TOTALAPAGAR", sumartotales);
                sesion.setAttribute("LISTADOCOMPRA", v);
                EscribirLog log = new EscribirLog();
                log.RegistroLog("Cliente " + sesion.getAttribute("RUT") + " modifica cantidad de un pedido. Carretera: " + carretera +  " - Nueva cantidad: " + cantidad);
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else if (btnEliminar != null){
                /**
                 * Si el usuario decide eliminar un pedido del listado el sistema recorrera 
                 * el listado en busca del ID en cuestión. Si lo encuentra eliminará el 
                 * registro del listado y actualizará los totales para finalizar con el 
                 * registro del log de la acción correspondiente.
                 */
                ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute(("LISTADOCOMPRA"));
                String carretera = "";
                int cant = 0;
                for (int i=0;i<v.size();i++){
                    if (v.get(i).getId()==Integer.parseInt(btnEliminar)){
                        carretera = v.get(i).getCarretera();
                        cant = v.get(i).getCantidad();
                        v.remove(i);
                        break;
                    }
                }
                int sumartotales=0;
                for(Venta temp : v){
                    sumartotales=sumartotales+temp.getTotal();
                }
                sesion.setAttribute("TOTALAPAGAR", sumartotales);
                sesion.setAttribute("LISTADOCOMPRA", v);
                EscribirLog log = new EscribirLog();
                log.RegistroLog("Cliente " + sesion.getAttribute("RUT") + " elimina un pedido del listado. Carretera: " + carretera + ", Cantidad: " + cant);
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response); 
            }else if (btnHacerPedido != null){
                /**
                 * Cuando el usuario decide procesar el pedido el sistema iniciará el proceso 
                 * de guardar la información en la base de datos, obteniendo el id de la venta, 
                 * procesando cada carretera del listado, guardando las opciones seleccionadas de 
                 * medio de pago y retiro y redireccionando al voucher para desplegar la información
                 * resultante. La página voucher.jsp es la encargada de eliminar las variables de 
                 * sesión relacionadas a todo el proceso de compra para iniciar un nuevo proceso en 
                 * limpio.
                 */
                ctrlVenta cv = new ctrlVenta();
                int IDVenta = cv.ObtenerNuevoID();
                if (IDVenta != -1){
                    IDVenta = IDVenta + 1;
                    ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute("LISTADOCOMPRA");
                    if (cv.GuardarVenta(IDVenta, v)){
                        Opcion o = new Opcion();
                        o.setIdventa(IDVenta);
                        o.setPago(Integer.parseInt(sesion.getAttribute("OPPAGO").toString()));
                        o.setRetiro(Integer.parseInt(sesion.getAttribute("OPRETIRO").toString()));
                        ctrlOpcion co = new ctrlOpcion();
                        if (co.GuardarOpciones(o)){
                            sesion.setAttribute("IDVENTA", IDVenta);
                            dispatcher = request.getServletContext().getRequestDispatcher("/voucher.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                }
            }else if (resetTodo != null){
                /**
                 * Si el usuario decide iniciar el proceso de compra desde el principio 
                 * se eliminarán las variables de sesión relacionadas y se redireccionará
                 * al formulario en limpio para iniciar un nuevo proceso de compra.
                 */
                sesion.removeAttribute("NOMBRE");
                sesion.removeAttribute("RUT");
                sesion.removeAttribute("DIRECCION");
                sesion.removeAttribute("COMPRADOR");
                sesion.removeAttribute("OPPAGO");
                sesion.removeAttribute("OPRETIRO");
                sesion.removeAttribute("LISTADOCOMPRA");
                sesion.removeAttribute("TOTALAPAGAR");
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
