package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Camino implements Comparable<Camino>
{
    private Integer tiempo;
    private LinkedList<Cuadra> tramos;
    private LinkedHashSet<Calle> trenes;
    private Nodo origen, destino;


    public Camino()
    {
        this.tramos = new LinkedList<Cuadra>();
        this.trenes = new LinkedHashSet<Calle>();
        this.tiempo = 0;
        this.origen = null;
        this.destino = null;
    }

    public Integer getTiempo()
    {
        return this.tiempo;
    }

    public void setTiempo(int tiempo)
    {
        this.tiempo = tiempo;
    }
    
    public Nodo getOrigen()
    {
        return this.origen;
    }

    public void setOrigen(Nodo origen)
    {
        this.origen = origen;
    }
    
    public Nodo getDestino()
    {
        return this.destino;
    }

    public void setDestino(Nodo destino)
    {
        this.destino = destino;
    }
    
    public void addTramo(Cuadra tramo)
    {
        this.tramos.add(tramo);
//        this.trenes.add(tramo.getLineaTren());
    }

    public int getNumeroLineasTrenes()
    {
        return this.trenes.size();
    }

    public Iterable<Calle> getLineasTrenes()
    {
        return this.trenes;
    }
    
    public Iterable<Cuadra> getTramos()
    {
        return this.tramos;
    }

    @Override
    public String toString()
    {
        String s = "Camino de "+ this.origen.getNombre() + " a " + this.destino.getNombre() + ":\n";
        for(Cuadra t : this.tramos)
            s += t.toString() + "\n";
        s += "Tiempo: " + String.valueOf(this.tiempo) + "\n";
        return s;
    }

    @Override
    public int compareTo(Camino o)
    {
        return this.tiempo.compareTo(o.getTiempo());
    }
}
