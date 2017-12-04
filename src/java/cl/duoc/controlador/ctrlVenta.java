/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.controlador;

import cl.duoc.db.Conexion;
import cl.duoc.modelo.Opcion;
import cl.duoc.modelo.Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Alejandra Diaz - Ricardo Carcassón
 */
public class ctrlVenta {

    /**
     * Método que permite obtener un nuevo id de venta. El id es generado en
     * base a la cantidad de registros que existen en la tabla de ventas,
     * considerando solo aquellos IDs que no se repiten.
     *
     * @return un int con el total de registros que coincidan con la consulta.
     */
    public int ObtenerNuevoID() {
        EscribirLog log = new EscribirLog();
        int total = 0;
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT COUNT(DISTINCT idventa) from venta;";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                total = rst.getInt(1);
            }
            return total;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error obtener un nuevo ID - Error: " + err.getMessage());
            return -1;
        }
    }

    /**
     * Método que registra la venta cuando el cliente procesa el pedido. El
     * método recibe dos parámetros: idVenta, que posee el número de id de venta
     * asignado y v que posee la información de las carreteras seleccionadas en
     * el proceso de pedido junto con las cantidads y totales correspondientes.
     * Adicionalmente el método guardará en el log del sistema la acción
     * realizada o el error que ocurrio si este fuese el caso.
     *
     * @param idVenta De tipo int con el número de la venta
     * @param v De tipo ArrayList Venta que posee la información de las
     * carreteras seleccionadas en el proceso de compra con sus respectivos
     * valores
     * @return Un boolean para indicar que el proceso se registro de manera
     * correcta.
     */
    public boolean GuardarVenta(int idVenta, ArrayList<Venta> v) {
        EscribirLog log = new EscribirLog();
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            for (Venta temp : v) {
                String consulta = "INSERT INTO venta VALUES (NULL, "
                        + idVenta + ",'"
                        + temp.getRut() + "','"
                        + temp.getCarretera() + "',"
                        + temp.getCantidad() + ","
                        + temp.getTotal() + ");";
                stmt.executeUpdate(consulta);
            }
            log.RegistroLog("Pedido número " + idVenta + " registrado correctamente");
            return true;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error al registrar pedido número " + idVenta + " - Error: " + err.getMessage());
            return false;
        }
    }

    /**
     * Este método obtiene la lista de usuarios únicos que han realizado compras
     * en el sistema. Permite conocer el rut de los usuarios que han realizado
     * compras anteriores.
     *
     * @return un ArrayList del tipo Venta con la información del rut de los
     * usuarios.
     */
    public ArrayList<Venta> ListarUsuarios() {
        EscribirLog log = new EscribirLog();
        ArrayList<Venta> listaUsuarios = new ArrayList<>();
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT DISTINCT(rut) as rut from venta;";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                Venta ven = new Venta();
                ven.setRut(rst.getString("rut"));
                listaUsuarios.add(ven);
            }

            return listaUsuarios;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error consultar la lista de usuarios - Error: " + err.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Este método obtiene la lista de ventas por usuario, filtrado por rut.
     * Permite obtener información histórica de las ventas por usuario.
     *
     * @param rut De tipo string, recibe el rut del usuario a consultar.
     * @return un ArrayList del tipo Venta con la información del idventa, rut,
     * carretera, cantidad, total del rut consultado.
     */
    public ArrayList<Venta> BuscarDetallePorUsuario(String rut) {
        ArrayList<Venta> listaVentas = new ArrayList<>();
        EscribirLog log = new EscribirLog();
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT idventa, rut, carretera, cantidad, total from venta where rut = '" + rut + "';";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                Venta ven = new Venta();
                ven.setId(rst.getInt("idventa"));
                ven.setRut(rst.getString("rut"));
                ven.setCarretera(rst.getString("carretera"));
                ven.setCantidad(rst.getInt("cantidad"));
                ven.setTotal(rst.getInt("total"));
                listaVentas.add(ven);
            }
            return listaVentas;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error consultar las ventas por usuario - Error: " + err.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Este método obtiene la lista de ventas por usuario, filtrado por rut.
     * Informa el valor total a pagar de cada venta anterior.
     *
     * @param rut De tipo string, recibe el rut del usuario a consultar.
     * @return un ArrayList del tipo Venta con la información del idventa, rut y
     * el valor total a pagar por cada venta.
     */
    public ArrayList<Venta> BuscarPedidoPorUsuario(String rut) {
        EscribirLog log = new EscribirLog();
        ArrayList<Venta> listaVentas = new ArrayList<>();
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT idventa, rut, sum(total) as total from venta where rut = '" + rut + "' group by idventa, rut;";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                Venta ven = new Venta();
                ven.setId(rst.getInt("idventa"));
                ven.setRut(rst.getString("rut"));
                ven.setTotal(rst.getInt("total"));
                listaVentas.add(ven);
            }
            return listaVentas;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error consultar el total de las ventas por usuario - Error: " + err.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Este método obtiene la lista de ventas, filtradas por id. Permite conocer
     * los detalles de la venta anterior del usuario.
     *
     * @param idventa De tipo int, recibe el id de venta a consultar.
     * @return un ArrayList del tipo Venta con la información del idventa, rut,
     * carretera, cantidad y total filtrado por idventa.
     */
    public ArrayList<Venta> BuscarVentas(int idventa) {
        EscribirLog log = new EscribirLog();
        ArrayList<Venta> listaVentas = new ArrayList<>();
        try {
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT idventa, rut, carretera, cantidad, total from venta where idventa = '" + idventa + "';";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                Venta ven = new Venta();
                ven.setId(rst.getInt("idventa"));
                ven.setRut(rst.getString("rut"));
                ven.setCarretera(rst.getString("carretera"));
                ven.setCantidad(rst.getInt("cantidad"));
                ven.setTotal(rst.getInt("total"));
                listaVentas.add(ven);
            }
            return listaVentas;
        } catch (Exception err) {
            log.RegistroLog("Ocurrió un error consultar el detalle de ventas por id - Error: " + err.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Este método informa la opcion de pago y retiro seleccionada por el
     * usuario, filtrado id de venta. Este método permite conocer la opción
     * seleccionada por el usuario en compras anteriores.
     *
     * @param idventa De tipo int, recibe el id de venta a consultar.
     * @return un objeto de tipo opcion, con la informacion del idventa, pago y
     * retiro filtrado por idventa.
     */
    public Opcion BuscarOpcion(int idventa) {
        EscribirLog log = new EscribirLog();
        try {
            Opcion op = new Opcion();
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");

            Statement stmt = conexion.createStatement();

            String consulta = "SELECT idventa, pago, retiro fron opciones where idventa = " + idventa + ";";

            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()) {
                op.setIdventa(rst.getInt("idventa"));
                op.setPago(rst.getInt("pago"));
                op.setRetiro(rst.getInt("retiro"));
            }
            return op;
        } catch (Exception err) {
            log.RegistroLog("Ocurrio un error consultar las opciones de pago y retiro - Error: " + err.getMessage());
            return new Opcion();
        }
    }

}
