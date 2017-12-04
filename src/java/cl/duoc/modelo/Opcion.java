/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.modelo;

/**
 * Esta clase se encargará de la gestión de la información de las opciones 
 * seleccionadas por el usuario con cuatro propiedades. ID de tipo int para 
 * el identificador unico de la opción dentro de la base de datos, idventa de 
 * tipo int que indica el número de venta al que esta asociada la opción, 
 * pago de tipo int para indicar el tipo de pago que selecciono el usuario y
 * retiro de tipo int para indicar la opción de retiro seleccionada por el 
 * usuario en el proceso de compra.
 * @author Ricardo Carcassón
 */
public class Opcion {
    private int id;
    private int idventa;
    private int pago;
    private int retiro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getRetiro() {
        return retiro;
    }

    public void setRetiro(int retiro) {
        this.retiro = retiro;
    }
    
    public Opcion(){
        this.id = 0;
        this.idventa = 0;
        this.pago = 0;
        this.retiro = 0;
    }
}
