package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del panel
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCaminos
 * PanelCaminos}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCaminos
 *      PanelCaminos}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface CaminoListener extends EventListener
{
    /**
     * Método invocado cuando el usuario selecciona un nuevo camino en la lista
     * de caminos encontrados.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCaminos
     *      PanelCaminos}
     */
    public void caminoSeleccionado(EventObject e);
}
