package ar.edu.utn.frsf.isi.died2015.metro.control;

import java.awt.Color;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import ar.edu.utn.frsf.isi.died2015.metro.control.algoritmos.FRLayout;
import ar.edu.utn.frsf.isi.died2015.metro.control.algoritmos.TodosLosCaminos;
import ar.edu.utn.frsf.isi.died2015.metro.control.util.ParseadorCSV;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cajero;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Camino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Grafo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Inspeccion;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Calle;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Reclamo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cuadra;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Vertice;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoException;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoVerticeInexistenteException;

public class GestorMetro
{
    private int idCajero = 0;
    private int idVertice = 0;

    private Grafo grafo;
    private ArrayList<Vertice> distribucionGuardada;

    private FRLayout layout;
    private LinkedHashMap<String, Nodo> nodos; // id Estación =>
                                                        // Estación

    private LinkedHashMap<Nodo, Vertice> nodosAVertice; // Estacion =>
                                                               // Vertice
    private LinkedHashMap<Vertice, Nodo> verticeANodos; // Vertice =>
                                                               // Estacion

    private LinkedHashMap<Cuadra, Arco> calleAArco; // Tramo => Arco
    private LinkedHashMap<Arco, Cuadra> arcoACalle; // Arco => Tramo

    private ArrayList<Color> colores;
    private HashMap<Calle, Color> coloresLineas;
    private int idColor;

    private HashMap<Cuadra, Integer> tiempos;

    private SimpleDateFormat formatoFecha;

    private static GestorMetro instance = null;
    
    protected GestorMetro()
    {
        this.grafo = new Grafo();
        this.layout = null;

        this.distribucionGuardada = new ArrayList<Vertice>();

        this.nodos = new LinkedHashMap<String, Nodo>();
        this.verticeANodos = new LinkedHashMap<Vertice, Nodo>();
        this.nodosAVertice = new LinkedHashMap<Nodo, Vertice>();
        this.arcoACalle = new LinkedHashMap<Arco, Cuadra>();
        this.calleAArco = new LinkedHashMap<Cuadra, Arco>();

        this.tiempos = new HashMap<Cuadra, Integer>();

        restaurarColores();

        this.formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    }
    
    public static GestorMetro getInstancia() {
        if(instance == null) {
           instance = new GestorMetro();
        }
        return instance;
     }

    /**
     * Retorna el {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Grafo grafo} que modela el
     * sistema de metro actual.
     */
    public Grafo getGrafo()
    {
        return this.grafo;
    }

    /**
     * Guarda la distribución de las estaciones en la pantalla.
     */
    public void guardarDistribucion()
    {
        // Guardamos sólamente la posiciones de los vértices del grafo.
        this.distribucionGuardada = new ArrayList<Vertice>(grafo.getNumeroVertices());
        for (Vertice v : grafo.getVertices())
        {
            Vertice n = new Vertice();
            n.setPos(v.getPosX(), v.getPosY());
            n.setDesp(v.getDespX(), v.getDespY());
            this.distribucionGuardada.add(n);
        }
    }

    /**
     * Restaura la última distribución de las estaciones guardada.
     */
    public void restaurarDistribucion()
    {
        if (!this.distribucionGuardada.isEmpty())
        {
            int i = 0;
            Vertice v;
            for (Vertice n : this.grafo.getVertices())
            {
                v = this.distribucionGuardada.get(i++);
                n.setPos(v.getPosX(), v.getPosY());
                n.setDesp(v.getDespX(), v.getDespY());
            }
        }
    }

    /**
     * Retorna un objeto {@link java.lang.Iterable<> Iterable} de
     * {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estación} con las estaciones de
     * trenes del sistema de metro.
     */
    public Iterable<Nodo> getEstaciones()
    {
        return this.nodos.values();
    }

    /*
     * Restaura los colores por defecto.
     */
    private void restaurarColores()
    {
        this.idColor = 0;
        this.coloresLineas = new HashMap<Calle, Color>();
        this.colores = new ArrayList<Color>();
        colores.add(new Color(51, 167, 216));
        colores.add(new Color(0, 168, 143));
        colores.add(new Color(142, 252, 7));
        colores.add(new Color(254, 242, 0));
        colores.add(new Color(255, 194, 15));
        colores.add(new Color(247, 143, 30));
        colores.add(new Color(243, 112, 32));
        colores.add(new Color(238, 27, 36));
        colores.add(new Color(228, 64, 151));
        colores.add(new Color(166, 68, 153));
        colores.add(new Color(136, 105, 173));
        colores.add(new Color(50, 118, 181));
    }

    /*
     * Retorna un color para cada la línea de tren pasada como argumento. Si esta no está registrada
     * retorna un nuevo color, de lo contrario el color registrado.
     */
    private Color getColorLineaTren(Calle linea)
    {
        if (!this.coloresLineas.containsKey(linea))
        {
            if (this.idColor < this.colores.size())
            {
                this.coloresLineas.put(linea, this.colores.get(idColor));
            } else
            {
                int nuevoId = this.idColor % this.colores.size();
                Color nuevoColor = this.colores.get(nuevoId).darker();
                this.colores.set(nuevoId, nuevoColor);
                this.coloresLineas.put(linea, nuevoColor);
            }
            ++(this.idColor);
        }
        return this.coloresLineas.get(linea);
    }

    public FRLayout getLayout()
    {
        return this.layout;
    }

    public boolean cargarArchivo(File archivo)
    {
        ParseadorCSV csv = new ParseadorCSV(archivo);
        ArrayList<String[]> datos = csv.getDatos();
        Grafo g = new Grafo();
        // limpiar this.estaciones
        int contador = 0;

        if (datos != null)
        {
            for (String[] linea : datos)
            {
                try
                {
                    if (linea[0].equals("N")) // Estación
                    {
                        // Creamos la estación
                        Nodo nodo = new Nodo();
                        nodo.setId(linea[1]);
                        nodo.setNombre(linea[2]);
//                        e.setTiempoTrasbordo(Integer.valueOf(linea[3]));
                        nodo.setAccesible(true);
                        
                        System.out.println("Nodo creadu: "+nodo.toString());

//                        int cantCajeros = Integer.valueOf(linea[5]);
//                        int i = 6;

//                        if (cantCajeros != 0)
//                        {
//                            for (int j = 0; j < cantCajeros; ++j)
//                                e.addCajero(new Cajero(idCajero++, linea[i++]));
//                        }
//                        if (i < linea.length) e.setTelefono(linea[i]);

                        // Agregamos la estación.
                        this.nodos.put(nodo.getId(), nodo);

                        // Creamos el vértice del grafo que modela a la estación.
                        Vertice v = new Vertice(idVertice, true, nodo.getNombre());
                        idVertice += 1;
                        // Mapeamos la estación con el vértice
                        this.verticeANodos.put(v, nodo);
                        this.nodosAVertice.put(nodo, v);

                        // Agregamos el vértice al grafo.
                        g.addVertice(v);

                    } else if (linea[0].equals("C")) // Tramo
                    {
                        contador++;
                        if (!this.nodos.containsKey(linea[2])) return false;
                        if (!this.nodos.containsKey(linea[3])) return false;

                        // Creamos el tramo
                        Cuadra cuadra = new Cuadra();

                        Integer tiempo = Integer.valueOf(linea[1]);

                        Nodo o = this.nodos.get(linea[2]);
                        Nodo d = this.nodos.get(linea[3]);

                        cuadra.setOrigen(o);
                        cuadra.setDestino(d);

                        cuadra.setHabilitado(Integer.valueOf(linea[5]) == 1);
                        System.out.println("Cuadra creada: "+cuadra.toString());
                        Calle calle = new Calle(linea[4]);
//                        t.setLineaTren(lt);
                        
                        System.out.println("Calle creada: "+calle.toString());
                        this.nodos.get(linea[2]).addCuadra(cuadra);

                        // Creamos un tramo inverso sólo para buscarlo que la tabla de tiempos y así
                        // evitar agregarlo.
                        Cuadra ci = new Cuadra();
                        ci.setDestino(o);
                        ci.setOrigen(d);

                        boolean b = this.tiempos.containsKey(cuadra);
                        boolean bi = this.tiempos.containsKey(ci);

                        // Si no registramos ninguno de los dos tramos, agregamos el tiempo.
                        if (!b && !bi) this.tiempos.put(cuadra, tiempo);

                        Arco a;
                        // Creamos el arco del grafo que modela al tramo.
                        String nombreCalle = "";
                        if(contador%4==0) nombreCalle = calle.toString();
                        try
                        {
                            a = new Arco(this.nodosAVertice.get(o),
                                            this.nodosAVertice.get(d), cuadra.isHabilitado(), nombreCalle);
                            a.setColor(getColorLineaTren(calle));
                            g.addArco(a);
                        } catch (GrafoVerticeInexistenteException | GrafoException e)
                        {
                            e.printStackTrace();
                            return false;
                        }

                        // Mapeamos el arco con el tramo
                        this.arcoACalle.put(a, cuadra);
                        this.calleAArco.put(cuadra, a);

                    }
                    else if (linea[0].equals("R")) // Reclamo
                    {
                        if (!this.nodos.containsKey(linea[2])) return false;

                        Reclamo r = new Reclamo();
                        r.setId(linea[1]);
                        r.setEstacion(this.nodos.get(linea[2]));
                        String[] nombreApellido = linea[3].split(" ");
                        r.setNombre(nombreApellido[0]);
                        r.setApellido(nombreApellido[1]);
                        r.setTelefono(linea[4]);
                        r.setNumeroDocumento(linea[5]);
                        r.setMotivo(linea[6]);
                        r.setFecha(this.formatoFecha.parse(linea[7]));

//                        this.nodos.get(linea[2]).addReclamo(r);
                    } 
                    else
                        return false;
                } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e)
                {
                    e.printStackTrace();
                    return false;
                }
            }

            this.grafo = g;
            this.layout = new FRLayout(this.grafo, 900, 600, 20);
            this.layout.setMarco(10);
            this.layout.ejecutar(1000, true);
            this.restaurarColores();
            this.guardarDistribucion();
            return true;
        }

        return false;
    }

    private Integer getTiempoTramo(Cuadra tramo)
    {
        Integer tiempo = this.tiempos.get(tramo);
        if (tiempo != null) return tiempo;
        Cuadra inverso = new Cuadra();
        inverso.setOrigen(tramo.getDestino());
        inverso.setDestino(tramo.getOrigen());
        return this.tiempos.get(inverso);
    }

    public PriorityQueue<Camino> getCaminosEntre(Nodo origen, Nodo destino)
    {
        Vertice inicio = this.nodosAVertice.get(origen);
        Vertice fin = this.nodosAVertice.get(destino);
        PriorityQueue<Camino> caminos = new PriorityQueue<Camino>(10, new Comparator<Camino>()
        {
            @Override
            public int compare(Camino o1, Camino o2)
            {
                return o1.getTiempo() - o2.getTiempo();
            }
        });
        try
        {
            TodosLosCaminos algoritmo = new TodosLosCaminos(this.grafo, inicio, fin);

            int tiempo = 0;

            for (LinkedList<Arco> arcos : algoritmo.getCaminos())
            {

                Camino camino = new Camino();
                camino.setOrigen(origen);
                camino.setDestino(destino);

                Calle lineaAnterior = null;
                tiempo = 0;

                for (Arco arco : arcos)
                {
                    Cuadra tramo = this.arcoACalle.get(arco);

                    camino.addTramo(tramo);

                    // Si la línea de tren que pasa por el tramo actual es distina a la del tramo
                    // anterior quiere decir que se hizo un transbordo, por lo tanto le sumamos el
                    // tiempo de trasbordo de la estación origen del tramo actual (estacion final
                    // del tramo anterior) al tiempo total del camino.
//                    if (lineaAnterior != null)
//                        if (!tramo.getLineaTren().equals(lineaAnterior))
//                            tiempo += tramo.getOrigen().getTiempoTrasbordo();
//
//                    lineaAnterior = tramo.getLineaTren();

                    // Le asignamos el tiempo al tramo sólo para poder rellenar el panel de caminos.
                    tramo.setTiempo(getTiempoTramo(tramo));

                    tiempo += tramo.getTiempo();
                }

                camino.setTiempo(tiempo);
                caminos.add(camino);
            }

        } catch (GrafoVerticeInexistenteException e)
        {
            e.printStackTrace();
            return null;
        }
        return caminos;
    }

    /**
     * Retorna los arcos correspondientes a un camino.
     * 
     * @param camino
     */
    public ArrayList<Arco> getArcosCamino(Camino camino)
    {
        ArrayList<Arco> arcos = new ArrayList<Arco>();
        for (Cuadra tramo : camino.getTramos())
        {
            Arco a = this.calleAArco.get(tramo);
            arcos.add(a);
        }
        return arcos;
    }

    /**
     * Agrega un nuevo reclamo a una estacion.
     * 
     * @param r
     * @param estacion
     */
//    public void addReclamo(Reclamo r, Nodo estacion)
//    {
//        estacion.addReclamo(r);
//    }

    /**
     * Valida que la password del administrador sea correcta.
     * 
     * @param password
     * @return
     */
    public boolean validarPassword(String password)
    {
        return password.equals("/*admin*/");
    }

    /**
     * Retorna un {@link java.util.Vector Vector} las
     * {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo estaciones} que deben
     * inspeccionarse.
     */
//    public Vector<Nodo> getEstacionesAInspeccionar()
//    {
//        Vector<Nodo> estaciones = new Vector<Nodo>();
//
//        // Almacenamos las estaciones en una estructura ordenable, primero con cantidad de reclamos
//        // y luego por fecha (más antígua primero).
//        PriorityQueue<Nodo> pq = new PriorityQueue<Nodo>(new Comparator<Nodo>()
//        {
//            @Override
//            public int compare(Nodo e1, Nodo e2)
//            {
////                int diff = e2.getReclamos().size() - e1.getReclamos().size();
////                if (diff == 0) // Igual cantidad de reclamos
////                {
////                    Reclamo r1 = e1.getReclamos().peek();
////                    Reclamo r2 = e2.getReclamos().peek();
////
////                    if (r1 == null) return 0; // Estaciones sin reclamos.
////                    return r1.getFecha().compareTo(r2.getFecha());
////                }
////                return diff;
//            }
//        });
//
//        for (Nodo e : this.nodos.values())
//            if(e.getReclamos().size() != 0)
//                pq.add(e);
//
//        for (int i = 0; i < 3 && !pq.isEmpty(); ++i)
//        {
//            Nodo e = pq.poll();
//            estaciones.add(e);
//        }
//
//        return estaciones;
//    }

    /**
     * Genera las inspecciones a cada una de las estaciones pasadas como argumento. A cada una de
     * las estaciones se le asignará una inspección con un días consecutivos a partir del día
     * corriente, es decir, a la primera estación se le asignará una inspección con el día hoy, a la
     * segunda (de existir) con el día de mañana y así sucesivamente. Este método no verifica que
     * las estaciones pasadas como argumento sean las correctas por lo que se recomienda obtenerlas
     * primero con el método
     * {@link ar.edu.utn.frsf.isi.died2015.metro.control.GestorMetro#getEstacionesAInspeccionar()
     * GestorMetro#getEstacionesAInspeccionar()}
     * 
     * @param estaciones
     *            {@link java.util.Vector Vector} de
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo Estacion} con las
     *            estaciones a inspeccionar.
     * @see {@link ar.edu.utn.frsf.isi.died2015.metro.control.GestorMetro#getEstacionesAInspeccionar()}
     */
//    public void generarInspecciones(Vector<Nodo> estaciones)
//    {
//        Calendar c  = Calendar.getInstance(); // calendario con el día de hoy.
//        
//        for(Nodo e : estaciones)
//        {
//            Inspeccion i = new Inspeccion();
//            i.setFecha(c.getTime());
//            e.addInspeccion(i);
//            e.delReclamos();
//            c.add(Calendar.DATE, 1);
//        }
//    }

    
    /**
     * Retorna todos los tramos del grafo que tienen origen en la estacion pasada como argumento.
     * @param origen
     * @return
     */
    public Vector<Cuadra> getTramosConOrigen(Nodo origen)
    {
        Vector<Cuadra> tramos = new Vector<Cuadra>();
        
        // Obtenemos el vértice correspondiente al origen
        Vertice inicio = this.nodosAVertice.get(origen);
        
        // Buscamos todos los arcos adyacentes al origen
        try
        {
            for(Arco a : this.grafo.getAdyacentes(inicio))
            {
                Cuadra t = this.arcoACalle.get(a);
                tramos.add(t);
            }
        } catch (GrafoVerticeInexistenteException e)
        {
            e.printStackTrace();
        }
        
        return tramos;
    }

    public void actualizarEstadoTramo(Cuadra modificado)
    {
        Arco a = this.calleAArco.get(modificado);

        this.grafo.habilitarArco(a, modificado.isHabilitado());
        //this.grafo.getArco(a.getInicio(), a.getFin()).setHabilitado(modificado.isHabilitado());
            
        
        
    }

    public Arco getArcoByTramo(Cuadra modificado)
    {
        return this.calleAArco.get(modificado);
    }

    public Nodo getEstacionByVertice(Vertice origen)
    {
        return this.verticeANodos.get(origen);
    }

}
