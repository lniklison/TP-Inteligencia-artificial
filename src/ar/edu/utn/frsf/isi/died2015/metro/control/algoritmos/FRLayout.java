package ar.edu.utn.frsf.isi.died2015.metro.control.algoritmos;

import java.util.Random;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Grafo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Vertice;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoVerticeInexistenteException;

/**
 * Clase que crea un distribuidor de nodos de un grafo basado en el algoritmo de {@link http
 * ://emr.cs.iit.edu/~reingold/force-directed.pdf Fruchterman-Reignold}.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino. 
 * @version 1.1
 */
public class FRLayout
{
    private Grafo grafo;
    private double tamVertice;
    private int maxIteraciones;

    // Variables físicas.
    private double gravedad;
    private double kHooke;
    private double tamMinResorte;
    private double amortiguacion;

    private double energia;
    private double ultimaEnergia;
    private double gradienteEnergia;
    private double energiaSistemaEstable;

    // Tamaño de la región de distribución.
    double altoRegion;
    double anchoRegion;
    // Frontera de dibujo.
    private double minFronteraX;
    private double minFronteraY;
    private double maxFronteraX;
    private double maxFronteraY;
    // Padding de la forntera.
    private double marco;

    /**
     * Constructor del distribuidor.
     * 
     * @param grafo
     *            {@link algoritmo.Grafo Grafo} cuyos nodos se quieren distribuir.
     * @param ancho
     *            El ancho de la región sobre la cual se distribuirán los nodos del grafo. Este debe
     *            ser >= 1.0.
     * @param alto
     *            El alto de la región sobre la cual se distribuirán los nodos del grafo. Este debe
     *            ser >= 1.0.
     * @param tamanioVertice
     *            Tamaño del cuadrado contenedor del vértice del grafo. Este debe ser <= al tamaño
     *            de la región.
     */
    public FRLayout(Grafo grafo, double ancho, double alto, double tamanioVertice)
    {
        super();

        setGrafo(grafo);

        setAnchoRegion(ancho);
        setAltoRegion(alto);
        setTamVertice(tamanioVertice);

        this.maxIteraciones = 500;

        this.gravedad = 50000.0;
        this.kHooke = 0.5;
        this.tamMinResorte = 30;
        this.amortiguacion = 0.1;

        this.energia = 0;
        this.ultimaEnergia = Double.MAX_VALUE;
        this.gradienteEnergia = Double.MAX_VALUE;
        this.energiaSistemaEstable = 0.000001;

        this.marco = 0;
    }

    /*
     * Posiciona los nodos de forma pseudo-aleatoria dentro de la región definida. El parámetro
     * deterministico es una bandera booleana utilizada para posicionar los nodos de forma
     * pseudo-aleatoria y determinística, esto es que cada vez que llame a esta función con true
     * posicionará los nodos en las mismas coordenadas pseudo-aleatorias. Esto es útil para
     * verificar los parámetros con los cuales se obtienen mejores distribuiciones para un mismo
     * grafo. Si se la llama con false aumenta la entropía y todas las distribuciones serán
     * distintas.
     */
    private void posicionesAleatorias(boolean deterministico)
    {
        Random rn;
        if(deterministico)
            rn = new Random(0);
        else
            rn = new Random();
        double x = 0, y = 0;
        for(Vertice v : grafo.getVertices())
        {
            x = rn.nextDouble() * this.maxFronteraX + this.marco;
            y = rn.nextDouble() * this.maxFronteraY + this.marco;
            v.setPos(x, y);
            v.setDesp(0, 0);
        }
    }

    /*
     * Calcula las fuerzas de repulsión y atracción del sistema de partículas modelado por el grafo.
     */
    private void calcularFuerzas()
    {
        double sumGravedadFX, sumGravedadFY, gravedadF, gravedadFX, gravedadFY;
        double sumHookeFX, sumHookeFY, hookeF, hookeFX, hookeFY;
        double distancia, deltaX, deltaY;

        this.energia = 0;

        // Gravedad
        for(Vertice base : grafo.getVertices())
        {
            sumGravedadFX = 0;
            sumGravedadFY = 0;

            for(Vertice otro : grafo.getVertices())
            {
                if(base == otro)
                    continue;

                deltaX = base.getPosX() - otro.getPosX();
                deltaY = base.getPosY() - otro.getPosY();

                distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if(distancia < 2 * tamVertice)
                    distancia = 2 * tamVertice;

                gravedadF = (double) 1 / (distancia * distancia) * this.gravedad;

                gravedadFX = gravedadF * deltaX / distancia;
                gravedadFY = gravedadF * deltaY / distancia;

                sumGravedadFX += gravedadFX;
                sumGravedadFY += gravedadFY;

                this.energia += Math.abs(gravedadF);
            }

            // Ley de Hooke
            sumHookeFX = 0;
            sumHookeFY = 0;
            Vertice otro;
            try
            {
                for(Arco a : this.grafo.getAdyacentes(base))
                {
                    otro = a.getFin();

                    deltaX = base.getPosX() - otro.getPosX();
                    deltaY = base.getPosY() - otro.getPosY();

                    distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    if(distancia == 0)
                        continue;

                    hookeF = -(double) kHooke * (distancia - this.tamMinResorte);

                    hookeFX = hookeF * deltaX / distancia;
                    hookeFY = hookeF * deltaY / distancia;

                    sumHookeFX += hookeFX;
                    sumHookeFY += hookeFY;

                    this.energia += hookeF;
                }
            }
            catch(GrafoVerticeInexistenteException e)
            {
                System.out.println(e);
                e.printStackTrace();
            }

            // Actualizamos los desplazamientos.
            if((base.getPosX() < this.minFronteraX || base.getPosX() > this.maxFronteraX)
                    && (base.getPosY() < this.minFronteraY || base.getPosY() > this.maxFronteraY))
            {
                base.setDespX((base.getDespX() + sumGravedadFX + sumHookeFX)
                        * Math.pow(this.amortiguacion, 12));
                base.setDespY((base.getDespY() + sumGravedadFY + sumHookeFY)
                        * Math.pow(this.amortiguacion, 12));
            } else if(base.getPosX() < this.minFronteraX || base.getPosX() > this.maxFronteraX)
            {
                base.setDespX((base.getDespX() + sumGravedadFX + sumHookeFX)
                        * Math.pow(this.amortiguacion, 12));
                base.setDespY((base.getDespY() + sumGravedadFY + sumHookeFY) * this.amortiguacion);
            } else if(base.getPosY() < this.minFronteraY || base.getPosY() > this.maxFronteraY)
            {
                base.setDespX((base.getDespX() + sumGravedadFX + sumHookeFX) * this.amortiguacion);
                base.setDespY((base.getDespY() + sumGravedadFY + sumHookeFY)
                        * Math.pow(this.amortiguacion, 12));
            } else
            {
                base.setDespX((base.getDespX() + sumGravedadFX + sumHookeFX) * this.amortiguacion);
                base.setDespY((base.getDespY() + sumGravedadFY + sumHookeFY) * this.amortiguacion);
            }
        }
    }

    /*
     * Calcula las posiciones finales de los nodos luego de cada iteración del algoritmo.
     */
    private void reposicionar()
    {
        double x, y;
        for(Vertice v : grafo.getVertices())
        {
            // Posición actual + el desplazamiento.
            x = v.getPosX() + v.getDespX();
            y = v.getPosY() + v.getDespY();

            // Si nos fuimos de la frontera, corregimos.
            if(x < minFronteraX)
                x = minFronteraX;
            if(x > maxFronteraX)
                x = maxFronteraX;
            if(y < minFronteraY)
                y = minFronteraY;
            if(y > maxFronteraY)
                y = maxFronteraY;

            v.setPos(x, y);
        }
    }

    /**
     * Ejecuta el algoritmo con los parámetros preestablecidos.
     * 
     * @param iteraciones
     *            Cantidad de iteraciones que queremos que realice el algoritmo. Si este valor es
     *            mayor al establecido mediante el método
     *            {@link algoritmo.FRLayout#setIteracionesMaximas(int)}, se utilizará el último
     *            valor establecido mediante el mismo o el valor por defecto (500 iteraciones).
     * @param deterministico
     *            Bandera booleana utilizada para pre-posicionar los nodos de forma
     *            pseudo-aleatoria, determinística y luego aplicarle el algoritmo. Esto significa
     *            que cada vez que se llame a este método con <code>true</code> se obtendrá siempre
     *            la misma distribución, lo cual es útil para verificar los valores de los
     *            parámetros del distribuidor con los cuales se obtienen mejores distribuiciones
     *            para un mismo grafo. Si se la llama con <code>false</code> aumenta la entropía y
     *            todas las distribuciones serán diferences en cada llamada.
     */
    public void ejecutar(int iteraciones, boolean deterministico)
    {

        this.energia = Double.MAX_VALUE;
        this.ultimaEnergia = 0;
        int i = 0; // contador de iteraciones

        // Primeros posicionamos los nodos en posiciones aleatorias dentro de la región.
        posicionesAleatorias(deterministico);

        // Aplicamos el layout.
        while(i < iteraciones && gradienteEnergia > energiaSistemaEstable)
        {
            this.ultimaEnergia = this.energia;

            calcularFuerzas();
            reposicionar();

            this.gradienteEnergia = Math.abs((this.ultimaEnergia - this.energia) / this.energia);

            ++i;
        }

        // Centramos el dibujo en la región.
        centrar();
    }

    /*
     * Función utilizada para centrar la distribución dentro de la región.
     */
    private void centrar()
    {
        double minx = Double.MAX_VALUE, miny = Double.MAX_VALUE, maxx = 0, maxy = 0;
        double x, y;

        // Calculamos el rectángulo del dibujo.
        for(Vertice v : this.grafo.getVertices())
        {
            x = v.getPosX();
            y = v.getPosY();

            if(x < minx)
                minx = x;
            if(x > maxx)
                maxx = x;
            if(y < miny)
                miny = y;
            if(y > maxy)
                maxy = y;
        }

        // Obtenemos el desplazamiento para todos los nodos;
        x = ((this.anchoRegion - (maxx - minx)) / 2) - minx;
        y = ((this.altoRegion - (maxy - miny)) / 2) - miny;

        // Reposicionamos todos los nodos.
        for(Vertice v : this.grafo.getVertices())
            v.setPos(v.getPosX() + x, v.getPosY() + y);
    }

    /*
     * Actualiza los valores de la frontera de dibujo.
     */
    private void actualizarFrontera()
    {
        this.minFronteraX = (this.tamVertice / 2) + this.marco;
        this.minFronteraY = (this.tamVertice / 2) + this.marco;
        this.maxFronteraX = this.anchoRegion - (this.tamVertice / 2) - this.marco;
        this.maxFronteraY = this.altoRegion - (this.tamVertice / 2) - this.marco;
    }

    /**
     * Obtiene el grafo sobre el cual se aplica el algoritmo.
     */
    public Grafo getGrafo()
    {
        return this.grafo;
    }

    /**
     * Establece el {@link algoritmo.Grafo grafo} sobre el cual se aplicará el algoritmo.
     */
    public void setGrafo(Grafo grafo)
    {
        if(grafo == null)
            throw new NullPointerException("El grafo no puede ser nulo.");
        this.grafo = grafo;
    }

    /**
     * Retorna la cantidad máxima de iteraciones que el algoritmo puede realizar.
     */
    public int getIteracionesMaximas()
    {
        return this.maxIteraciones;
    }

    /**
     * Establece la cantidad máxima de iteraciones que el algoritmo puede realizar.
     */
    public void setIteracionesMaximas(int iteraciones)
    {
        if(iteraciones < 0)
            throw new IllegalArgumentException("La cantidad de iteraciones no puede ser < 0.");
        this.maxIteraciones = iteraciones;
    }

    /**
     * Obtiene el tamaño del cuadrado contenedor de los vértices del grafo.
     */
    public double getTamVertice()
    {
        return this.tamVertice;
    }

    /**
     * Establece el tamaño del cuadrado contenedor de los vértices del grafo. El nuevo tamaño no
     * puede ser mayor que el tamaño de la región sobre la cual se distribuirán los nodos.
     * 
     * @see {@link algoritmo.FRLayout#getAnchoRegion()}
     * @see {@link algoritmo.FRLayout#getAltoRegion()}
     * @see {@link algoritmo.FRLayout#setAnchoRegion(double)}
     * @see {@link algoritmo.FRLayout#setAltoRegion(double)}
     */
    public void setTamVertice(double tamanioVertice)
    {
        if(tamanioVertice < 0.0)
            throw new IllegalArgumentException("El tamaño del vértice no puede ser < 0.");
        if(this.anchoRegion < tamanioVertice)
            throw new IllegalArgumentException(
                    "El tamaño del vértice no puede ser mayor que el ancho de la región.");
        if(this.altoRegion < tamanioVertice)
            throw new IllegalArgumentException(
                    "El tamaño del vértice no puede ser mayor que el alto de la región.");
        this.tamVertice = tamanioVertice;
        actualizarFrontera();
    }

    /**
     * Obtiene el multiplicador de la fuerza de gravedad del sistema. El valor por defecto es de
     * 200000.0.
     */
    public double getGravedad()
    {
        return gravedad;
    }

    /**
     * Establece el multiplicador de la fuerza de gravedad del sistema. Este debe ser >= 1.0.
     */
    public void setGravedad(double gravedad)
    {
        if(gravedad < 1.0)
            throw new IllegalArgumentException("El multiplicador de la gravedad debe ser >= 1.0.");
        this.gravedad = gravedad;
    }

    /**
     * Obtiene el multiplicador de Hooke. El valor por defecto es de 0.7.
     */
    public double getkHooke()
    {
        return this.kHooke;
    }

    /**
     * Establece el multiplicador de Hooke. Este debe ser >= 0.001.
     */
    public void setkHooke(double kHooke)
    {
        if(kHooke < 0.001)
            throw new IllegalArgumentException("El multiplicador de Hooke debe ser >= 0.001.");
        this.kHooke = kHooke;
    }

    /**
     * Obtiene el tamaño mínimo del resorte. El tamaño por defecto es de 80.0.
     */
    public double getTamMinResorte()
    {
        return this.tamMinResorte;
    }

    /**
     * Establece el tamaño mínimo del resorte. Este debe ser >= 1.0.
     */
    public void setTamMinResorte(double tamMinResorte)
    {
        if(tamMinResorte < 1.0)
            throw new IllegalArgumentException("El tamaño mínimo del resorte debe ser >= 1.0.");
        this.tamMinResorte = tamMinResorte;
    }

    /**
     * Obtiene el multiplicador de amortiguación del sistema. El valor por defecto es de 0.1.
     */
    public double getAmortiguacion()
    {
        return this.amortiguacion;
    }

    /**
     * Establece el multiplicador de amortiguación del sistema. Este debe ser >= 0.0
     */
    public void setAmortiguacion(double amortiguacion)
    {
        if(amortiguacion < 0.0)
            throw new IllegalArgumentException("El multiplicador de amortiguación debe ser >= 0.0.");
        this.amortiguacion = amortiguacion;
    }

    /**
     * Obtiene el alto de la región sobre la cual se distribuirán los nodos del grafo.
     */
    public double getAltoRegion()
    {
        return this.altoRegion;
    }

    /**
     * Establece el alto de la región sobre la cual se distribuirán los nodos del grafo. Este debe
     * ser >= 1.0.
     */
    public void setAltoRegion(double alto)
    {
        if(alto < 1.0)
            throw new IllegalArgumentException("El alto de la región debe ser >= 1.0.");
        this.altoRegion = alto;
        actualizarFrontera();
    }

    /**
     * Obtiene el ancho de la región sobre la cual se distribuirán los nodos del grafo.
     */
    public double getAnchoRegion()
    {
        return this.anchoRegion;
    }

    /**
     * Establece el ancho de la región sobre la cual se distribuirán los nodos del grafo. Este debe
     * ser >= 1.0.
     */
    public void setAnchoRegion(double ancho)
    {
        if(ancho < 1.0)
            throw new IllegalArgumentException("El alto de la región debe ser >= 1.0.");
        this.anchoRegion = ancho;
        actualizarFrontera();
    }

    /**
     * Obtiene el tamaño del marco de la región. El valor por defecto es de 0.0.
     */
    public double getMarco()
    {
        return this.marco;
    }

    /**
     * Establece el tamaño del marco de la región. Si el valor es positivo, se creará un marco
     * intero a la región, achicando el tamaño de la misma en 2 veces el tamaño del marco. Si el
     * valor es negativo, se obtendrá un comportamiento similar pero esta vez agrandando el tamaño
     * de la región 2 veces el tamaño del marco.
     */
    public void setMarco(double marco)
    {
        this.marco = marco;
        actualizarFrontera();
    }

}
