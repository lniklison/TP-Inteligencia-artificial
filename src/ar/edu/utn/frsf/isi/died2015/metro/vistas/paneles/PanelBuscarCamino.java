package ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.BuscarCaminoListener;

/**
 * Clase que crea un panel para la búsqueda de un camino entre dos estaciones.
 *
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 */
public class PanelBuscarCamino extends JPanel implements ActionListener
{
    private static final long serialVersionUID = -5174151485946159815L;

    private ArrayList<BuscarCaminoListener> listeners;

    private JLabel lblOrigen, lblDestino;
    private JComboBox<Nodo> cboxOrigen, cboxDestino;
    private JButton btnBuscar;

    /**
     * Constructor del panel. Los combobox utilizados para la selección de las estaciones serán
     * cargados con el mismo vector de estaciones pasada como argumento y la primera estación del
     * mismo aparecerá seleccionada por defecto.
     * 
     * @param estaciones
     *            Vector de tipo {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion}
     *            utilizado para rellenar los combobox.
     */
    public PanelBuscarCamino()
    {
        super();

        this.listeners = new ArrayList<BuscarCaminoListener>();

        this.lblOrigen = new JLabel("Estación origen:");
        this.lblDestino = new JLabel("Estación destino:");
        this.cboxOrigen = new JComboBox<Nodo>();
        this.cboxDestino = new JComboBox<Nodo>();
        this.btnBuscar = new JButton("Buscar");

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
        gbl.rowHeights = new int[]{0, 0, 0};
        this.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 5);

        gbc.gridx = 1;
        this.add(lblOrigen, gbc);

        gbc.gridx = 3;
        this.add(lblDestino, gbc);

        gbc.gridx = 5;
        this.add(btnBuscar, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 2;
        this.add(cboxOrigen, gbc);

        gbc.gridx = 4;
        this.add(cboxDestino, gbc);

        this.btnBuscar.addActionListener(this);
    }

    /**
     * Registra un nuevo listener al panel.
     * 
     * @param listener
     *            Clase que implementa la interfaz
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.BuscarCaminoListener
     *            BuscarCaminoListener}.
     */
    public void addBuscarCaminoListener(BuscarCaminoListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Establece el vector de estaciones pasado como argumento como las opciones seleccionables de
     * las estaciones "origen" del camino. La primer estación del vector aparecerá seleccionada por
     * defecto.
     * 
     * @param estaciones
     *            Vector de tipo {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion}
     *            .
     */
    public void setModeloOrigen(Vector<Nodo> estaciones)
    {
        DefaultComboBoxModel<Nodo> m = new DefaultComboBoxModel<Nodo>(estaciones);
        this.cboxOrigen.setModel(m);
    }

    /**
     * Establece el vector de estaciones pasado como argumento como las opciones seleccionables de
     * las estaciones "destino" del camino. La primer estación del vector aparecerá seleccionada por
     * defecto.
     * 
     * @param estaciones
     *            Vector de tipo {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion}
     *            .
     */
    public void setModeloDestino(Vector<Nodo> estaciones)
    {
        
        DefaultComboBoxModel<Nodo> m = new DefaultComboBoxModel<Nodo>(estaciones);
        this.cboxDestino.setModel(m);
    }

    /**
     * Retorna la estación origen seleccionada.
     * 
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Estacion}
     */
    public Nodo getEstacionOrigen()
    {
        return (Nodo) this.cboxOrigen.getSelectedItem();
    }

    /**
     * Retorna la estación destino seleccionada.
     * 
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Estacion}
     */
    public Nodo getEstacionDestino()
    {
        return (Nodo) this.cboxDestino.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnBuscar)
        {
            for(BuscarCaminoListener l : this.listeners)
                l.buscarCaminoEntreEstaciones(new EventObject(this));
        }
    }

    public void setEstacionOrigen(Nodo origen)
    {
        this.cboxOrigen.setSelectedItem(origen);
        
    }

    public void setEstacionDestino(Nodo destino)
    {
        this.cboxDestino.setSelectedItem(destino);
    }
}
