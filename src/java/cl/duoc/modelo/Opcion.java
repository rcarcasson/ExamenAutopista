/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.modelo;

/**
 *
 * @author v-carica
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
