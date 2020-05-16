package ar.edu.utn.frsf.isi.died2015.metro.modelo;

public class DetalleCamino
{
    private TipoDetalleCamino tipoDetalle;
    private String descripcion;
    private int tiempo;
    
    public DetalleCamino()
    {
        this.tipoDetalle = TipoDetalleCamino.ESTACION_ORIGEN;
        this.descripcion = "";
        this.tiempo = 0;
    }

    public DetalleCamino(TipoDetalleCamino tipoDetalle, String descripcion, int tiempo)
    {
        super();
        this.tipoDetalle = tipoDetalle;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
    }

    public TipoDetalleCamino getTipoDetalle()
    {
        return this.tipoDetalle;
    }

    public void setTipoDetalle(TipoDetalleCamino tipoDetalle)
    {
        this.tipoDetalle = tipoDetalle;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public int getTiempo()
    {
        return this.tiempo;
    }

    public void setTiempo(int tiempo)
    {
        this.tiempo = tiempo;
    }
    
    
    
}
