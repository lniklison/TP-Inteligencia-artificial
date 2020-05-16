package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.awt.Color;

public class Arco
{
    private Vertice inicio, fin;
    private boolean habilitado;
    private Object dato;
    private Color color;
    
    public Arco(Vertice inicio, Vertice fin)
    {
        super();

        if(inicio == null || fin == null)
            throw new NullPointerException("Ninguno de los vertices puede ser nulos.");

        this.inicio = inicio;
        this.fin = fin;
        this.habilitado = true;
        this.dato = new Object();
    }

    public Arco(Vertice inicio, Vertice fin, boolean habilitado, Object dato)
    {
        super();

        if(inicio == null || fin == null)
            throw new NullPointerException("Ninguno de los vertices puede ser nulos.");

        this.inicio = inicio;
        this.fin = fin;
        this.habilitado = habilitado;
        this.dato = dato;
    }

    public Vertice getInicio()
    {
        return inicio;
    }

    public void setInicio(Vertice inicio)
    {
        this.inicio = inicio;
    }

    public Vertice getFin()
    {
        return fin;
    }

    public void setFin(Vertice fin)
    {
        this.fin = fin;
    }

    public boolean isHabilitado()
    {
        return this.habilitado;
    }

    public void setHabilitado(boolean habilitado)
    {
        this.habilitado = habilitado;
    }

    public Arco getInverso()
    {
        return new Arco(this.fin, this.inicio, this.habilitado, this.dato);
    }

    public Object getDato()
    {
        return this.dato;
    }

    public void setInfo(Object dato)
    {
        this.dato = dato;
    }

    public Color getColor()
    {
        return this.color;
        
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Arco)
        {
            Arco otroArco = (Arco) obj;
            return (this.inicio.equals(otroArco.getInicio()) && this.fin.equals(otroArco.getFin()));
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return this.inicio.hashCode() ^ this.fin.hashCode();
    }

    @Override
    public String toString()
    {
        return new String("[" + this.inicio.getId() + " => " + this.fin.getId() + "]");
    }

}
