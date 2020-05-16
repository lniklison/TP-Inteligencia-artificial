package ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.w3c.dom.events.Event;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.TextAreaConLimite;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.TextFieldConLimite;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.ReclamoEstacionListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.SeleccionarFechaListener;

/**
 * Clase que crea un diálogo para registrar un nuevo reclamo sobre alguna de las estaciones.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class DialogoReclamoEstacion extends JDialog implements ActionListener, DocumentListener,
        SeleccionarFechaListener
{
    private static final long serialVersionUID = 3989964810366421836L;

    // Cantidad máxima de caracteres que se pueden ingresar en el campo "motivo" de un reclamo.
    private static final int LIM_CHAR_MOTIVO = 300;
    // Formateador para generar un String con la fecha a partir de un objeto Date.
    private static final SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");

    private ArrayList<ReclamoEstacionListener> listeners;

    private JLabel lblEstacion, lblFecha, lblNombre, lblApellido, lblDocumento, lblTelefono,
            lblMotivo, lblCaracteresRestantes;
    private JComboBox<Nodo> cboxEstacion;
    private JTextField fecha;
    private TextFieldConLimite nombre, apellido, numeroDocumento, telefono;
    private TextAreaConLimite motivo;
    private JScrollPane scroll;
    private JButton btnAceptar, btnCancelar, btnFecha;
    private JPanel panelBotones;
    private DialogoSeleccionarFecha dialogoFecha;

    private Date ultimaFecha;

    /**
     * Constructor del diálogo.
     * 
     * @param owner
     *            Frame padre de este diálogo.
     * @param modal
     *            Modalidad del diálogo. True para habilitarlo, False en caso contrario.
     */
    public DialogoReclamoEstacion(Frame owner, boolean modal)
    {
        super(owner, modal);
        this.setTitle("Nuevo Reclamo");
        this.setMinimumSize(new Dimension(400, 400));

        // Delegamos la responsabilidad de la locación al sistema operativo.
        this.setLocationByPlatform(true);

        // Creamos la lista de listeners.
        listeners = new ArrayList<ReclamoEstacionListener>();

        // Obtenemos el contentpane.
        JPanel cp = (JPanel) this.getContentPane();

        // Creamos los componentes.
        this.lblFecha = new JLabel("Fecha");
        this.lblEstacion = new JLabel("Estación");
        this.lblNombre = new JLabel("Nombre");
        this.lblApellido = new JLabel("Apellido");
        this.lblDocumento = new JLabel("Documento");
        this.lblTelefono = new JLabel("Teléfono");
        this.lblMotivo = new JLabel("Motivo");
        this.lblCaracteresRestantes = new JLabel("0/" + String.valueOf(LIM_CHAR_MOTIVO));
        this.lblCaracteresRestantes.setFont(new Font("Dialog", Font.PLAIN, 9));

        this.cboxEstacion = new JComboBox<Nodo>();
        this.fecha = new JTextField();

        this.nombre = new TextFieldConLimite(40, false);
        this.apellido = new TextFieldConLimite(30, false);
        this.numeroDocumento = new TextFieldConLimite(11, false);
        this.telefono = new TextFieldConLimite(15, false);

        this.scroll = new JScrollPane();
        this.motivo = new TextAreaConLimite(LIM_CHAR_MOTIVO, false);
        this.scroll.setViewportView(motivo);

        this.panelBotones = new JPanel();
        this.btnAceptar = new JButton("Aceptar");
        this.btnCancelar = new JButton("Cancelar");

        this.btnFecha = new JButton("...");

        this.dialogoFecha = new DialogoSeleccionarFecha(this, true);
        this.ultimaFecha = this.dialogoFecha.getFecha();
        this.fecha.setEditable(false);
        this.fecha.setHorizontalAlignment(JTextField.RIGHT);
        this.fecha.setText(formateadorFecha.format(this.ultimaFecha));

        // Creamos el layout y distribuimos los componentes.
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{0, 0, 0, 0};
        gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        cp.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridy = 0;
        cp.add(this.lblEstacion, gbc);

        gbc.gridy = 1;
        cp.add(this.lblFecha, gbc);

        gbc.gridy = 2;
        cp.add(this.lblNombre, gbc);

        gbc.gridy = 3;
        cp.add(this.lblApellido, gbc);

        gbc.gridy = 4;
        cp.add(this.lblTelefono, gbc);

        gbc.gridy = 5;
        cp.add(this.lblDocumento, gbc);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        cp.add(this.lblMotivo, gbc);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;

        gbc.gridx = 1;
        cp.add(this.fecha, gbc);

        gbc.gridx = 2;
        cp.add(this.btnFecha, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        gbc.gridy = 0;
        cp.add(this.cboxEstacion, gbc);

        gbc.gridy = 2;
        cp.add(this.nombre, gbc);

        gbc.gridy = 3;
        cp.add(this.apellido, gbc);

        gbc.gridy = 4;
        cp.add(this.telefono, gbc);

        gbc.gridy = 5;
        cp.add(this.numeroDocumento, gbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        cp.add(this.scroll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 5, 5, 0);
        add(this.panelBotones, gbc);

        this.panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        this.panelBotones.add(this.btnAceptar);
        this.panelBotones.add(this.btnCancelar);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        cp.add(this.lblCaracteresRestantes, gbc);

        // Registramos los listeners.
        this.btnAceptar.addActionListener(this);
        this.btnCancelar.addActionListener(this);
        this.btnFecha.addActionListener(this);
        this.motivo.getDocument().addDocumentListener(this);
        this.dialogoFecha.addSeleccionarFechaListener(this);


        this.pack();
    }

    /**
     * Registra un nuevo listener al diálogo.
     * 
     * @param listener
     *            Clase que implementa la interfaz
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.ReclamoEstacionListener
     *            ReclamoEstacionListener}.
     */
    public void addReclamoEstacionListener(ReclamoEstacionListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Establece el vector de estaciones pasado como argumento como las opciones seleccionables de
     * las estaciones. La primer estación del vector aparecerá seleccionada por defecto.
     * 
     * @param estaciones
     *            Vector de tipo {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion}
     */
    public void setModeloEstaciones(Vector<Nodo> estaciones)
    {
        DefaultComboBoxModel<Nodo> m = new DefaultComboBoxModel<Nodo>(estaciones);
        this.cboxEstacion.setModel(m);
        if(m.getSize() != 0) this.cboxEstacion.setSelectedIndex(0);
    }

    /**
     * Retorna un objeto {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion} que ha
     * sido seleccionado en el campo "Estación".
     */
    public Nodo getEstacionSeleccionada()
    {
        return (Nodo) this.cboxEstacion.getSelectedItem();
    }

    /**
     * Retorna un entero con el índice de la estación seleccionada en el campo "Estación".
     */
    public int getIndiceEstacionSeleccionada()
    {
        return this.cboxEstacion.getSelectedIndex();
    }

    /**
     * Marca como seleccionada el objeto {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo
     * Estacion} pasado como argumento en el campo "Estación". Si esta no se encuentra en la lista,
     * se ignora la llamada a este método.
     * 
     * @param e
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion} a seleccionar.
     */
    public void seleccionarEstacion(Nodo e)
    {
        this.cboxEstacion.setSelectedItem(e);
    }

    /**
     * Marca como seleccionada la estación que ocupa la posición establecida por el argumento. Si el
     * indice está fuera de los valores correctos ( 0 <= indice < #{estaciones en la lista}) se
     * ignora la llamada a este método.
     * 
     * @param indice
     *            Entero, índice de la estación a seleccionar.
     */
    public void seleccionarEstacionPorIndice(int indice)
    {
        try
        {
            this.cboxEstacion.setSelectedIndex(indice);
        }
        catch(IllegalArgumentException e)
        {}

    }

    /**
     * Retorna la última fecha seleccionada.
     */
    public Date getFecha()
    {
        return this.ultimaFecha;
    }

    /**
     * Retorna el nombre ingresado en el campo "Nombre".
     */
    public String getNombre()
    {
        return this.nombre.getText();
    }

    /**
     * Retorna el apellido ingresado en el campo "Apellido".
     */
    public String getApellido()
    {
        return this.apellido.getText();
    }

    /**
     * Retorna el teléfono ingresado en el campo "Teléfono".
     */
    public String getTelefono()
    {
        return this.telefono.getText();
    }

    /**
     * Retorna el número de documento ingresado en el campo "Número de documento".
     */
    public String getNumeroDocumento()
    {
        return this.numeroDocumento.getText();
    }

    /**
     * Obtiene el motivo del reclamo ingresado en el campo "Motivo".
     */
    public String getMotivo()
    {
        return this.motivo.getText();
    }

    /**
     * Limpia el formulario del diálogo.
     */
    public void limpiar()
    {
        // Por defecto, seleccionamos por defecto la primer estación.
        if(this.cboxEstacion.getModel().getSize() != 0)
            this.cboxEstacion.setSelectedIndex(0);
        // Borramos los campos de texto.
        this.nombre.setText("");
        this.apellido.setText("");
        this.numeroDocumento.setText("");
        this.telefono.setText("");
        this.motivo.setText("");
    }

    private boolean camposCorrectos()
    {
        // Por el momento verificamos que el campo de motivo no puede ser vacío.
        if(this.motivo.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar un motivo para poder generar el reclamo.\n",
                            "Ingrese un motivo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnFecha)
        {
            // Cada vez que precionamos el botón de la fecha "...", mostramos el díalogo del
            // calendario mostrando seleccionado el día de hoy.
            this.dialogoFecha.seleccionarFechaHoy();
            this.dialogoFecha.setVisible(true);
        } else if(e.getSource() == this.btnAceptar)
        {
            if(camposCorrectos())
            {
                for(ReclamoEstacionListener l : listeners)
                    l.reclamoEstacionAceptado(new EventObject(this));
            }

        } else if(e.getSource() == this.btnCancelar)
        {
            for(ReclamoEstacionListener l : listeners)
                l.reclamoEstacionCancelado(new EventObject(this));
        }
    }

    // Cada vez que se modifica el área de texto del motivo, actualizamos la cantidad de caracteres
    // restantes.
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        this.lblCaracteresRestantes.setText(String.valueOf(e.getDocument().getLength()) + "/"
                + String.valueOf(LIM_CHAR_MOTIVO));
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        this.lblCaracteresRestantes.setText(String.valueOf(e.getDocument().getLength()) + "/"
                + String.valueOf(LIM_CHAR_MOTIVO));
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        this.lblCaracteresRestantes.setText(String.valueOf(e.getDocument().getLength()) + "/"
                + String.valueOf(LIM_CHAR_MOTIVO));
    }

    @Override
    public void handleEvent(Event evt)
    {}

    @Override
    public void nuevaFechaSeleccionada(EventObject e)
    {
        // Si hay una nueva fecha seleccionada, la guardamos como la última seleccionada si no es 
        // una fecha posterior al día de hoy.
        Date fecha = dialogoFecha.getFecha();
        Date hoy = new Date();
        if(fecha.after(hoy))
        {
            JOptionPane.showMessageDialog(
                            this,
                            "La fecha seleccionada no puede ser posterior al día de hoy.\n"
                            + "Por favor seleccione una nueva fecha.",
                            "Fecha inválida", JOptionPane.INFORMATION_MESSAGE);
        }else
        {
            this.ultimaFecha = fecha;
            this.fecha.setText(formateadorFecha.format(this.ultimaFecha));
        }
    }

}
