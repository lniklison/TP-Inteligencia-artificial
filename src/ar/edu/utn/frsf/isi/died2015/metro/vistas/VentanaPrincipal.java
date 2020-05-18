package ar.edu.utn.frsf.isi.died2015.metro.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import ar.edu.utn.frsf.isi.died2015.metro.control.GestorMetro;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Camino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Reclamo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Vertice;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoFichaEstacion;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoInspeccion;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.DialogoReclamoEstacion;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos.PanelPassword;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.BuscarCaminoListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.CaminoListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.FichaEstacionListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.InspeccionListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.PanelGrafoListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.ReclamoEstacionListener;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelBuscarCamino;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelCaminos;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelGrafo;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;
import tp.inteligencia.artificial.AC19Environment;
import tp.inteligencia.artificial.AndroideC19;
import tp.inteligencia.artificial.AndroideC19Main;

public class VentanaPrincipal extends JFrame implements ActionListener, BuscarCaminoListener,
                CaminoListener, ReclamoEstacionListener, FichaEstacionListener, InspeccionListener, PanelGrafoListener
                
{
    private static final long serialVersionUID = -3849476473173475476L;

    private GestorMetro gestor;

    private JFileChooser fc;

    private JMenuItem mitemCargarCvs, mitemGuardar, mitemRestaurar, mitemSalir, mitemReclamos,
                    mitemInspecciones, mitemFicha, mitemEjecutarAgente;

    private PanelGrafo panelGrafo;
    private PanelBuscarCamino panelBuscar;
    private PanelCaminos panelCaminos;

    private PanelPassword panelLogin;
    private DialogoReclamoEstacion dialogoReclamos;
    private DialogoFichaEstacion dialogoFicha;
    private DialogoInspeccion dialogoInspecciones;

    Vector<Nodo> estaciones;

    public VentanaPrincipal()
    {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000, 500));
        this.setTitle("Metro 2015");

        // Obtenemos el contentpane
        JPanel cp = (JPanel) this.getContentPane();

        // Cremos la bara de menú
        JMenuBar barraMenu = new JMenuBar();
        this.setJMenuBar(barraMenu);

        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuEstacion = new JMenu("Estación");
        JMenu menuAdministrar = new JMenu("Administrar");
        barraMenu.add(menuArchivo);
        barraMenu.add(menuEstacion);
        barraMenu.add(menuAdministrar);

        this.mitemCargarCvs = new JMenuItem("Cargar CVS");
        this.mitemGuardar = new JMenuItem("Guardar distribución");
        this.mitemRestaurar = new JMenuItem("Restaurar distribución");
        this.mitemEjecutarAgente = new JMenuItem("Ejecutar agente");
        this.mitemSalir = new JMenuItem("Salir");
        menuArchivo.add(this.mitemCargarCvs);
        menuArchivo.addSeparator();
        menuArchivo.add(this.mitemGuardar);
        menuArchivo.add(this.mitemRestaurar);
        menuArchivo.addSeparator();
        menuArchivo.add(this.mitemEjecutarAgente);
        menuArchivo.addSeparator();
        menuArchivo.add(this.mitemSalir);

        this.mitemReclamos = new JMenuItem("Generar reclamo");
        this.mitemFicha = new JMenuItem("Ver ficha");
        menuEstacion.add(this.mitemFicha);
        menuEstacion.add(this.mitemReclamos);

        this.mitemInspecciones = new JMenuItem("Generar inspecciones");
        menuAdministrar.add(this.mitemInspecciones);

        // Desactivamos aquellos menúes que así lo requieran hasta que carguemos un metro
        this.mitemRestaurar.setEnabled(false);
        this.mitemGuardar.setEnabled(false);
        this.mitemInspecciones.setEnabled(false);
        this.mitemReclamos.setEnabled(false);
        this.mitemFicha.setEnabled(false);

        // Creamos los componentes.
        this.gestor = GestorMetro.getInstancia();

        this.fc = new JFileChooser();
        this.fc.setAcceptAllFileFilterUsed(false);
        this.fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Comma Separated Values", "csv"));

        this.panelGrafo = new PanelGrafo(this.gestor.getGrafo());
        this.panelBuscar = new PanelBuscarCamino();
        this.panelCaminos = new PanelCaminos();
        this.panelLogin = new PanelPassword();

        this.dialogoReclamos = new DialogoReclamoEstacion(this, true);
        this.dialogoFicha = new DialogoFichaEstacion(this, false);
        this.dialogoInspecciones = new DialogoInspeccion(this, true);

        this.estaciones = new Vector<Nodo>();

        // Creamos, aplicamos y agregamos todos los componentes al layout.
        BorderLayout bl = new BorderLayout();
        bl.setHgap(5);
        bl.setVgap(5);
        cp.setLayout(bl);

//        cp.add(this.panelBuscar, BorderLayout.NORTH);
        cp.add(this.panelGrafo, BorderLayout.CENTER);
//        cp.add(this.panelCaminos, BorderLayout.WEST);

        // Registramos los listeners.

        // De los menúes.
        this.mitemCargarCvs.addActionListener(this);
        this.mitemGuardar.addActionListener(this);
        this.mitemRestaurar.addActionListener(this);
        this.mitemFicha.addActionListener(this);
        this.mitemReclamos.addActionListener(this);
        this.mitemSalir.addActionListener(this);
        this.mitemInspecciones.addActionListener(this);
        this.mitemEjecutarAgente.addActionListener(this);

        // De los paneles
        this.panelBuscar.addBuscarCaminoListener(this);
        this.panelCaminos.addCaminoLisetner(this);
        this.panelGrafo.addPanelGrafoListeners(this);

        // De los diálogos
        this.dialogoReclamos.addReclamoEstacionListener(this);
        this.dialogoFicha.addFichaEstacionListener(this);
        this.dialogoInspecciones.addInspeccionListener(this);

        // Workarround: Esta ventana es la que se ocupa de repintar el
        // panelGrafo ya que si lo hace
        // este mismo se producen errores en le repintado. Repinta a 90FPS
        Timer t = new Timer(1000 / 90, new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                repaint();
            }
        });
        t.start();

        this.pack();

        this.setVisible(true);
    }

    /*
     * Carga un nuevo archivo csv
     */
    private void cargarNuevoMetro(File archivo)
    {
        // Cargamos el archivo CSV.
        this.gestor.cargarArchivo(archivo);

        // Asignamos el nuevo grafo.
        this.panelGrafo.setGrafo(this.gestor.getGrafo());

        // Obtenemos las estaciones para cargar los demás paneles y diálogos.
        this.estaciones = new Vector<Nodo>();
        for (Nodo e : this.gestor.getNodos())
            estaciones.add(e);
        Collections.sort(estaciones);
        this.panelBuscar.setModeloOrigen(estaciones);
        this.panelBuscar.setModeloDestino(estaciones);
        this.dialogoReclamos.setModeloEstaciones(this.estaciones);
        this.dialogoFicha.setModeloEstaciones(estaciones);

        // Activamos las opciones de menú
        this.mitemRestaurar.setEnabled(true);
        this.mitemGuardar.setEnabled(true);
        this.mitemInspecciones.setEnabled(true);
        this.mitemReclamos.setEnabled(true);
        this.mitemFicha.setEnabled(true);

    }

    /**********************************************************************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.mitemCargarCvs)
        {
            int retorno = this.fc.showOpenDialog(this);
            if (retorno == JFileChooser.APPROVE_OPTION)
            {
                // Obtenemos el archivo.
                File archivo = this.fc.getSelectedFile();

                // TODO: en nuevo thread?
                cargarNuevoMetro(archivo);

            }
        } else if (e.getSource() == this.mitemGuardar) // Guardar la disposición.
        {
            this.gestor.guardarDistribucion();

        } else if (e.getSource() == this.mitemRestaurar) // Restaurar la disposición.
        {
            this.gestor.restaurarDistribucion();

        } else if (e.getSource() == this.mitemSalir) // Salir de la aplicación.
        {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        } 

        else if (e.getSource() == this.mitemFicha) // Al querer ver la ficha de una estación.
        {
            if (!this.dialogoFicha.isVisible())
            {
                this.dialogoFicha.seleccionarEstacionPorIndice(0);
                this.dialogoFicha.setVisible(true);
            }
        } else if (e.getSource() == this.mitemReclamos) // Al querer ingresar un nuevo reclamo
        {
            if (!this.dialogoReclamos.isVisible())
            {
                this.dialogoReclamos.seleccionarEstacionPorIndice(0);
                this.dialogoReclamos.setVisible(true);
            }
        }else if(e.getSource() == this.mitemEjecutarAgente){
            AC19Environment environment = new AC19Environment(this.gestor.getNodos(),this.panelGrafo);

            AndroideC19 agent = new AndroideC19(environment.getEnvironmentState().getPositions(),
                environment.getEnvironmentState().getMap(),environment.getEnvironmentState().getPosicionesEnfermos());

            SearchBasedAgentSimulator simulator =
                new SearchBasedAgentSimulator(environment, agent);
        
            simulator.start();

//                (new AndroideC19Main(this.panelGrafo)).run(); 
            
        } else if (e.getSource() == this.mitemInspecciones) // Al querer generar las inspecciones.
        {
            String[] options = new String[] { "Aceptar", "Cancelar" };
            int option = JOptionPane.showOptionDialog(this, this.panelLogin,
                            "Ingrese su password.", JOptionPane.NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

            if (option == JOptionPane.OK_OPTION)
            {
                String password = this.panelLogin.getPassword();

                // Validamos la password
                if (this.gestor.validarPassword(password))
                {
                    if (!this.dialogoInspecciones.isVisible())
                    {
                        // Obtenemos las estaciones que se inspeccionarían.
//                        Vector<Nodo> estAinspec = this.gestor.getEstacionesAInspeccionar();
//
//                        for (Nodo est : estAinspec)
//                            System.out.println(est);
//
//                        if (estAinspec.size() == 0) // No hay reclamos que procesas
//                        {
//                            JOptionPane.showMessageDialog(this, "No hay inspecciones por generar.",
//                                            "Información", JOptionPane.INFORMATION_MESSAGE);
//                        } else
//                        {
//                            this.dialogoInspecciones.setEstaciones(estAinspec);
//                            this.dialogoInspecciones.setVisible(true);
//                        }
                    }
                } else
                {
                    JOptionPane.showMessageDialog(this, "La contraseña es incorrecta.\n"
                                    + " Por favor inténtelo nuevamente.", "Contraseña incorrecta",
                                    JOptionPane.ERROR_MESSAGE);
                }
            }

            this.panelLogin.limpiar();
        }
    }

    /**********************************************************************************************/
    // Al presionar el botón BUSCAR del panelBuscar
    @Override
    public void buscarCaminoEntreEstaciones(EventObject e)
    {
        Nodo origen = this.panelBuscar.getEstacionOrigen();
        Nodo destino = this.panelBuscar.getEstacionDestino();

        if (origen != null && destino != null)
        {
            if (origen == destino) // Estaciones iguales.
            {
                JOptionPane.showMessageDialog(
                                this,
                                "Las estaciones son iguales, no hay camino por buscar.\n"
                                                + " Por favor vuelva a seleccionar otro par de estaciones.",
                                "Estaciones Iguales", JOptionPane.INFORMATION_MESSAGE);
            } else
            {

                // TODO: en otro thead?
                PriorityQueue<Camino> caminos = this.gestor.getCaminosEntre(origen, destino);
                if (caminos.isEmpty())
                {
                    JOptionPane.showMessageDialog(
                                    this,
                                    "<html>No se encontraron caminos entre las estaciones <b>"
                                                    + origen
                                                    + "</b> y <b>"
                                                    + destino
                                                    + "</b>.<br>Por favor vuelva a seleccionar otro par de estaciones. </html>",
                                    "Camino no encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else
                {
                    this.panelCaminos.setEstacionesCamino(origen, destino);
                    this.panelCaminos.setModeloListaCaminos(caminos);
                }
            }
        }
    }

    /**********************************************************************************************/
    // al seleccionarse un nuevo camino encontrado
    @Override
    public void caminoSeleccionado(EventObject e)
    {
        Camino c = this.panelCaminos.getCaminoSeleccionado();
        if (c != null) // por las dudas.
        {
            ArrayList<Arco> arcos = this.gestor.getArcosCamino(c);
            if (!arcos.isEmpty())
            {
                this.panelGrafo.marcarArcos(arcos);
            }
        }
    }

    /**********************************************************************************************/

    // Al aceptar el diálogo de un nuevo reclamo
    @Override
    public void reclamoEstacionAceptado(EventObject e)
    {
        Nodo estacion = this.dialogoReclamos.getEstacionSeleccionada();
        Reclamo r = new Reclamo();
        r.setFecha(this.dialogoReclamos.getFecha());
        r.setNombre(this.dialogoReclamos.getNombre());
        r.setApellido(this.dialogoReclamos.getApellido());
        r.setTelefono(this.dialogoReclamos.getTelefono());
        r.setNumeroDocumento(this.dialogoReclamos.getNumeroDocumento());
        r.setMotivo(this.dialogoReclamos.getMotivo());
        r.setEstacion(estacion);
//        this.gestor.addReclamo(r, estacion);

        this.dialogoReclamos.setVisible(false);
        this.dialogoReclamos.limpiar();

        JOptionPane.showMessageDialog(this, "El reclamo fue registrado con éxtio.",
                        "Gracias por su atención.", JOptionPane.PLAIN_MESSAGE);

    }

    // Al cancelar el diálogo de un nuevo reclamo
    @Override
    public void reclamoEstacionCancelado(EventObject e)
    {
        this.dialogoReclamos.setVisible(false);
        this.dialogoReclamos.limpiar();
    }

    /**********************************************************************************************/
    // Al aceptar el diálogo de las fichas de las estaciones.
    @Override
    public void fichaEstacionAceptada(EventObject e)
    {
        this.dialogoFicha.setVisible(false);
    }

    /**********************************************************************************************/
    // Al presionar el botón de generar inspecciones.
    @Override
    public void inspeccionAceptada(EventObject e)
    {
        Vector<Nodo> estacionesAInspeccionar = this.dialogoInspecciones.getEstaciones();

//        this.gestor.generarInspecciones(estacionesAInspeccionar);

        JOptionPane.showMessageDialog(this, "Las inspecciones fueron generadas con éxito.",
                        "Inspecciones generadas.", JOptionPane.INFORMATION_MESSAGE);

        this.dialogoInspecciones.setVisible(false);
        this.dialogoInspecciones.limpiar();
    }

    @Override
    public void inspeccionCancelada(EventObject e)
    {
        this.dialogoInspecciones.setVisible(false);
        this.dialogoInspecciones.limpiar();
    }

    @Override
    public void verticeOrigenSeleccionado(EventObject o)
    {
        Vertice vertice = this.panelGrafo.getVerticeSeleccionado();
        Nodo origen = this.gestor.getEstacionByVertice(vertice);
        this.panelBuscar.setEstacionOrigen(origen);      
    }

    @Override
    public void verticeDestinoSeleccionado(EventObject o)
    {
        Vertice vertice = this.panelGrafo.getVerticeSeleccionado();
        Nodo destino = this.gestor.getEstacionByVertice(vertice);
        this.panelBuscar.setEstacionDestino(destino);
    }

    @Override
    public void verFichaEstacion(EventObject o)
    {
        Vertice vertice = this.panelGrafo.getVerticeSeleccionado();
        Nodo estacion = this.gestor.getEstacionByVertice(vertice);
        if (!this.dialogoFicha.isVisible())
        {
            this.dialogoFicha.seleccionarEstacion(estacion);
            this.dialogoFicha.setVisible(true);
        }
    }

    /**********************************************************************************************/
    

}
