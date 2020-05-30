package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.util.LinkedHashSet;
import java.util.PriorityQueue;

public class Nodo implements Comparable<Nodo>
{
    private String id;
    private String nombre;
    private boolean accesible;
    private double x;
    private double y;
    private LinkedHashSet<Cuadra> calles;
    
    public Nodo(){
        super();
        this.calles = new LinkedHashSet<Cuadra>();
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public boolean isAccesible(){
        return this.accesible;
    }
    public void setAccesible(boolean accesible){
        this.accesible = accesible;
    }
    
    public Iterable<Cuadra> getCuadras(){
        return this.calles;
    }
    public void addCuadra(Cuadra cuadra){
        this.calles.add(cuadra);
    }
    
    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getPosX(){
        return this.x;
    }

    public double getPosY(){
        return this.y;
    }
    
     
    @Override
    public String toString(){
        return this.nombre;
    }

    @Override
    public int compareTo(Nodo o){
        return this.nombre.compareTo(o.getNombre());
    }
}
