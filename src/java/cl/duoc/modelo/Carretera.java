/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.modelo;

/**
 * Esta clase gestionará la información de la carretera. Usará tres propiedades: 
 * id de tipo int para el identificador unico de la carretera, nombre de tipo
 * string para el nombre de la carretera y valor de tipo int para el precio del
 * peaje asociado de dicha carretera.
 * @author Ricardo Carcassón
 */
public class Carretera {
    private int id;
    private String nombre;
    private int valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    public Carretera(){
        this.id = 0;
        this.nombre = "";
        this.valor = 0;
    }
            
    
}
