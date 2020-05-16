package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del diálogo
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoReclamoEstacion
 * DialogoReclamoEstacion}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoReclamoEstacion}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface ReclamoEstacionListener extends EventListener
{
    /**
     * Método invocado cuando el usuario presiona el botón para aceptar el nuevo reclamo sobre la
     * estación.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoReclamoEstacion}
     */
    public void reclamoEstacionAceptado(EventObject e);

    /**
     * Método invocado cuando el usuario presiona el botón para cancelar el nuevo reclamo sobre la
     * estación.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoReclamoEstacion}
     */
    public void reclamoEstacionCancelado(EventObject e);
}
