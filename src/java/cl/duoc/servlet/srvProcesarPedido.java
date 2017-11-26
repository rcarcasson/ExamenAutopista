/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.servlet;

import cl.duoc.controlador.ctrlCarretera;
import cl.duoc.modelo.Carretera;
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
 * @author v-carica
 */
@WebServlet(name = "srvProcesarPedido", urlPatterns = {"/srvProcesarPedido"})
public class srvProcesarPedido extends HttpServlet {

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
            
            String btnModificarCantidad = request.getParameter("btnModificarCantidad");
            String btnEliminar = request.getParameter("btnEliminar");
            
            HttpSession sesion = request.getSession();
            RequestDispatcher dispatcher;
            ArrayList<Venta> listado;
            
            if(btnAgregar != null){
                sesion.setAttribute("RUT", txtRut);
                sesion.setAttribute("NOMBRE", txtNombre);
                sesion.setAttribute("DIRECCION", txtDireccion);
                sesion.setAttribute("COMPRADOR", txtComprador);
                sesion.setAttribute("OPPAGO", opPago);
                sesion.setAttribute("OPRETIRO", opRetiro);
                if (sesion.getAttribute("LISTADOCOMPRA")==null){
                    listado = new ArrayList<Venta>();
                }else{
                    listado = (ArrayList<Venta>)sesion.getAttribute("LISTADOCOMPRA");
                }
                ctrlCarretera ctrlC = new ctrlCarretera();
                Carretera c = ctrlC.ObtenerCarretera(Integer.parseInt(cmbAutopista));
                boolean encontrado = false;
                for(int i=0;i<listado.size();i++){
                    if (listado.get(i).getCarretera().equals(c.getNombre())){
                        listado.get(i).setCantidad(Integer.parseInt(txtCantidad));
                        listado.get(i).setTotal(c.getValor()*Integer.parseInt(txtCantidad));
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado){
                    Venta v = new Venta();
                    v.setId(Integer.parseInt(cmbAutopista));
                    v.setCarretera(c.getNombre());
                    v.setTotal(c.getValor()*Integer.parseInt(txtCantidad));
                    v.setCantidad(Integer.parseInt(txtCantidad));
                    listado.add(v);
                }
                int sumartotales=0;
                for(Venta temp : listado){
                    sumartotales=sumartotales+temp.getTotal();
                }
                sesion.setAttribute("TOTALAPAGAR", sumartotales);
                sesion.setAttribute("LISTADOCOMPRA", listado);
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else if (btnModificarCantidad != null){
                ctrlCarretera ctrlC = new ctrlCarretera();
                int cantidad = Integer.parseInt(request.getParameter("txtCantidad"+btnModificarCantidad));
                ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute(("LISTADOCOMPRA"));
                for (int i=0;i<v.size();i++){
                    if (v.get(i).getId()==Integer.parseInt(btnModificarCantidad)){
                        v.get(i).setCantidad(cantidad);
                        v.get(i).setTotal(ctrlC.ObtenerValor(Integer.parseInt(btnModificarCantidad))*cantidad);
                        break;
                    }
                }
                int sumartotales=0;
                for(Venta temp : v){
                    sumartotales=sumartotales+temp.getTotal();
                }
                sesion.setAttribute("TOTALAPAGAR", sumartotales);
                sesion.setAttribute("LISTADOCOMPRA", v);
                dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }else if (btnEliminar != null){
                ArrayList<Venta> v = (ArrayList<Venta>)sesion.getAttribute(("LISTADOCOMPRA"));
                for (int i=0;i<v.size();i++){
                    if (v.get(i).getId()==Integer.parseInt(btnEliminar)){
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
