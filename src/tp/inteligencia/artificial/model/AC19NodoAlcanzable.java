/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp.inteligencia.artificial.model;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cuadra;
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
    private Cuadra cuadra;

    public AC19NodoAlcanzable(Integer nodo, String calle, Cuadra c) {
        this.nodo = nodo;
        this.calle = calle;
        this.cuadra = c;
        
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
    
    public Cuadra getCuadra(){
        return this.cuadra;
    }

}
