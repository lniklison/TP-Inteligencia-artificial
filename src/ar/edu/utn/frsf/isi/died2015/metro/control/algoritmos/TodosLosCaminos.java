package ar.edu.utn.frsf.isi.died2015.metro.control.algoritmos;

import java.util.HashMap;
import java.util.LinkedList;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Grafo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Vertice;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoVerticeInexistenteException;

/**
 * Clase que crea un algoritmo que busca todos los caminos de un {@link algoritmo.Grafo Grafo} entre
 * un {@link algoritmo.Vertice vértice} <code>inicio</code> y un {@link algoritmo.Vertice vértice}
 * <code>fin</code>.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class TodosLosCaminos
{
    private HashMap<Vertice, Boolean> visitado;
    private LinkedList<Arco> unCamino;
    private LinkedList<LinkedList<Arco>> todosLosCaminos;
    private Vertice inicio, fin;
    private Grafo grafo;

    /**
     * Constructor del algoritmo.
     * 
     * @param grafo
     *            {@link algoritmo.Grafo Grafo} en donde buscar los caminos.
     * @param inicio
     *            {@link algoritmo.Vertice Vértice} inicial de los caminos.
     * @param fin
     *            {@link algoritmo.Vertice Vértice} final de los caminos.
     * @throws GrafoVerticeInexistenteException
     *             Si alguno de los {@link algoritmo.Vertice vértices} <code>inicio</code> o
     *             <code>fin</code> no pertenecen al grafo.
     */
    public TodosLosCaminos(Grafo grafo, Vertice inicio, Vertice fin)
            throws GrafoVerticeInexistenteException
    {
        if(grafo == null)
            throw new NullPointerException("El grafo no puede ser nulo.");
        if(inicio == null)
            throw new NullPointerException("El vértice inicio no puede ser nulo.");
        if(fin == null)
            throw new NullPointerException("El vértice final no puede ser nulo.");

        this.inicio = inicio;
        this.fin = fin;
        this.grafo = grafo;
        this.visitado = new HashMap<Vertice, Boolean>(grafo.numeroVertices());
        for(Vertice v : grafo.getVertices())
            this.visitado.put(v, false);

        this.unCamino = new LinkedList<Arco>();
        this.todosLosCaminos = new LinkedList<LinkedList<Arco>>();

        aplicar(this.inicio);
    }

    /*
     * Aplica el algoritmo recursivo.
     */
    private void aplicar(Vertice actual) throws GrafoVerticeInexistenteException
    {
        visitado.put(actual, true);

        if(actual == this.fin)
        {
            procesarSolucion();
            return;
        }

        for(Arco a : grafo.getAdyacentes(actual))
        {
            if(a.isHabilitado())
            {
                Vertice siguiente = a.getFin();

                if(siguiente.isHabilitado())
                {
                    if(!visitado.get(siguiente))
                    {
                        unCamino.addLast(a);
                        visitado.put(siguiente, true);

                        aplicar(siguiente);

                        unCamino.removeLast();
                        visitado.put(siguiente, false);
                    }
                }
            }
        }

    }

    /*
     * Procesa una solución (camino) encontrada.
     */
    @SuppressWarnings("unchecked")
    private void procesarSolucion()
    {
        // Agrega una copia del camino a la lista de caminos ya encontrados.
        this.todosLosCaminos.add((LinkedList<Arco>) this.unCamino.clone());
    }

    /*
     * Imprime la solución alcanzada hasta el momento.
     */
    private void imprimirSolucion(LinkedList<Arco> unCamino)
    {
        for(Arco a : unCamino)
        {
            System.out.print(a.getInicio().getId());

            if(a.getFin() != this.fin)
                System.out.print(" => ");
            else
                System.out.print(" => " + a.getFin().getId());
        }
    }

    /**
     * Imprime por stdout todos los caminos encontrados.
     */
    public void imprimirCaminos()
    {
        for(LinkedList<Arco> c : this.todosLosCaminos)
        {
            imprimirSolucion(c);
            System.out.println();
        }
    }

    /**
     * Retorna una lista de listas con los arcos ordenados que representan todos y cada uno de los
     * caminos encontrados.
     */
    public LinkedList<LinkedList<Arco>> getCaminos()
    {
        return this.todosLosCaminos;
    }
}
