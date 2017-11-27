/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.controlador;

import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author v-carica
 */

public class EscribirLog {
    public void RegistroLog(String texto){
        FileWriter archivo = null;
        PrintWriter pw = null;
        try{
            Date fecha = new Date();
            DateFormat fechahora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String m = System.getProperty("user.home");
            archivo = new FileWriter(m + File.separator + "archivolog.txt",true);
            pw = new PrintWriter(archivo);
            pw.println("Fecha/Hora: " + fechahora.format(fecha).toString()+ " - " + texto);
        }
        catch (Exception err){
            err.printStackTrace();
        }
        finally{
            try {
                if (null != archivo){
                    archivo.close();
                }
            }
            catch (Exception err2){
                err2.printStackTrace();
            }
        }
        
    }
}
