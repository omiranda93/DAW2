package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscarmirandabravo
 */
public class Pedido {
    int numero;
    String cliente;
    boolean preparado;

    public Pedido() {
    }

    public Pedido(int numero, String cliente, boolean preparado) {
        this.numero = numero;
        this.cliente = cliente;
        this.preparado = preparado;
    }

    public String getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isPreparado() {
        return preparado;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPreparado(boolean preparado) {
        this.preparado = preparado;
    }
}
