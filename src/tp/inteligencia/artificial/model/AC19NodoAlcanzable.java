/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp.inteligencia.artificial.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Gino
 */
public class AC19NodoAlcanzable {
    private Integer nodo;
    private String calle;
    private Double x;
    private Double y;

    public AC19NodoAlcanzable(Integer nodo, String calle) {
        this.nodo = nodo;
        this.calle = calle;
        
        generarPosicionAleatoria();
    }

    private void generarPosicionAleatoria() {
        int g = (int) ((Math.random()*1000)%360);
        this.x = Math.cos(g);
        this.y = Math.sin(g);
        System.out.println("Grados: " + g + "\tPosX: " + this.x + "\tPosY: " + this.y);
    }

    public Integer getNodo() {
        return nodo;
    }

    public void setNodo(Integer nodo) {
        this.nodo = nodo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
