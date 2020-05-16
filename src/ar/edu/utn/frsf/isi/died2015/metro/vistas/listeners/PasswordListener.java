package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Intefaz listener utilizada para recibir eventos asincrónicos del diálogo
 * {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.PanelPassword DialogoPassword}.
 *
 * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.PanelPassword DialogoPassword}
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public interface PasswordListener extends EventListener
{
    /**
     * Método invocado cuando el usuario presiona el botón "Aceptar" del diálogo.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.PanelPassword DialogoPassword}
     */
    public void passwordIngresada(EventObject e);
    
    
    /**
     * Método invocado cuando el usuario presiona el botón "Cancelar" del diálogo.
     * 
     * @param e
     *            un objeto que manda el mensaje.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.PanelPassword DialogoPassword}
     */
    public void passwordCancelada(EventObject e);
}
