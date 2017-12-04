/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.controlador;

import cl.duoc.db.Conexion;
import cl.duoc.modelo.Opcion;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Ricardo Carcassón
 */
public class ctrlOpcion {
    /**
     * Este método permite guardar las opciones de forma de pago y de retiro de
     * los peajes comprados por el cliente. El método guardar el id de la venta
     * junto con las opciones previamente indicadas.
     * @param o De tipo clase Opcion con los valores seleccionados por el cliente
     * @return boolean para indicar si se guardo de manera correcta la información
     */
    public boolean GuardarOpciones(Opcion o){
        EscribirLog log = new EscribirLog();
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            String consulta = "INSERT INTO opciones VALUES (NULL," +
                    o.getIdventa() + "," +
                    o.getPago() + "," +
                    o.getRetiro() + ");";
            
            stmt.executeUpdate(consulta);
            log.RegistroLog("Registro de opciones de pago y retiro registrados satisfactoriamente para venta número: " + o.getIdventa());
            return true;
        }
        catch (Exception err){
            log.RegistroLog("Ocurrio un error al registrar opciones de venta de orden número: " + o.getIdventa() + " - Menesaje: " + err.getMessage());
            return false;
        }
    }
    
}
