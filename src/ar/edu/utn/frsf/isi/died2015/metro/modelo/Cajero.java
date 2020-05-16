package ar.edu.utn.frsf.isi.died2015.metro.modelo;

/**
 * Clase que modela el cajero de un banco.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class Cajero
{
    private int id;
    private String banco;

    public Cajero(int id, String banco)
    {
        super();
        this.id = id;
        this.banco = banco;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getBanco()
    {
        return banco;
    }

    public void setBanco(String banco)
    {
        this.banco = banco;
    }
    
    @Override
    public String toString()
    {
        return this.banco;
    }
    
    @Override
    public boolean equals(Object obj)
    {    
        if(obj instanceof Cajero)
        {
            Cajero otroCajero = (Cajero) obj;
            return this.banco.equals(otroCajero.getBanco());
        }
        return false;
    }
}
