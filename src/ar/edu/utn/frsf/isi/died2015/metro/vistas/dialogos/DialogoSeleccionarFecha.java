package ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.SeleccionarFechaListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCalendario;

/**
 * Clase que crea un diálogo para la selección de una fecha.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class DialogoSeleccionarFecha extends JDialog implements ActionListener
{
    private static final long serialVersionUID = -7821004498483749274L;

    private ArrayList<SeleccionarFechaListener> listeners;

    private PanelCalendario calendario;
    private JPanel panelBotones;
    private JButton btnAceptar, btnCancelar;

    /**
     * Constructor del diálogo.
     * 
     * @param owner
     *            Diálogo padre de este diálogo.
     * @param modal
     *            Modalidad del diálogo. True para habilitarlo, False en caso contrario.
     */
    public DialogoSeleccionarFecha(Dialog owner, boolean modal)
    {
        super(owner, modal);

        this.setTitle("Seleccione una fecha");

        // Evitamos que se pueda modificar el tamaño del panel.
        this.setResizable(false);

        // Lo centramos respecto al diálogo padre si lo tiene.
        // if(owner != null)
        // this.setLocation((int) owner.getLocation().getX() +
        // (int)((owner.getWidth() - this.getWidth())/2) ,
        // (int) owner.getLocation().getY() +
        // (int)((owner.getHeight() - this.getHeight())/2) );

        // Delegamos la responsabilidad de la locación al sistema operativo.
        this.setLocationByPlatform(true);

        // Creamos la lista de listeners.
        listeners = new ArrayList<SeleccionarFechaListener>();

        // Obtenemos el contentpane.
        JPanel cp = (JPanel) this.getContentPane();

        // Creamos los componentes.
        this.calendario = new PanelCalendario();

        this.panelBotones = new JPanel();
        this.btnAceptar = new JButton("Aceptar");
        this.btnCancelar = new JButton("Cancelar");

        // Asociamos los botones a las acciones del diálogo.
        this.btnAceptar.setActionCommand("OK");
        this.btnCancelar.setActionCommand("Cancel");

        // Creamos el layout y distribuimos los componentes.
        BorderLayout bl = new BorderLayout();
        bl.setVgap(10);

        cp.setLayout(bl);

        cp.add(calendario, BorderLayout.CENTER);

        this.panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        this.panelBotones.add(btnAceptar);
        this.panelBotones.add(btnCancelar);

        cp.add(panelBotones, BorderLayout.SOUTH);

        // Registramos los listeners.
        this.btnAceptar.addActionListener(this);
        this.btnCancelar.addActionListener(this);
        
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        this.pack();

    }

    /**
     * Registra un nuevo listener al diálogo.
     * 
     * @param listener
     *            Clase que implementa la interfaz
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.SeleccionarFechaListener
     *            SeleccionarFechaListener}.
     */
    public void addSeleccionarFechaListener(SeleccionarFechaListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Retorna la fecha seleccionada. Si se ha aceptado el diálogo y no se seleccionó una fecha, se
     * retornará la última fecha seleccioada.
     */
    public Date getFecha()
    {
        return calendario.getFecha();
    }

    /**
     * Selecciona la fecha del corriente día en el calendario.
     */
    public void seleccionarFechaHoy()
    {
        this.calendario.seleccionarFechaHoy();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnAceptar)
        {
            this.setVisible(false);

            for(SeleccionarFechaListener l : listeners)
                l.nuevaFechaSeleccionada(new EventObject(this));

        } else if(e.getSource() == this.btnCancelar)
        {
            this.setVisible(false);
        }
    }

}
