package ar.edu.utn.frsf.isi.died2015.metro.modelo;

public class Cuadra
{
    private Nodo origen, destino;
    private boolean habilitado;
//    private LineaTren linea;
    private int tiempo;
    
    public Cuadra()
    {
        super();
        this.origen = null;
        this.destino = null;
        this.habilitado = false;
//        this.linea = null;
    }

    public Cuadra(Nodo origen, Nodo destino, boolean habilitado)
    {
        super();
        setOrigen(origen);
        setDestino(destino);
        this.habilitado = habilitado;
//        setLineaTren(linea);
    }

    public Nodo getOrigen()
    {
        return this.origen;
    }

    public void setOrigen(Nodo origen)
    {
        if(origen == null)
            throw new IllegalArgumentException("La estación origen no puede ser nula.");
        this.origen = origen;
    }

    public Nodo getDestino()
    {
        return this.destino;
    }

    public void setDestino(Nodo destino)
    {
        if(destino == null)
            throw new IllegalArgumentException("La estación destino no puede ser nula.");
        this.destino = destino;
    }

    public boolean isHabilitado()
    {
        return this.habilitado;
    }

    public void setHabilitado(boolean habilitado)
    {
        this.habilitado = habilitado;
    }

//    public void setLineaTren(LineaTren linea)
//    {
//        if(linea == null)
//            throw new IllegalArgumentException("La línea de tren no puede ser nula.");
//        this.linea = linea;
//    }
//
//    public LineaTren getLineaTren()
//    {
//        return this.linea;
//    }

    public int getTiempo()
    {
        return tiempo;
    }

    public void setTiempo(int tiempo)
    {
        this.tiempo = tiempo;
    }
    
    public double getMetros(){
        double dX = this.destino.getPosX() - this.origen.getPosX();
        double dY = this.destino.getPosY() - this.origen.getPosY();
        double r = Math.sqrt((Math.pow(dX, 2) + Math.pow(dY, 2)));
        return r;
    }

    @Override
    public String toString()
    {
        
        return this.origen.toString() + " => "+ this.destino.toString();
    }
        
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Cuadra)
        {
            Cuadra otroTramo = (Cuadra) obj;
            return (this.origen.equals(otroTramo.getOrigen()) && this.destino.equals(otroTramo
                    .getDestino()));
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.origen.hashCode() ^ this.destino.hashCode();
    }

}
