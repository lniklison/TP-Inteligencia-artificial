package ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos;

import java.awt.Color;
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
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Reclamo;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.ModeloTablaReclamos;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.InspeccionListener;

/**
 * Clase que crea un diálogo para mostrar los reclamos de las estaciones y confirmar si se quiere
 * realizar una/s inspección/es sobre la/s estación/es involucrada/s.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class DialogoInspeccion extends JDialog implements ActionListener, ListSelectionListener
{
    private static final long serialVersionUID = -4724227136652464057L;

    private ArrayList<InspeccionListener> listeners;

    private JPanel panelDetalles, panelReclamos, panelBotones;
    private JLabel lblEstacion, lblFecha, lblNombre, lblApellido, lblTipoDocumento, lblDocumento,
                    lblMotivo;
    private JTextField estacion, fecha, nombre, apellido, telefono, documento;
    private JTextArea motivo;
    private JScrollPane scrollReclamos, scrollMotivo;
    private JTable tablaReclamos;
    private JButton btnAceptar, btnCancelar;

    private Vector<Nodo> estaciones;

    /**
     * Constructor del diálogo.
     * 
     * @param owner
     *            Frame padre de este diálogo.
     * @param modal
     *            Modalidad del diálogo. True para habilitarlo, False en caso contrario.
     */
    public DialogoInspeccion(Frame owner, boolean modal)
    {
        super(owner, modal);
        this.setTitle("Confirmar la generación de inspecciones");
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(this.getPreferredSize());

        // Delegamos la responsabilidad de la locación al sistema operativo.
        this.setLocationByPlatform(true);

        this.estaciones = new Vector<Nodo>();

        // Creamos la lista de listeners.
        this.listeners = new ArrayList<InspeccionListener>();

        // Creamos los componentes.
        this.panelDetalles = new JPanel();
        this.panelReclamos = new JPanel();
        this.panelBotones = new JPanel();

        this.lblFecha = new JLabel("Fecha");
        this.lblEstacion = new JLabel("Estación");
        this.lblNombre = new JLabel("Nombre");
        this.lblApellido = new JLabel("Apellido");
        this.lblDocumento = new JLabel("Documento");
        this.lblTipoDocumento = new JLabel("Tipo de documento");
        this.lblMotivo = new JLabel("Motivo");

        this.estacion = new JTextField();
        this.fecha = new JTextField();
        this.nombre = new JTextField();
        this.apellido = new JTextField();
        this.telefono = new JTextField();
        this.documento = new JTextField();

        this.motivo = new JTextArea();

        this.tablaReclamos = new JTable(new ModeloTablaReclamos());

        this.scrollReclamos = new JScrollPane();
        this.scrollReclamos.setViewportView(this.tablaReclamos);

        this.scrollMotivo = new JScrollPane();
        this.scrollMotivo.setViewportView(this.motivo);

        this.btnAceptar = new JButton("Confirmar");
        this.btnCancelar = new JButton("Cancelar");

        // Configuramos los componentes.

        // Hacemos los campos de texto como no editables, sólo muestran valores.
        this.estacion.setEditable(false);
        this.fecha.setEditable(false);
        this.nombre.setEditable(false);
        this.apellido.setEditable(false);
        this.telefono.setEditable(false);
        this.documento.setEditable(false);
        this.motivo.setEditable(false);
        this.motivo.setRows(10);

        // Configuramos la tabla de reclamos.
        this.tablaReclamos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tablaReclamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tablaReclamos.setRowSelectionAllowed(true);
        this.tablaReclamos.getTableHeader().setReorderingAllowed(false);
        this.tablaReclamos.setAutoCreateRowSorter(true);

        this.scrollReclamos
                        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Configuramos los bordes de los paneles de reclamos y detalles.
        this.panelReclamos.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
                        "Reclamos", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

        this.panelDetalles.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
                        "Detalles", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

        // Creamos los layouts y distribuimos los componentes.

        // Distribuimos el panel de los reclamos.
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0 };
        gbl.rowHeights = new int[] { 0, 0 };
        gbl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        this.panelReclamos.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.panelReclamos.add(this.scrollReclamos, gbc);

        // Distibuimos el panel de detalles de los reclamos.
        gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        this.panelDetalles.setLayout(gbl);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridy = 0;
        this.panelDetalles.add(this.lblEstacion, gbc);

        gbc.gridy = 1;
        this.panelDetalles.add(this.lblFecha, gbc);

        gbc.gridy = 2;
        this.panelDetalles.add(this.lblNombre, gbc);

        gbc.gridy = 3;
        this.panelDetalles.add(this.lblApellido, gbc);

        gbc.gridy = 4;
        this.panelDetalles.add(this.lblTipoDocumento, gbc);

        gbc.gridy = 5;
        this.panelDetalles.add(this.lblDocumento, gbc);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.panelDetalles.add(this.lblMotivo, gbc);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;

        gbc.gridx = 1;
        this.panelDetalles.add(this.fecha, gbc);

        gbc.gridx = 1;

        gbc.gridy = 0;
        this.panelDetalles.add(this.estacion, gbc);

        gbc.gridy = 2;
        this.panelDetalles.add(this.nombre, gbc);

        gbc.gridy = 3;
        this.panelDetalles.add(this.apellido, gbc);

        gbc.gridy = 4;
        this.panelDetalles.add(this.telefono, gbc);

        gbc.gridy = 5;
        this.panelDetalles.add(this.documento, gbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        this.panelDetalles.add(this.scrollMotivo, gbc);

        // Distibuimos el panel principal (content pane).

        // Obtenemos el content pane.
        JPanel cp = (JPanel) this.getContentPane();

        gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0 };
        gbl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 1.0, 0.1, 0.0, Double.MIN_VALUE };
        cp.setLayout(gbl);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;

        gbc.gridy = 0;
        cp.add(this.panelReclamos, gbc);

        gbc.gridy = 1;
        cp.add(this.panelDetalles, gbc);

        this.panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        this.panelBotones.add(this.btnAceptar);
        this.panelBotones.add(this.btnCancelar);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cp.add(this.panelBotones, gbc);

        // Registramos los listeners.
        this.btnAceptar.addActionListener(this);
        this.btnCancelar.addActionListener(this);
        this.tablaReclamos.getSelectionModel().addListSelectionListener(this);

        this.pack();
    }

    /**
     * Registra un nuevo listener al diálogo.
     * 
     * @param listener
     *            Clase que implementa la interfaz
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.InspeccionListener
     *            InspeccionListener}.
     */
    public void addInspeccionListener(InspeccionListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Establece la lista de {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo estaciones} a
     * las cuales se le registrarán las inspecciones.
     */
    public void setEstaciones(Vector<Nodo> estaciones)
    {
        this.estaciones = estaciones;

        Vector<Reclamo> reclamos = new Vector<Reclamo>();
//        for (Nodo e : estaciones)
//        {
//            PriorityQueue<Reclamo> pqReclamos = e.getReclamos();
//            while (!pqReclamos.isEmpty())
//                reclamos.add(pqReclamos.poll());
//        }

        ModeloTablaReclamos m = new ModeloTablaReclamos();
        m.setDatos(reclamos);
        this.tablaReclamos.clearSelection();
        this.tablaReclamos.setModel(m);
    }
    
    public Vector<Nodo> getEstaciones()
    {
        return this.estaciones;
    }

    /**
     * Limpia todos los componentes del diálogo.
     */
    public void limpiar()
    {
        this.tablaReclamos.clearSelection();
        this.tablaReclamos.setModel(new ModeloTablaReclamos());
        this.estacion.setText("");
        this.fecha.setText("");
        this.nombre.setText("");
        this.apellido.setText("");
        this.telefono.setText("");
        this.documento.setText("");
        this.motivo.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAceptar)
        {
            for (InspeccionListener l : listeners)
                l.inspeccionAceptada(new EventObject(this));
        } else if (e.getSource() == this.btnCancelar)
        {
            for (InspeccionListener l : listeners)
                l.inspeccionCancelada(new EventObject(this));
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getSource() == this.tablaReclamos.getSelectionModel())
        {
            // Cada vez que seleccionamos un reclamo de la tabla, mostramos sus detalles en el panel
            // de detalles.
            if (!e.getValueIsAdjusting())
            {
                Reclamo r = null;
                int indice = 0;

                try
                {
                    indice = this.tablaReclamos.getSelectedRow();
                    r = ((ModeloTablaReclamos) this.tablaReclamos.getModel()).getDatos().elementAt(
                                    indice);
                } catch (ArrayIndexOutOfBoundsException e2)
                {}

                if (r != null)
                {
                    this.estacion.setText(r.getEstacion().toString());
                    this.fecha.setText((String) this.tablaReclamos.getModel().getValueAt(indice, 0));
                    this.nombre.setText(r.getNombre());
                    this.apellido.setText(r.getApellido());
                    this.telefono.setText(r.getTelefono());
                    this.documento.setText(r.getNumeroDocumento());
                    this.motivo.setText(r.getMotivo());
                }
            }
        }

    }
}
