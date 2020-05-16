package ar.edu.utn.frsf.isi.died2015.metro.modelo;

public class Calle
{
    private String nombre;
    
    public Calle(String nombre)
    {
        super();
        this.nombre = nombre;
    }

    
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    @Override
    public String toString()
    {
        
        return this.nombre;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Calle)
        {
            Calle otraLinea = (Calle) obj;
            return this.nombre.equals(otraLinea.getNombre());
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return this.nombre.hashCode();
    }

}
