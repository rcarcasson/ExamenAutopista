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
 * @author Ricardo Carcassón
 * Este controlador se encarga de escribir en un archivo log cada unas de las
 * acciones que ocurren dentro del sistema, así como también los errores que
 * ocurren en ellas.
 */

public class EscribirLog {
    /**
     * Método que escribe en un archivo de texto un log con las acciones 
     * realizadas por los usuarios y los errores que ocurren dentro del sistema.
     * El log se guarda dentro del HOME del usuario que corre la aplicación.
     * El método registra la fecha y la hora del evento e imprime el texto en el
     * archivo archivolog.txt
     * @param texto El parametro texto es el que trae la descripción de la acción o
     * error que ocurrio en el sistema.
     */
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
