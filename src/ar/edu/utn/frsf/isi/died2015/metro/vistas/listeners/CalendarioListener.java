package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del panel
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCalendario PanelCalendario}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCalendario}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface CalendarioListener extends EventListener
{
    /**
     * Método invocado cuando el usuario selecciona alguna fecha en el calendario.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCalendario}
     */
    public void fechaCalendarioSeleccionada(EventObject e);
}
