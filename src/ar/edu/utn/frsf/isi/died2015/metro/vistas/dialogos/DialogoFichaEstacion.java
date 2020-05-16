package ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cajero;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Inspeccion;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Calle;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Reclamo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cuadra;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.ModeloTablaReclamos;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.FichaEstacionListener;

public class DialogoFichaEstacion extends JDialog implements ActionListener
{
    private static final long serialVersionUID = -4531046279077311377L;

    private ArrayList<FichaEstacionListener> listeners;
    
    private JLabel lblEstacion, lblAccesible, lblTelefono, lblTiempo, lblLineas, lblCajeros,
            lblReclamos, lblInspecciones;
    private JLabel accesible, telefono, tiempo;
    private JComboBox<Nodo> cboxEstaciones;
    private JList<Cajero> listaCajeros;
    private JList<Inspeccion> listaInspecciones;
    private JList<Calle> listaLineas;
    private JScrollPane scrollCajeros, scrollInspecciones, scrollLineas, scrollReclamos;
    private JTable tablaReclamos;
    private JButton btnAceptar;
    private JPanel panelBotones;
    
    
    
    public DialogoFichaEstacion(Frame owner, boolean modal)
    {
        super(owner, modal);
        this.setTitle("Fichas de las Estaciones");
        this.setPreferredSize(new Dimension(400,600));
        
        this.listeners = new ArrayList<FichaEstacionListener>();
        
        // Delegamos la responsabilidad de la locación al sistema operativo.
        this.setLocationByPlatform(true);

        
        //Obtenemos el contentpane.
        JPanel cp = (JPanel) this.getContentPane();
        
        cp.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        // Creamos los componentes.
        this.lblEstacion = new JLabel("Estacion:");
        this.lblTelefono = new JLabel("Teléfono:");
        this.lblAccesible  = new JLabel("Accesible:");
        this.lblTiempo = new JLabel("Tiempo de transbordo:");
        this.lblLineas = new JLabel("Líneas:");
        this.lblCajeros = new JLabel("Cajeros:");
        this.lblReclamos = new JLabel("Reclamos:");
        this.lblInspecciones = new JLabel("Inspecciones:");
        
        this.telefono = new JLabel("");
        this.accesible = new JLabel("");
        this.tiempo = new JLabel("");
                
        this.cboxEstaciones = new JComboBox<Nodo>();
        
        this.listaCajeros = new JList<Cajero>();
        this.listaInspecciones = new JList<Inspeccion>();
        this.listaLineas = new JList<Calle>();
        
        this.tablaReclamos = new JTable(new ModeloTablaReclamos());
        this.tablaReclamos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tablaReclamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tablaReclamos.setRowSelectionAllowed(true);
        this.tablaReclamos.getTableHeader().setReorderingAllowed(false);
        this.tablaReclamos.setAutoCreateRowSorter(true);
        
        this.scrollInspecciones = new JScrollPane();
        this.scrollInspecciones.setViewportView(this.listaInspecciones);
        this.scrollCajeros = new JScrollPane();
        this.scrollCajeros.setViewportView(this.listaCajeros);
        this.scrollReclamos = new JScrollPane();
        this.scrollReclamos.setViewportView(this.tablaReclamos);
        this.scrollLineas = new JScrollPane();
        this.scrollLineas.setViewportView(this.listaLineas);
        
        this.panelBotones = new JPanel();
        this.btnAceptar = new JButton("Aceptar");
            
        // Creamos el layout y posicionamos los componentes.
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{0, 0, 0};
        gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        cp.setLayout(gbl);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridy = 0;
        cp.add(this.lblEstacion, gbc);
        
        gbc.gridy = 1;
        cp.add(this.lblTelefono, gbc);
        
        gbc.gridy = 2;
        cp.add(this.lblAccesible, gbc);
        
        gbc.gridy = 3;
        cp.add(this.lblTiempo, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        gbc.gridy = 4;
        cp.add(this.lblLineas, gbc);
        
        gbc.gridy = 5;
        cp.add(this.lblCajeros, gbc);
        
        gbc.gridy = 6;
        cp.add(this.lblReclamos, gbc);
        
        gbc.gridy = 7;
        cp.add(this.lblInspecciones, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridx = 1;
        
        gbc.gridy = 0;
        cp.add(this.cboxEstaciones, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridy = 1;
        cp.add(this.telefono, gbc);
        
        gbc.gridy = 2;
        cp.add(this.accesible, gbc);
        
        gbc.gridy = 3;
        cp.add(this.tiempo, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridy = 4;
        cp.add(this.scrollLineas, gbc);
        
        gbc.gridy = 5;
        cp.add(this.scrollCajeros, gbc);
        
        gbc.gridy = 6;
        cp.add(this.scrollReclamos, gbc);
        
        gbc.gridy = 7;
        cp.add(this.scrollInspecciones, gbc);
        
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 8;
        this.panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        this.panelBotones.add(this.btnAceptar);
        cp.add(this.panelBotones, gbc);
        
        // Registramos los listenes
        this.btnAceptar.addActionListener(this);
        this.cboxEstaciones.addActionListener(this);
        
        this.pack();
    }

    public void addFichaEstacionListener(FichaEstacionListener listener)
    {
        this.listeners.add(listener);
    }
    
    public void setModeloEstaciones(Vector<Nodo> estaciones)
    {
        DefaultComboBoxModel<Nodo> m = new DefaultComboBoxModel<Nodo>(estaciones);
        this.cboxEstaciones.setModel(m);
        if(m.getSize() != 0)
            this.cboxEstaciones.setSelectedIndex(0);
    }

    
    public void seleccionarEstacion(Nodo estacion) 
    {
        this.cboxEstaciones.setSelectedItem(estacion);
    }
    
    public void seleccionarEstacionPorIndice(int indice)
    {
        try
        {
            this.cboxEstaciones.setSelectedIndex(indice);
        }catch(IllegalArgumentException e)
        {}
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
       if(e.getSource() == this.btnAceptar)
       {
           for(FichaEstacionListener l : this.listeners)
               l.fichaEstacionAceptada(new EventObject(this));
       } else if(e.getSource() == this.cboxEstaciones)
       {
           Nodo estacion = (Nodo) this.cboxEstaciones.getSelectedItem();
           if(estacion != null)
           {
//               this.telefono.setText(estacion.getTelefono());
               if(estacion.isAccesible())
                   this.accesible.setText("SI");
               else
                   this.accesible.setText("NO");
//               this.tiempo.setText(String.valueOf(estacion.getTiempoTrasbordo()));
               
//               LinkedHashSet<LineaTren> hs = new LinkedHashSet<LineaTren>(); 
//               DefaultListModel<LineaTren> ml = new DefaultListModel<LineaTren>();
//               for(Calle t : estacion.getTramos())
//                   hs.add(t.getLineaTren());
//               for(LineaTren l : hs)
//                   ml.addElement(l);
//               this.listaLineas.setModel(ml);
               
//               DefaultListModel<Cajero> mc = new DefaultListModel<Cajero>();
//               for(Cajero c : estacion.getCajeros())
//                   mc.addElement(c);
//               this.listaCajeros.setModel(mc);
               
//               ModeloTablaReclamos mr = new ModeloTablaReclamos();
//               Vector<Reclamo> reclamos = new Vector<Reclamo>();
//               for(Reclamo r : estacion.getReclamos())
//                   reclamos.add(r);
//               mr.setDatos(reclamos);
//               this.tablaReclamos.setModel(mr);
               
//               DefaultListModel<Inspeccion> mi = new DefaultListModel<Inspeccion>();
//               for(Inspeccion i : estacion.getInspecciones())
//                   mi.addElement(i);
//               this.listaInspecciones.setModel(mi);
               
           }
       }
    }
}