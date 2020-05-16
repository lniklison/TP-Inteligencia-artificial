package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventObject;

import org.w3c.dom.events.EventListener;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del diálogo
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoSeleccionarFecha
 * DialogoSeleccionarFecha}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoSeleccionarFecha}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface SeleccionarFechaListener extends EventListener
{
    /**
     * Método invocado cuando el usuario selecciona alguna fecha en el calendario.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoSeleccionarFecha}
     */
    public void nuevaFechaSeleccionada(EventObject e);
}
