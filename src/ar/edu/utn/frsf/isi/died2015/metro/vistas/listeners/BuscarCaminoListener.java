package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del panel
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelBuscarCamino PanelBuscarCamino}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelBuscar}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface BuscarCaminoListener extends EventListener
{
    /**
     * Método invocado cuando el usuario presiona el botón "buscar" del panel PanelBuscar.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelBuscar}
     */
    public void buscarCaminoEntreEstaciones(EventObject e);
}
