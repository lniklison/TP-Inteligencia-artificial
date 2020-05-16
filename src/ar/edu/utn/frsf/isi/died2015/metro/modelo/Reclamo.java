package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.util.Date;

/**
 * Clase que modela un reclamo sobre una estación.
 * 
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Estacion}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class Reclamo implements Comparable<Reclamo>
{
    private Nodo estacion;
    private Date fecha;
    private String nombre, apellido, numeroDocumento, motivo, telefono, id;
    
    /**
     * Construye un reclamo vacío.
     */
    public Reclamo()
    {
        super();
        this.estacion = null;
    }

    /**
     * Construye un reclamo parametrizado.
     * 
     * @param id
     *            Identificador único del reclamo.
     * @param estacion
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estación} sobre la cual
     *            se quiere realizar el reclamo.
     * @param fecha
     *            Fecha del reclamo.
     * @param nombre
     *            Nombre de la persona que realiza el reclamo.
     * @param apellido
     *            Apellido de la persona que realiza el reclamo.
     * @param telefono
     *            Teléfono de la persona que realiza el reclamo.
     * @param numeroDocumento
     *            Número de documento de la persona que realiza el reclamo.
     * @param motivo
     *            Motivo por el cual se realiza el reclamo.
     */
    public Reclamo(String id, Nodo estacion, Date fecha, String nombre, String apellido,
            String telefono, String numeroDocumento, String motivo)
    {
        super();
        this.id = id;
        this.estacion = estacion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.numeroDocumento = numeroDocumento;
        this.motivo = motivo;
    }

    /**
     * Retorna el identificador único del reclamo.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Establece el identificador único del reclamo.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Retorna la {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo estación} sobre la cual
     * se realiza el reclamo.
     */
    public Nodo getEstacion()
    {
        return estacion;
    }

    /**
     * Establece la {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo estación} sobre la
     * cual se realiza el reclamo.
     */
    public void setEstacion(Nodo estacion)
    {
        this.estacion = estacion;
    }

    /**
     * Retorna la fecha del reclamo.
     */
    public Date getFecha()
    {
        return this.fecha;
    }

    /**
     * Establece la fecha del reclamo.
     */
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    /**
     * Retorna el nombre de la persona que realiza el reclamo.
     */
    public String getNombre()
    {
        return this.nombre;
    }

    /**
     * Establece el nombre de la persona que realiza el reclamo.
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Retorna el apellido de la persona que realiza el reclamo.
     */
    public String getApellido()
    {
        return this.apellido;
    }

    /**
     * Establece el apellido de la persona que realiza el reclamo.
     */
    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    /**
     * Retrona el teléfono de la persona que realiza el reclamo.
     */
    public String getTelefono()
    {
        return this.telefono;
    }

    /**
     * Establece el telefono de la persona que realiza el reclamo.
     */
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    /**
     * Retorna el número de documento de la persona que realiza el reclamo.
     */
    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento de la persona que realiza el reclamo.
     */
    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Retorna el motivo por el cual se realiza el reclamo.
     */
    public String getMotivo()
    {
        return motivo;
    }

    /**
     * Establece el motivo por el cual se realiza el reclamo.
     */
    public void setMotivo(String motivo)
    {
        this.motivo = motivo;
    }

    @Override
    public int compareTo(Reclamo o)
    {
        return this.fecha.compareTo(o.getFecha());
        
    }

}
