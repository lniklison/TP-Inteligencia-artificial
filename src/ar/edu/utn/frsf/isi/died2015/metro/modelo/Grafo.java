package ar.edu.utn.frsf.isi.died2015.metro.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoArcoInexistenteException;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoException;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoVerticeInexistenteException;

/**
 * Clase que modela un grafo. En este no permiten lazos y para los arcos x => y e y => x no es
 * posible asignarle distintos pesos, persiste el peso del último arco añadido.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class Grafo
{

    private ArrayList<Vertice> vertices;
    private HashMap<Vertice, LinkedList<Arco>> adj;
    private LinkedHashSet<Arco> arcos;

    public Grafo()
    {
        this.vertices = new ArrayList<Vertice>();
        this.adj = new HashMap<Vertice, LinkedList<Arco>>();
        this.arcos = new LinkedHashSet<Arco>();
    }

    /**
     * Retorna el número de vértices del grafo.
     */
    public int numeroVertices()
    {
        return this.vertices.size();
    }

    /**
     * Retorna <code>true</code> si el vértice existe en el grafo, de lo contrario retorna
     * <code>false</code>.
     * 
     * @param unVertice
     *            {@link algoritmo.Vertice Vértice} buscado.
     */
    public boolean existeVertice(Vertice unVertice)
    {
        if(unVertice == null)
            throw new NullPointerException("El vértice no puede ser nulo.");
        return this.adj.containsKey(unVertice);
    }

    /**
     * Retorna <code>true</code> si el arco <code>inicio</code>=><code>fin</code> existe en el
     * grafo, de lo contrario retorna <code>false</code>.
     * 
     * @param inicio
     *            {@link algoritmo.Vertice Vértice} inicio del arco.
     * @param fin
     *            {@link algoritmo.Vertice Vértice} final del arco.
     * @throws GrafoVerticeInexistenteException
     *             Si alguno de los vértices <code>inicio</code> o <code>fin</code> del arco no
     *             pertenecen al grafo.
     */
    public boolean existeArco(Vertice inicio, Vertice fin) throws GrafoVerticeInexistenteException
    {
        if(inicio == null || fin == null)
            throw new NullPointerException("Ninguno de los vértices puede ser nulo.");
        if(existeVertice(inicio))
        {
            if(existeVertice(fin))
                return this.adj.get(inicio).contains(fin);
            else
                throw new GrafoVerticeInexistenteException("El vértice :" + fin
                        + " no pertecene al grafo.");
        } else
            throw new GrafoVerticeInexistenteException("El vértice :" + inicio
                    + " no pertecene al grafo.");
    }

    /**
     * Agrega un nuevo vértice al grafo. Si este ya pertenece al mismo se ignora la llamada a este
     * método.
     * 
     * @param nuevoVertice
     *            {@link algoritmo.Vertice Vértice} a agergar.
     */
    public void addVertice(Vertice nuevoVertice)
    {
        // Verificamos si el nuevoVertice ya se encuentra en el grafo.
        if(!existeVertice(nuevoVertice))
        {
            // Agregamos el vértice
            this.vertices.add(nuevoVertice);

            // Creamos la entrada para sus vertices adyacentes.
            this.adj.put(nuevoVertice, new LinkedList<Arco>());
        }
    }

    /**
     * Agrega un nuevo arco al grafo. Si este ya existe se ignora la llamada a este método.
     * 
     * @param arco
     *            {@link ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco Arco} a agregar.
     * @throws GrafoVerticeInexistenteException
     *             Si alguno de los vértices del arco no pertecen al grafo.
     * @throws GrafoException
     *             Si se quiere añadir un lazo al grafo (<code>inicio</code> == <code>fin</fin>).
     */
    public void addArco(Arco arco) throws GrafoVerticeInexistenteException, GrafoException
    {
        if(arco == null)
            throw new IllegalArgumentException("El arco no puede ser nulo.");
        if(arco.getInicio() == arco.getFin())
            throw new GrafoException("No se permiten añadir lazos.");
        if(!existeArco(arco.getInicio(), arco.getFin()))
        {
            this.adj.get(arco.getInicio()).add(arco);

            // Creamos el arco inverso para verficar si podemos agregar al original como un arco
            // no dirigido.
            Arco inverso = new Arco(arco.getFin(), arco.getInicio());
            boolean n = this.arcos.contains(arco);
            boolean i = this.arcos.contains(inverso);
            if(!n && !i)
                this.arcos.add(arco);
        }

    }

    /**
     * Agerga un nuevo arco al grafo. Si este ya existe se ignora la llamada a este método.
     * 
     * @param inicio
     *            {@link algoritmo.Vertice Vértice} inicio del arco.
     * @param fin
     *            {@link algoritmo.Vertice Vértice} final del arco.
     * @param habilitado
     *            Si queremos que el arco se agrege habilitado o no.
     * @param dato
     *            Objeto con información auxiliar del arco.
     * @throws GrafoVerticeInexistenteException
     *             Si alguno de los vértices <code>inicio</code> o <code>fin</code> del arco no
     *             pertecen al grafo.
     * @throws GrafoException
     *             Si se quiere añadir un lazo al grafo (<code>inicio</code> == <code>fin</fin>).
     */
    public void addArco(Vertice inicio, Vertice fin, boolean habilitado, Object dato)
            throws GrafoVerticeInexistenteException, GrafoException
    {

        if(inicio != null && inicio == fin)
            throw new GrafoException("No se permiten añadir lazos.");
        if(!existeArco(inicio, fin))
        {
            // Creamos y agregamos el arco.
            Arco nuevoArco = new Arco(inicio, fin, habilitado, dato);
            this.adj.get(inicio).add(nuevoArco);

            // Creamos el arco inverso para verficar si podemos agregar al original como un arco no
            // dirigido.
            Arco inverso = new Arco(fin, inicio);
            boolean n = this.arcos.contains(nuevoArco);
            boolean i = this.arcos.contains(inverso);
            if(!n && !i)
                this.arcos.add(nuevoArco);
        }
    }

    /**
     * Retorna el {@link algoritmo.Arco Arco} que tiene como inicio al vértice <code>inicio</code> y
     * como vértice final a <code>fin</code>.
     * 
     * @param inicio
     *            {@link algoritmo.Vertice Vértice} inicio del arco.
     * @param fin
     *            {@link algoritmo.Vertice Vértice} final del arco.
     * @throws GrafoArcoInexistenteException
     *             Si el arco <code>inicio</code> => <code>fin</code> no pertenece al grafo.
     * @throws GrafoVerticeInexistenteException
     *             Si alguno de los vértices <code>inicio</code> o <code>fin</code> del arco no
     *             pertecen al grafo.
     */
    public Arco getArco(Vertice inicio, Vertice fin) throws GrafoArcoInexistenteException,
            GrafoVerticeInexistenteException
    {
        if(existeVertice(inicio))
        {
            if(existeVertice(fin))
            {
                for(Arco a : getAdyacentes(inicio))
                    if(a.getFin() == fin)
                        return a;

                throw new GrafoArcoInexistenteException("El arco: " + inicio + " => " + fin
                        + " no pertenece al grafo.");
            } else
                throw new GrafoVerticeInexistenteException("El vértice :" + fin
                        + " no pertecene al grafo.");
        } else
            throw new GrafoVerticeInexistenteException("El vértice :" + inicio
                    + " no pertecene al grafo.");

    }

    /**
     * Retorna un {@link java.lang.Iterable<> Iterable} de {@link algoritmo.Arco Arco} con los
     * vértices adyacentes (arcos salientes) del {@link algoritmo.Vertice Vértice} pasado por
     * argumento.
     * 
     * @param unVertice
     *            {@link algoritmo.Vertice Vértice} del cual queremos obtener sus nodos adyacentes.
     * @throws GrafoVerticeInexistenteException
     *             Si el vértice <code>unVertice</code> no pertecene al grafo.
     */
    public Iterable<Arco> getAdyacentes(Vertice unVertice) throws GrafoVerticeInexistenteException
    {
        if(unVertice == null)
            throw new NullPointerException("El vértice no puede ser nulo.");
        if(!existeVertice(unVertice))
            throw new GrafoVerticeInexistenteException("El vértice :" + unVertice
                    + " no pertecene al grafo.");

        return this.adj.get(unVertice);
    }

    /**
     * Retorna un {@link java.lang.Iterable<> Iterable} de {@link algoritmo.Vertice Vértice} con los
     * vértices del grafo.
     */
    public Iterable<Vertice> getVertices()
    {
        return this.vertices;
    }

    /**
     * Retorna la cantidad de vértices del grafo.
     */
    public int getNumeroVertices()
    {
        return this.vertices.size();
    }

    /**
     * Retorna un {@link java.lang.Iterable<> Iterable} de {@link algoritmo.Arco Arco} con los no
     * dirigidos del grafo.
     */
    public Iterable<Arco> getArcosNoDirigidos()
    {
        return this.arcos;
    }
    
    public void imprimir()
    {
        for(Vertice v : this.vertices)
        {
            System.out.print(v.getId()+" : ");
            try
            {
                for(Arco a : getAdyacentes(v))
                {
                    System.out.print(a + " ");
                }
            } catch (GrafoVerticeInexistenteException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    public void habilitarArco(Arco a, boolean habilitado)
    {
        try
        {
            for(Arco arco : getAdyacentes(a.getInicio()))
                if(arco.getFin() == a.getFin())
                    arco.setHabilitado(habilitado);
        } catch (GrafoVerticeInexistenteException e)
        {
                e.printStackTrace();
        }
        
    }
}
