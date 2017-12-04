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
     * Método que permite obtener un nuevo id de venta. El id es generado en base 
     * a la cantidad de registros que existen en la tabla de ventas, considerando 
     * solo aquellos IDs que no se repiten.
     * @return un int con el total de registros que coincidan con la consulta.
     */

    public int ObtenerNuevoID() {
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
            return -1;
        }
    }
    /**
     * Método que registra la venta cuando el cliente procesa el pedido. El 
     * método recibe dos parámetros: idVenta, que posee el número de id de venta
     * asignado y v que posee la información de las carreteras
     * seleccionadas en el proceso de pedido junto con las cantidads y  totales
     * correspondientes. Adicionalmente el método guardará en el log del sistema 
     * la acción realizada o el error que ocurrio si este fuese el caso.
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
            log.RegistroLog("Ocurrio un error al registrar pedido número " + idVenta + " - Error: " + err.getMessage());
            return false;
        }
    }

    public ArrayList<Venta> ListarUsuarios() {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Venta> BuscarDetallePorUsuario(String rut) {
        ArrayList<Venta> listaVentas = new ArrayList<>();
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Venta> BuscarPedidoPorUsuario(String rut) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Venta> BuscarVentas(int idventa) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Opcion BuscarOpcion(int idventa) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Opcion();
        }
    }

}
