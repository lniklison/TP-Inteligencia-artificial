package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.util.LinkedHashSet;
import java.util.PriorityQueue;

public class Nodo implements Comparable<Nodo>
{
    private String id;
    private String nombre;
    private boolean accesible;
//    private int tiempoTrasbordo;
//    private String telefono;
//    private LinkedHashSet<Cajero> cajeros;
    private LinkedHashSet<Cuadra> calles;
//    private PriorityQueue<Reclamo> reclamos;
//    private PriorityQueue<Inspeccion> inspecciones;
    
    public Nodo()
    {
        super();
//        this.cajeros = new LinkedHashSet<Cajero>();
        this.calles = new LinkedHashSet<Cuadra>();
//        this.reclamos = new PriorityQueue<Reclamo>();
//        this.inspecciones = new PriorityQueue<Inspeccion>();
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public boolean isAccesible()
    {
        return this.accesible;
    }

    public void setAccesible(boolean accesible)
    {
        this.accesible = accesible;
    }

//    public int getTiempoTrasbordo()
//    {
//        return this.tiempoTrasbordo;
//    }
//
//    public void setTiempoTrasbordo(int tiempoTrasbordo)
//    {
//        this.tiempoTrasbordo = tiempoTrasbordo;
//    }

//    public String getTelefono()
//    {
//        return this.telefono;
//    }
//
//    public void setTelefono(String telefono)
//    {
//        this.telefono = telefono;
//    }

//    public Iterable<Cajero> getCajeros()
//    {
//        return this.cajeros;
//    }
//    
//    public void addCajero(Cajero cajero)
//    {
//        this.cajeros.add(cajero);
//    }
    
    public Iterable<Cuadra> getCuadras()
    {
        return this.calles;
    }
    
    public void addCuadra(Cuadra cuadra)
    {
        this.calles.add(cuadra);
    }
    
//    public PriorityQueue<Reclamo> getReclamos()
//    {
//        return this.reclamos;
//    }
//    
//    public void addReclamo(Reclamo reclamo)
//    {
//        this.reclamos.add(reclamo);
//    }
    
//    public void delReclamos()
//    {
//        this.reclamos = new PriorityQueue<Reclamo>();
//    }
//    
//    public PriorityQueue<Inspeccion> getInspecciones()
//    {
//        return this.inspecciones;
//    }
    
//    public void addInspeccion(Inspeccion inspeccion)
//    {
//        this.inspecciones.add(inspeccion);
//    }
//    
//    public void delInspecciones()
//    {
//        this.inspecciones.clear();
//    }
    
    @Override
    public String toString()
    {
        return this.nombre;
    }

    @Override
    public int compareTo(Nodo o) // para ordenar por nombre
    {
        return this.nombre.compareTo(o.getNombre());
    }
}
