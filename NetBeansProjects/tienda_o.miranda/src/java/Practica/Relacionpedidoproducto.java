/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Practica;

/**
 *
 * @author oscarmirandabravo
 */
public class Relacionpedidoproducto {
    
    String nombre;
    int numero;
    int cantidad;

    public Relacionpedidoproducto() {
    }

    public Relacionpedidoproducto(String nombre, int numero, int cantidad) {
        this.nombre = nombre;
        this.numero = numero;
        this.cantidad = cantidad;
    }

    
    
    public int getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    
    
}
