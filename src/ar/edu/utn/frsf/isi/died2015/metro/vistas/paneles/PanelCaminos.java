package ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Camino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.DetalleCamino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Calle;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.TipoDetalleCamino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cuadra;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.CaminoListRenderer;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.DetalleCaminoRenderer;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.CaminoListener;

public class PanelCaminos extends JPanel implements ListSelectionListener, ActionListener
{
    private static final long serialVersionUID = -6528913268539864900L;
    private static final String HTML_OPEN = "<html><b>";
    private static final String HTML_CLOSE = "</b></html>";

    private ArrayList<CaminoListener> listeners;
    
    private JSplitPane split;

    private JPanel panelCaminos;
    private JLabel lblIconoOrigen, lblIconoDestino;
    private JLabel lblOrigen, lblNombreEstacionOrigen, lblDestino, lblNombreEstacionDestino;
    private JList<Camino> listaCaminos;
    private JScrollPane scrollCaminos;

    private JPanel panelDetalle;
    private JList<DetalleCamino> listaDetalles;
    private JScrollPane scrollDetales;
    

    public PanelCaminos()
    {
        super();
        this.setPreferredSize(new Dimension(200,800));
        
        this.listeners  = new ArrayList<CaminoListener>();
        
        // Creamos el panel de los caminos.
        this.panelCaminos = new JPanel();
        this.panelCaminos.setMinimumSize(new Dimension(200, 300));
        this.panelCaminos.setBorder(new TitledBorder(null, "Camino/s", TitledBorder.LEADING,
                        TitledBorder.TOP, null, null));

        this.lblIconoOrigen = new JLabel();
        this.lblIconoOrigen.setIcon(new ImageIcon(getClass().getResource("../imagenes/pin.png")));
        this.lblIconoDestino = new JLabel();
        this.lblIconoDestino.setIcon(new ImageIcon(getClass().getResource("../imagenes/pin.png")));
        this.lblOrigen = new JLabel("Estación Origen:");
        this.lblDestino = new JLabel("Estación Destino:");
        this.lblNombreEstacionOrigen = new JLabel("");
        this.lblNombreEstacionDestino = new JLabel("");
        this.listaCaminos = new JList<Camino>();
        this.listaCaminos.setCellRenderer(new CaminoListRenderer());
        this.listaCaminos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scrollCaminos = new JScrollPane();
        this.scrollCaminos.setViewportView(this.listaCaminos);

        // Agregamos los componentes al panel de caminos.
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 20, 0, 20, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        this.panelCaminos.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);

        gbc.gridy = 0;
        this.panelCaminos.add(this.lblOrigen, gbc);

        gbc.gridy = 2;
        this.panelCaminos.add(this.lblDestino, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;

        gbc.gridy = 1;
        this.panelCaminos.add(this.lblNombreEstacionOrigen, gbc);

        gbc.gridy = 3;
        this.panelCaminos.add(this.lblNombreEstacionDestino, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 5, 10);
        gbc.gridx = 0;

        gbc.gridy = 1;
        this.panelCaminos.add(this.lblIconoOrigen, gbc);

        gbc.gridy = 3;
        this.panelCaminos.add(this.lblIconoDestino, gbc);

        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridwidth = 2;
        gbc.gridy = 4;
        this.panelCaminos.add(this.scrollCaminos, gbc);

        // Panel de detalles.
        this.panelDetalle = new JPanel();
        this.panelDetalle.setBorder(new TitledBorder(null, "Detalles", TitledBorder.LEADING,
                        TitledBorder.TOP, null, null));
        this.listaDetalles = new JList<DetalleCamino>();
        this.listaDetalles.setCellRenderer(new DetalleCaminoRenderer());
        this.listaDetalles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scrollDetales = new JScrollPane();
        this.scrollDetales.setViewportView(this.listaDetalles);

        // Agregamos los componentes al panel de detalles del camino.
        gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0 };
        gbl.rowHeights = new int[] { 0 };
        gbl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        this.panelDetalle.setLayout(gbl);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.panelDetalle.add(this.scrollDetales, gbc);

        // Creamos el SplitPane que contendrá los paneles creados
        this.split = new JSplitPane();
        this.split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.split.setContinuousLayout(true);
        this.split.setTopComponent(this.panelCaminos);
        this.split.setBottomComponent(this.panelDetalle);

        gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0 };
        gbl.rowHeights = new int[] { 0, 0 };
        gbl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        this.setLayout(gbl);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(this.split, gbc);

        // Registramos los listeners.
        this.listaCaminos.addListSelectionListener(this);
    }
    
    public void addCaminoLisetner(CaminoListener listener)
    {
        this.listeners.add(listener);
    }
    
    public void setEstacionesCamino(Nodo origen, Nodo destino)
    {
        this.lblNombreEstacionOrigen.setText(HTML_OPEN+origen.toString()+HTML_CLOSE);
        this.lblNombreEstacionDestino.setText(HTML_OPEN+destino.toString()+HTML_CLOSE);
    }
    
    public void setModeloListaCaminos(PriorityQueue<Camino> caminos)
    {
        DefaultListModel<Camino> m = new DefaultListModel<Camino>();
        this.listaDetalles.setModel(new DefaultListModel<DetalleCamino>());
        
        while(true)
        {
            Camino c = caminos.poll();
            if(c == null) break;
            m.addElement(c);
        }
        this.listaCaminos.setModel(m);
    }
    
    public Camino getCaminoSeleccionado()
    {
        return this.listaCaminos.getSelectedValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getSource() == this.listaCaminos)
        {
            if (!e.getValueIsAdjusting())
            {
                if (this.listaCaminos.getSelectedValue() != null)
                {
                    // cargamos los detalles de los caminos.
                    Camino c = this.listaCaminos.getSelectedValue();
                    DefaultListModel<DetalleCamino> m = new DefaultListModel<DetalleCamino>();
                    for (DetalleCamino d : getDetallesCamino(c))
                        m.addElement(d);
                    this.listaDetalles.setModel(m);
                    
                    // notificamos a los listeners
                    for(CaminoListener l : this.listeners)
                    {
                        l.caminoSeleccionado(new EventObject(this));
                    }
                }
            }
        }

    }

    private Vector<DetalleCamino> getDetallesCamino(Camino camino)
    {
        Vector<DetalleCamino> detalles = new Vector<DetalleCamino>();

        Calle ultimaLinea = null;

        for (Cuadra tramo : camino.getTramos())
        {

            if (tramo.getOrigen() == camino.getOrigen())
            {
                DetalleCamino d = new DetalleCamino();
                d.setTipoDetalle(TipoDetalleCamino.ESTACION_ORIGEN);
                d.setDescripcion("Inicio del recorrido en la estación <b>" + tramo.getOrigen()
                                + "</b>.");
                detalles.add(d);
            }

//            if (ultimaLinea != null)
//            {
//                if (!tramo.getLineaTren().equals(ultimaLinea) )
//                {
//                    DetalleCamino d = new DetalleCamino();
//                    d.setTipoDetalle(TipoDetalleCamino.TRANSBORDO);
//                    d.setDescripcion("Transbordo en la estación <b>" + tramo.getOrigen()
//                                    + "</b> de la línea <b>" + ultimaLinea + "</b> a la línea <b>"
//                                    + tramo.getLineaTren() + "</b>.");
//                    d.setTiempo(tramo.getOrigen().getTiempoTrasbordo());
//                    detalles.add(d);
//                }
//            }

//            ultimaLinea = tramo.getLineaTren();
            {
                DetalleCamino d = new DetalleCamino();
                d.setTipoDetalle(TipoDetalleCamino.ESTACION_INTERMEDIA);
                d.setDescripcion("Recorrido del tramo desde la estación <b>" + tramo.getOrigen()
                                + "</b> hasta la estación <b>" + tramo.getDestino() + "</b>");
                d.setTiempo(tramo.getTiempo());
                detalles.add(d);
            }

            if (tramo.getDestino() == camino.getDestino())
            {
                DetalleCamino d = new DetalleCamino();
                d.setTipoDetalle(TipoDetalleCamino.ESTACION_DESTINO);
                d.setDescripcion("Fin del recorrido en la estación <b>" + tramo.getDestino()
                                + "</b>.");
                d.setTiempo(camino.getTiempo());
                detalles.add(d);
            }

        }

        return detalles;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        this.setVisible(false);
    }
}
