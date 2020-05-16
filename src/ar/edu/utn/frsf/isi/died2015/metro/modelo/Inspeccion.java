package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Inspeccion
{
    private Date fecha;
    private static final SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
    
    public Inspeccion()
    {
        super();
        this.setFecha(new Date());
    }
    
    public Inspeccion(Date fecha)
    {
        super();
        this.setFecha(fecha);
    }

    public Date getFecha()
    {
        return this.fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
    
    @Override
    public String toString()
    {
        return "Inspección el día "+formateadorFecha.format(this.fecha);
    }
    
}
