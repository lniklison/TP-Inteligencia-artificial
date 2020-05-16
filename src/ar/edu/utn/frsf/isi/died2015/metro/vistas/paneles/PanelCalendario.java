package ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.CalendarioRenderer;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.CalendarioListener;

/**
 * Clase que crea un calendario en el cual se puede seleccionar una fecha detereminada.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class PanelCalendario extends JPanel implements ActionListener, ChangeListener,
        ListSelectionListener
{
    private static final long serialVersionUID = 360253377262990447L;

    private static final String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo",
            "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private static final String[] nombresDias = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
    private static final SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");

    private ArrayList<CalendarioListener> listeners;

    private JLabel lblFecha;
    private JButton btnAnioAnterior, btnAnioSiguiente, btnMesAnterior, btnMesSiguiente, btnHoy;
    private JComboBox<String> cboxMeses;
    private JSpinner spAnio;
    private JScrollPane scroll;
    private JTable tblCalendario;
    private DefaultTableModel modeloCalendario;
    private DefaultTableColumnModel modeloColumnaCalendario;
    private GregorianCalendar calendario, calendarioHoy;

    /**
     * Construye un calendario. Por defecto el máximo año seleccionable es el corriente, el mínimo
     * año seleccionable es el corriente menos 100 años y la fecha seleccionada es la fecha actual.
     */
    @SuppressWarnings("serial")
    public PanelCalendario()
    {
        super();

        // Creamos los componentes
        this.listeners = new ArrayList<CalendarioListener>();

        this.calendario = new GregorianCalendar();
        this.calendarioHoy = new GregorianCalendar();
        Date fechaActual = calendario.getTime();
        this.lblFecha = new JLabel(formateadorFecha.format(fechaActual), JLabel.CENTER);

        // Botones.
        this.btnAnioAnterior = new JButton("<<");
        this.btnAnioSiguiente = new JButton(">>");
        this.btnMesAnterior = new JButton("<");
        this.btnMesSiguiente = new JButton(">");
        this.btnHoy = new JButton("Hoy");
        this.btnHoy.setFont(new Font("Dialog", Font.PLAIN, 10));

        // Combobox meses.
        this.cboxMeses = new JComboBox<String>(nombresMeses);
        ((JLabel) cboxMeses.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Dejamos como seleccionado el mes actual.
        this.cboxMeses.setSelectedIndex(this.calendarioHoy.get(GregorianCalendar.MONTH));

        // Spinner año.
        this.spAnio = new JSpinner(new SpinnerNumberModel(calendario.get(GregorianCalendar.YEAR),
                calendario.get(GregorianCalendar.YEAR) - 100,
                calendario.get(GregorianCalendar.YEAR), 1));
        ((DefaultEditor) spAnio.getEditor()).getTextField().setHorizontalAlignment(
                JTextField.HORIZONTAL);

        // Tabla del calendario.

        // Deshabilitamos la edición de las celdas.
        this.modeloCalendario = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        // Dejamos fijo el ancho de las columnas.
        this.modeloColumnaCalendario = new DefaultTableColumnModel()
        {
            @Override
            public void addColumn(TableColumn aColumn)
            {
                aColumn.setMaxWidth(35);
                aColumn.setMinWidth(35);
                aColumn.setPreferredWidth(35);
                super.addColumn(aColumn);
            }
        };

        this.scroll = new JScrollPane();
        this.tblCalendario = new JTable(modeloCalendario);
        this.tblCalendario.setColumnModel(this.modeloColumnaCalendario);
        // Sólo se puede seleccionar una celda.
        this.tblCalendario.setColumnSelectionAllowed(true);
        this.tblCalendario.setRowSelectionAllowed(true);
        this.tblCalendario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // No permitimos el ordenamiento de la tabla ni cambiar su tamaño.
        this.tblCalendario.getTableHeader().setResizingAllowed(false);
        this.tblCalendario.getTableHeader().setReorderingAllowed(false);
        this.tblCalendario.setRowHeight(25); // Alto de las filas.
        // Creamos 6 filas (semanas en un mes).
        modeloCalendario.setRowCount(6);
        // Agregamos los nombres de los días al header de la tabla.
        for(String dia : nombresDias)
            modeloCalendario.addColumn(dia);
        // Coloreamos la tabla.
        this.tblCalendario.setDefaultRenderer(tblCalendario.getColumnClass(0),
                new CalendarioRenderer());
        scroll.setViewportView(tblCalendario);

        // Necesario para el tamaño fijo.
        tblCalendario.setPreferredScrollableViewportSize(tblCalendario.getPreferredSize());
        tblCalendario.setFillsViewportHeight(true);

        // Agregamos los componentes al panel
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{0, 0, 0};
        gbl.rowHeights = new int[]{0, 0, 0, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 1, 0, 1);

        gbc.gridy = 0;

        gbc.gridx = 0;
        this.add(btnAnioAnterior, gbc);

        gbc.gridx = 1;
        this.add(spAnio, gbc);

        gbc.gridx = 2;
        this.add(btnAnioSiguiente, gbc);

        gbc.gridy = 1;

        gbc.gridx = 0;
        this.add(btnMesAnterior, gbc);

        gbc.gridx = 1;
        this.add(cboxMeses, gbc);

        gbc.gridx = 2;
        this.add(btnMesSiguiente, gbc);

        gbc.gridy = 3;

        gbc.gridx = 1;
        this.add(lblFecha, gbc);

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        this.add(btnHoy, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scroll, gbc);

        // Registramos los listeners.
        this.btnAnioAnterior.addActionListener(this);
        this.btnAnioSiguiente.addActionListener(this);
        this.btnMesAnterior.addActionListener(this);
        this.btnMesSiguiente.addActionListener(this);
        this.btnHoy.addActionListener(this);
        this.spAnio.addChangeListener(this);
        this.cboxMeses.addActionListener(this);
        this.tblCalendario.getSelectionModel().addListSelectionListener(this);
        this.tblCalendario.getColumnModel().getSelectionModel().addListSelectionListener(this);

        // Actualizamos el calendario.
        actualizarCalendario();

        // Seteamos el ancho del panel al ancho de la tabla
        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
    }

    /**
     * Registra un nuevo listener al panel.
     * 
     * @param listener
     *            Clase que implementa la interfaz
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.CalendarioListener
     *            CalendarioListener}.
     */
    public void addCalendarioListener(CalendarioListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Retorna un objeto {@link java.util.Date Date} con la fecha seleccionada. Si se llama a este
     * método con sin haber seleccionado alguna fecha, retornará la fecha actual (última fecha
     * seleccionada).
     */
    public Date getFecha()
    {
        return this.calendario.getTime();
    }

    /**
     * Retorna un objeto {@link java.util.Date Date} con la fecha del corriente día.
     */
    public Date getFechaHoy()
    {
        this.calendarioHoy = new GregorianCalendar();
        return this.calendarioHoy.getTime();
    }

    /**
     * Selecciona la fecha del corriente día en el calendario.
     */
    public void seleccionarFechaHoy()
    {
        this.calendario = new GregorianCalendar();
        this.spAnio.setValue(calendario.get(GregorianCalendar.YEAR));
        this.cboxMeses.setSelectedIndex(calendario.get(GregorianCalendar.MONTH));

        this.tblCalendario.changeSelection(calendario.get(GregorianCalendar.WEEK_OF_MONTH) - 1,
                calendario.get(GregorianCalendar.DAY_OF_WEEK) - 1, false, false);
    }

    /*
     * Actualiza el estado de los botones y el contenido de la tabla.
     */
    private void actualizarCalendario()
    {
        // Deshabilitamos los botones si corresponde.
        this.btnAnioAnterior.setEnabled(true);
        this.btnAnioSiguiente.setEnabled(true);
        if(this.spAnio.getModel().getPreviousValue() == null)
            this.btnAnioAnterior.setEnabled(false);
        if(this.spAnio.getModel().getNextValue() == null)
            this.btnAnioSiguiente.setEnabled(false);

        this.btnMesAnterior.setEnabled(true);
        this.btnMesSiguiente.setEnabled(true);
        if(this.cboxMeses.getSelectedIndex() == 0)
            this.btnMesAnterior.setEnabled(false);
        if(this.cboxMeses.getSelectedIndex() == 11)
            this.btnMesSiguiente.setEnabled(false);

        // Actualizamos el calendario
        int dia = this.calendario.get(GregorianCalendar.DAY_OF_MONTH);

        // Ponemos el día del calendario en 1 sólo para calcular el offset.
        this.calendario.set((int) this.spAnio.getValue(), this.cboxMeses.getSelectedIndex(), 1);
        int offset = calendario.get(GregorianCalendar.DAY_OF_WEEK);

        // Corregimos el calendario.
        this.calendario.set((int) this.spAnio.getValue(), this.cboxMeses.getSelectedIndex(), dia);

        // Limpiamos la tabla del calendario.
        for(int i = 0; i < 6; ++i)
            for(int j = 0; j < 7; ++j)
                modeloCalendario.setValueAt(null, i, j);

        // Actualizamos la tabla del calendario.
        int diaFinal = calendario.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        for(dia = 1; dia <= diaFinal; ++dia)
            modeloCalendario.setValueAt(dia, (int) ((dia + offset - 2) / 7),
                    ((dia + offset - 2) % 7));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnAnioAnterior)
        {
            // Si presionamos el botón de "año anterior" primero verificamos si es posible avanzar.
            // De ser posible, actualizamos el spinner de los años y el calendario.
            Object valor = this.spAnio.getPreviousValue();
            if(valor != null)
            {
                this.spAnio.setValue((int) valor);
                actualizarCalendario();
            }
        } else if(e.getSource() == this.btnAnioSiguiente)
        {
            // Si presionamos el botón de "año anterior" primero verificamos si es posible avanzar.
            // De ser posible, actualizamos el spinner de los años y el calendario.
            Object valor = this.spAnio.getNextValue();
            if(valor != null)
            {
                this.spAnio.setValue((int) valor);
                actualizarCalendario();
            }

        } else if(e.getSource() == this.btnMesAnterior)
        {
            // Si presionamos el botón de "mes anterior" primero verificamos si es posible avanzar.
            // De ser posible, actualizamos el combobox de los meses y el calendario.
            int index = this.cboxMeses.getSelectedIndex();
            if(index != 0)
            {
                this.cboxMeses.setSelectedIndex(index - 1);
                actualizarCalendario();
            }
        } else if(e.getSource() == this.btnMesSiguiente)
        {
            // Si presionamos el botón de "mes siguiente" primero verificamos si es posible avanzar.
            // De ser posible, actualizamos el combobox de los meses y el calendario.
            int index = this.cboxMeses.getSelectedIndex();
            if(index != 11)
            {
                this.cboxMeses.setSelectedIndex(index + 1);
                actualizarCalendario();
            }
        } else if(e.getSource() == this.btnHoy)
        {
            // Seleccionamos el día de hoy.
            this.seleccionarFechaHoy();
        } else if(e.getSource() == this.cboxMeses)
        {
            // Actualizamos el calendario cada vez que se selecciona un mes mediante el combobox.
            actualizarCalendario();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        // Actualizamos el calendario cada vez que cambia el spinner de los años.
        if(e.getSource() == this.spAnio)
            actualizarCalendario();
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        // Cada vez que se selecciona un día ...
        if(!e.getValueIsAdjusting())
        {
            int f = this.tblCalendario.getSelectedRow();
            int c = this.tblCalendario.getSelectedColumn();

            if(f != -1 && c != -1)
            {
                // Obtenemos el día seleccionado.
                Object o = this.tblCalendario.getValueAt(f, c);
                if(o != null)
                {
                    // Actualizamos el calendario.
                    this.calendario.set(calendario.get(GregorianCalendar.YEAR),
                            calendario.get(GregorianCalendar.MONTH), (int) o);
                    // Actualizamos el texto de la fecha actual.
                    this.lblFecha.setText(formateadorFecha.format(calendario.getTime()));

                    // Avisamos a todos los listeners.
                    for(CalendarioListener l : this.listeners)
                        l.fechaCalendarioSeleccionada(new EventObject(this));
                }
            }
        }
    }

}
