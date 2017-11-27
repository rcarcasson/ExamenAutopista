/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.controlador;

import cl.duoc.db.Conexion;
import cl.duoc.modelo.Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author v-carica
 */
public class ctrlVenta {
    public int ObtenerNuevoID(){
        int total=0;
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            
            String consulta = "SELECT COUNT(DISTINCT idventa) from venta;";
            
            ResultSet rst = stmt.executeQuery(consulta);
            while (rst.next()){
                total = rst.getInt(1);
            }
            return total;
        }
        catch (Exception err){
            return -1;
        }
    }
    
    public boolean GuardarVenta(int idVenta, ArrayList<Venta> v){
        EscribirLog log = new EscribirLog();
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            
            for(Venta temp : v){
                String consulta = "INSERT INTO venta VALUES (NULL, " +
                        idVenta + ",'" +
                        temp.getRut() + "','" +
                        temp.getCarretera() + "'," +
                        temp.getCantidad() +  "," + 
                        temp.getTotal() + ");";
                stmt.executeUpdate(consulta);
            }
            log.RegistroLog("Pedido número " + idVenta + " registrado correctamente");
            return true;
        }
        catch (Exception err){
            log.RegistroLog("Ocurrio un error al registrar pedido número " + idVenta + " - Error: " + err.getMessage());
            return false;
        }
    }
}
