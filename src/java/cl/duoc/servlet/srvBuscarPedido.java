/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.servlet;

import cl.duoc.controlador.ctrlOpcion;
import cl.duoc.controlador.ctrlVenta;
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
 * @author ygtha
 */
@WebServlet(name = "srvBuscarPedido", urlPatterns = {"/srvBuscarPedido"})
public class srvBuscarPedido extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            String btn_buscar = request.getParameter("btn_buscar");
            String btn_repetir = request.getParameter("btn_repetir");
            HttpSession sesion = request.getSession();
            RequestDispatcher dispatcher;
            ctrlVenta controlVenta = new ctrlVenta();

            if (btn_buscar != null) {
                String rut = request.getParameter("cmb_rut");
                ArrayList<Venta> ventaGuardada = controlVenta.BuscarPedidoPorUsuario(rut);
                if (!ventaGuardada.isEmpty()) {
                    ArrayList<Venta> ventaDetalle = controlVenta.BuscarDetallePorUsuario(rut);
                    for (Venta x : ventaGuardada) {
                        String pedido = "";
                        for (Venta y : ventaDetalle) {
                            if (x.getId() == y.getId()) {
                                pedido = y.getCarretera() + " - " + pedido;
                            }
                        }
                        x.setCarretera(pedido.substring(0, pedido.length() - 2));
                    }
                    sesion.setAttribute("lista_ventas", ventaGuardada);
                    dispatcher = getServletContext().getRequestDispatcher("/buscarPedidos.jsp");
                    dispatcher.forward(request, response);
                }else{
                    sesion.setAttribute("no_rut", "El rut ingresado no registra pedidos.");
                    dispatcher = getServletContext().getRequestDispatcher("/buscarPedidos.jsp");
                    dispatcher.forward(request, response);
                }
            }
            if (btn_repetir != null) {
                int IDVenta = controlVenta.ObtenerNuevoID();
                if (IDVenta != -1) {
                    IDVenta = IDVenta + 1;
                    ArrayList<Venta> v = controlVenta.BuscarVentas(Integer.valueOf(btn_repetir));
                    if (controlVenta.GuardarVenta(IDVenta, v)) {
                        Opcion o = controlVenta.BuscarOpcion(Integer.valueOf(btn_repetir));
                        ctrlOpcion co = new ctrlOpcion();
                        if (co.GuardarOpciones(o)) {
                            sesion.setAttribute("IDVENTA", IDVenta);
                            sesion.setAttribute("LISTADOCOMPRA", v);
                            sesion.setAttribute("OPRETIRO", o);
                            dispatcher = request.getServletContext().getRequestDispatcher("/voucher.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                }
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
        
            () {
        return "Short description";
        }// </editor-fold>

    }
