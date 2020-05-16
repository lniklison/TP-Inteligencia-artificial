package ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners;

import java.util.EventListener;
import java.util.EventObject;

public interface PanelGrafoListener extends EventListener
{
    public void verticeOrigenSeleccionado(EventObject o);
    public void verticeDestinoSeleccionado(EventObject o);
    public void verFichaEstacion(EventObject o);
}
