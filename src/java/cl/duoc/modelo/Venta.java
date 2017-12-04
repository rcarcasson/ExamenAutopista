/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.modelo;

/**
 * Clase que gestionará la información de la venta realizada en el sistema.
 * Posee 5 propiedades: id de tipo int que será el identificador único dentro 
 * la base de datos. Rut de tipo string para el rut de la empresa, carretera de 
 * tipo string que guardara el nombre de la carretera, cantidad de tipo int que
 * indica la cantidad de peajes ingresados a comprar para dicha carretera por 
 * parte del cliente y total de tipo int para registrar el total de la compra en 
 * base al valor * con la cantidad de peajes solicitados por el cliente.
 * @author Ricardo Carcassón
 */
public class Venta {
    private int id;
    private String rut;
    private String carretera;
    private int cantidad;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCarretera() {
        return carretera;
    }

    public void setCarretera(String carretera) {
        this.carretera = carretera;
    }
    
    public int getCantidad(){
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public Venta(){
        this.id = 0;
        this.rut = "";
        this.carretera = "";
        this.cantidad = 0;
        this.total = 0;
    }
    
}
