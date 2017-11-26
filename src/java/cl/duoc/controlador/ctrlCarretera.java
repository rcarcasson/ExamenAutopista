/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.controlador;

import cl.duoc.db.Conexion;
import cl.duoc.modelo.Carretera;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author v-carica
 */
public class ctrlCarretera {
    
    public ArrayList<Carretera> ObtenerCarreteras(){
        ArrayList<Carretera> lista = new ArrayList<Carretera>();
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            
            String consulta = "SELECT id, nombre, valor from carretera;";
            
            ResultSet rst = stmt.executeQuery(consulta);
            
            while(rst.next()){
                Carretera c = new Carretera();
                c.setId(rst.getInt("id"));
                c.setNombre(rst.getString("nombre"));
                c.setValor(rst.getInt("valor"));
                lista.add(c);
            }
            return lista;
            
        }
        catch(Exception err){
            err.printStackTrace();
            return new ArrayList<Carretera>(); 
        }
    }
    
    public Carretera ObtenerCarretera(int id){
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            
            String consulta = "SELECT nombre, valor from carretera where id="+id+";";
            ResultSet rst = stmt.executeQuery(consulta);
            Carretera c = new Carretera();
            while (rst.next()){
                c.setNombre(rst.getString("nombre"));
                c.setValor(rst.getInt("valor"));
            }
            return c;
        }
        catch (Exception err){
            return new Carretera();
        }
    }
    
    public int ObtenerValor(int id){
        int valor=0;
        try{
            Conexion conn = new Conexion();
            Connection conexion = conn.getConnection("bdcarreteras");
            
            Statement stmt = conexion.createStatement();
            String consulta = "SELECT valor from carretera where id="+id+";";
            
            ResultSet rst = stmt.executeQuery(consulta);
            while(rst.next()){
                valor = rst.getInt("valor");
            }
            stmt.close();
            conexion.close();
            return valor;
        }
        catch (Exception err){
            return 0;
        }
    }
        
}
