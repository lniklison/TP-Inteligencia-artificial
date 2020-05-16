package ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedHashSet;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Arco;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Grafo;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Vertice;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoArcoInexistenteException;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones.GrafoVerticeInexistenteException;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.ShapeStroke;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.listeners.PanelGrafoListener;

public class PanelGrafo extends JPanel implements MouseListener, MouseMotionListener, KeyListener,
                ActionListener
{
    private static final long serialVersionUID = -5157978796326300726L;

    private static final double VELOCIDAD_SCROLL = 20;

    private static final Color COLOR_SELECCION_CAMINO = Color.BLACK;
    private static final Color COLOR_SELECCION_MOUSE = Color.BLACK;
    private static final Color COLOR_ARCO_INHABILITADO = Color.BLACK;
    private static final float ANCHO_TRAZO_SELECCION = 4f;

    private Grafo grafo;
    
    private ArrayList<PanelGrafoListener> listeners;

    // Almacenamos el vértice Ó el arco seleccionado. Nunca pueden ser simultaneamente != null.
    private Vertice verticeSeleccionado;
    private Arco arcoSeleccionado;

    // Almacenamos la traslación del grafo cuando se hace "pan".
    private int traslacionX;
    private int traslacionY;

    // Tamaños relativos a los dibujos del grafo.
    private float anchoLineaArco;
    private float diametroVertice;

    // Puntos de inicio y final cuando se realiza un paneo de la pantalla.
    private Point puntoInicioPaneo;
    private Point puntoFinalPaneo;
    private AffineTransform transformadorCoordenadas;

    private ArrayList<Arco> caminoSeleccionado;

    private JPopupMenu menuVertice;
    private JMenuItem mitemDesde;
    private JMenuItem mitemHasta;
    private JMenuItem mitemVerFicha;

    private JPopupMenu menuArco;
    private JMenu menuArcoA;
    private JMenu menuArcoB;
    private JMenuItem mitemInhabilitarA;
    private JMenuItem mitemInhabilitarB;

    private Arco arcoA, arcoB;

    public PanelGrafo(Grafo grafo)
    {
        super(true);

        this.listeners = new ArrayList<PanelGrafoListener>();
        
        this.grafo = grafo;

        this.verticeSeleccionado = null;
        this.arcoSeleccionado = null;

        this.traslacionX = 0;
        this.traslacionY = 0;

        this.anchoLineaArco = 10.0f;
        this.diametroVertice = 30.0f;

        this.puntoInicioPaneo = new Point(0, 0);
        this.puntoFinalPaneo = new Point(0, 0);
        this.transformadorCoordenadas = null;

        this.caminoSeleccionado = new ArrayList<Arco>();

        this.menuVertice = new JPopupMenu();
        this.mitemDesde = new JMenuItem("Desde aquí");
        this.mitemHasta = new JMenuItem("Hasta aquí");
        this.mitemVerFicha = new JMenuItem("Ver ficha");
        this.menuVertice.add(this.mitemVerFicha);
        this.menuVertice.add(this.mitemDesde);
        this.menuVertice.add(this.mitemHasta);

        this.menuArco = new JPopupMenu();
        this.menuArcoA = new JMenu();
        this.menuArcoB = new JMenu();
        this.mitemInhabilitarA = new JMenuItem("");
        this.mitemInhabilitarB = new JMenuItem("");
        this.menuArco.add(menuArcoA);
        this.menuArco.add(menuArcoB);
        this.menuArcoA.add(mitemInhabilitarA);
        this.menuArcoB.add(mitemInhabilitarB);

        // Registramos los listeners.
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);

        // --------------
        this.mitemInhabilitarA.addActionListener(this);
        this.mitemInhabilitarB.addActionListener(this);
        this.mitemVerFicha.addActionListener(this);
        this.mitemDesde.addActionListener(this);
        this.mitemHasta.addActionListener(this);
        // --------------
    }

    public void addPanelGrafoListeners(PanelGrafoListener listener)
    {
        this.listeners.add(listener);
    }

    public void setGrafo(Grafo grafo)
    {
        this.grafo = grafo;
        this.caminoSeleccionado = new ArrayList<Arco>();
    }

    public void marcarArcos(ArrayList<Arco> arcos)
    {
        this.caminoSeleccionado = arcos;
    }
    
    public Vertice getVerticeSeleccionado()
    {
        return this.verticeSeleccionado;
    }
    
    /*
     * Crea un triángulo del doble de altura de s.
     */
    private Shape crearTriangulo(final float s)
    {
        final GeneralPath gp = new GeneralPath();
        gp.moveTo(-s, 0.0f);
        gp.lineTo(s, s);
        gp.lineTo(s, -s);
        gp.closePath();
        return gp;
    }
    
    private void dibujarVertice(Graphics2D g, Vertice v)
    {
        LinkedHashSet<Color> colores = new LinkedHashSet<Color>();

        // Buscamos los colores de los arcos adyacentes.
        try
        {
            for (Arco a : this.grafo.getAdyacentes(v))
                colores.add(a.getColor());

        } catch (GrafoVerticeInexistenteException e)
        {
            e.printStackTrace();
            return;
        }
        if (colores.isEmpty()) colores.add(Color.BLACK); // para detectar algún error.
        int cant = colores.size();
        int anguloPorcion = (int) 360 / cant;
        int anguloInicio = 90 + (anguloPorcion / 2);
        int radio = (int) (this.diametroVertice / 2);

        // Dibujamos el vértice.
        for (Color c : colores)
        {
            g.setColor(c);
            g.fillArc((int) (v.getPosX() - radio), (int) (v.getPosY() - radio),
                            (int) this.diametroVertice, (int) this.diametroVertice, anguloInicio,
                            anguloPorcion);
            anguloInicio += anguloPorcion;
        }

        // Dibujamos la información del vértice.
        double d = (Math.cos(Math.PI / 4) * (this.diametroVertice - this.diametroVertice / 2.5));
        g.setColor(Color.BLACK);
//        g.drawString(v.getDato().toString(), (int) (v.getPosX() + v.getDespX() + d),
//                        (int) (v.getPosY() + v.getDespY() - d));
    }

    private void dibujarArco(Graphics2D g, Arco a)
    {
        Vertice inicio = a.getInicio();
        Vertice fin = a.getFin();

        g.setStroke(new BasicStroke(this.anchoLineaArco));

        // Dibujamos la línea
        g.setColor(a.getColor());

        g.drawLine((int) inicio.getPosX(), (int) inicio.getPosY(), (int) fin.getPosX(),
                        (int) fin.getPosY());

        if (!a.isHabilitado())
        {
            // Verificamos si el arco inverso también está deshabilitado
            try
            {
                Arco i = this.grafo.getArco(a.getFin(), a.getInicio());
                if (!i.isHabilitado())
                {
                    g.setStroke(new BasicStroke(this.anchoLineaArco, BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_BEVEL, 10, new float[] { 5, 5 }, 0));
                    g.setColor(COLOR_ARCO_INHABILITADO);
                } else
                {
                    g.setColor(a.getColor().darker());
                    g.setStroke(new ShapeStroke(crearTriangulo(4f), 10f));

                }
            } catch (GrafoArcoInexistenteException | GrafoVerticeInexistenteException e)
            {
                e.printStackTrace();
            }

            g.drawLine((int) inicio.getPosX(), (int) inicio.getPosY(), (int) fin.getPosX(),
                            (int) fin.getPosY());
        } else
        {
            try
            {
                Arco i = this.grafo.getArco(a.getFin(), a.getInicio());
                if (!i.isHabilitado())
                {
                    g.setColor(a.getColor().darker());
                    g.setStroke(new ShapeStroke(crearTriangulo(4f), 10f));

                    g.drawLine((int) fin.getPosX(), (int) fin.getPosY(), (int) inicio.getPosX(),
                                    (int) inicio.getPosY());
                }
            } catch (GrafoArcoInexistenteException | GrafoVerticeInexistenteException e)
            {
                e.printStackTrace();
            }

        }

        // Dibujamos la información del arco
        g.setColor(Color.DARK_GRAY);
        double dx = (inicio.getPosX() + inicio.getDespX() + fin.getPosX() + fin.getDespX()) / 2;
        double dy = (inicio.getPosY() + inicio.getDespY() + fin.getPosY() + fin.getDespY()) / 2;
        AffineTransform orig = g.getTransform();
        g.translate(dx, dy);
        g.rotate(-Math.PI/4);
        g.drawString(a.getDato().toString(), 0, 0);
        g.setTransform(orig);
    }

    //

    private void dibujarSeleccion(Graphics2D g)
    {
        if (this.caminoSeleccionado != null)
        {
            if (!this.caminoSeleccionado.isEmpty())
            {
                Vertice inicio, fin;
                g.setStroke(new ShapeStroke(crearTriangulo(4f), 10f));
                for (Arco arco : this.caminoSeleccionado)
                {
                    inicio = arco.getInicio();
                    fin = arco.getFin();

                    g.setColor(COLOR_SELECCION_CAMINO);

                    // La línea
                    g.drawLine((int) fin.getPosX(), (int) fin.getPosY(), (int) inicio.getPosX(),
                                    (int) inicio.getPosY());
                }

            }

        }
    }

    private void dibujarGrafo(Graphics2D g)
    {
        if (this.grafo == null) return;

        Stroke defaultStroke = g.getStroke();
        Vertice inicio, fin;

        // Dibujamos el arco seleccionado con el mouse.
        if (this.arcoSeleccionado != null)
        {
            inicio = this.arcoSeleccionado.getInicio();
            fin = this.arcoSeleccionado.getFin();

            // Dibujamos la línea de fondo
            g.setStroke(new BasicStroke(this.anchoLineaArco + ANCHO_TRAZO_SELECCION));
            g.setColor(COLOR_SELECCION_MOUSE);

            g.drawLine((int) inicio.getPosX(), (int) inicio.getPosY(), (int) fin.getPosX(),
                            (int) fin.getPosY());
            // y arriba el arco en cuestión.
            // dibujarArco(g, this.arcoSeleccionado);
        }

        // Dibujamos los arcos.
        for (Vertice v : this.grafo.getVertices())
        {
            try
            {
                for (Arco a : this.grafo.getAdyacentes(v))
                    dibujarArco(g, a);
            } catch (GrafoVerticeInexistenteException e)
            {
                e.printStackTrace();
            }
        }

        dibujarSeleccion(g);

        // Dibujamos el vértice seleccionado con el mouse.
        if (this.verticeSeleccionado != null)
        {
            g.setColor(COLOR_SELECCION_MOUSE);
            g.fillOval((int) (this.verticeSeleccionado.getPosX() - (this.diametroVertice + ANCHO_TRAZO_SELECCION) / 2),
                            (int) (this.verticeSeleccionado.getPosY() - (this.diametroVertice + ANCHO_TRAZO_SELECCION) / 2),
                            (int) (this.diametroVertice + ANCHO_TRAZO_SELECCION),
                            (int) (this.diametroVertice + ANCHO_TRAZO_SELECCION));
        }

        // Dibujamos los nodos.

        // Restauramos el estilo de línea.
        g.setStroke(defaultStroke);
        Font f = new Font("Dialog", Font.BOLD, 12);
        Font fd = g.getFont();
        g.setFont(f);
        for (Vertice v : this.grafo.getVertices())
            dibujarVertice(g, v);
        g.setFont(fd);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHints(rh);

        Graphics2D g2 = ((Graphics2D) g);

        if (this.transformadorCoordenadas == null)
            this.transformadorCoordenadas = g2.getTransform();
        else
            g2.setTransform(this.transformadorCoordenadas);

        // Dibujarmos el fondo.
        ((Graphics2D) g).setPaint(new GradientPaint(-this.traslacionX, getHeight()
                        - this.traslacionY, new Color(200, 200, 200), -this.traslacionX,
                        -this.traslacionY, Color.WHITE));
        ((Graphics2D) g).fill(new Rectangle2D.Double(-this.traslacionX, -this.traslacionY,
                        getWidth(), getHeight()));

        // Dibujamos el grafo.
        dibujarGrafo((Graphics2D) g);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 600);
    }

    /*
     * Obtiene el vértice seleccioando, de lo contrario retorna null.
     */
    private Vertice getVerticeSeleccionado(int mouseX, int mouseY)
    {
        Ellipse2D circulo;

        for (Vertice v : this.grafo.getVertices())
        {
            circulo = new Ellipse2D.Double(v.getPosX() - (this.diametroVertice / 2), v.getPosY()
                            - (this.diametroVertice / 2), this.diametroVertice,
                            this.diametroVertice);
            if (circulo.contains(mouseX - this.traslacionX, mouseY - this.traslacionY)) return v;
        }
        return null;
    }

    /*
     * Obtiene el arco seleccionado, de lo contrario retorna null.
     */
    private Arco getArcoSeleccionado(int mouseX, int mouseY)
    {
        Line2D linea;
        Vertice inicio, fin;

        for (Arco a : this.grafo.getArcosNoDirigidos())
        {
            inicio = a.getInicio();
            fin = a.getFin();

            linea = new Line2D.Double(inicio.getPosX(), inicio.getPosY(), fin.getPosX(),
                            fin.getPosY());

            if (linea.intersects(mouseX - (this.anchoLineaArco / 2) - this.traslacionX, mouseY
                            - (this.anchoLineaArco / 2) - this.traslacionY, this.anchoLineaArco,
                            this.anchoLineaArco)) return a;
        }
        return null;
    }

    /* MOUSE LISTENER *************************************************************************** */
    @Override
    public void mousePressed(MouseEvent e)
    {
        this.requestFocus();

        // Si se suelta el botón derecho o izquierdo del mouse, verificamos si seleccionamos
        // seleccionamos algo.
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3)
        {
            this.verticeSeleccionado = getVerticeSeleccionado(e.getX(), e.getY());
            if (this.verticeSeleccionado == null)
                this.arcoSeleccionado = getArcoSeleccionado(e.getX(), e.getY());
            else
                this.arcoSeleccionado = null;

            /*********************************/
            if (e.getButton() == MouseEvent.BUTTON3)
            {
                if (this.arcoSeleccionado != null)
                {
                    Vertice inicio = this.arcoSeleccionado.getInicio();
                    Vertice fin = this.arcoSeleccionado.getFin();

                    try
                    {
                        this.menuArcoA.setText(inicio.getDato().toString() + " => "
                                        + fin.getDato().toString());
                        this.menuArcoB.setText(fin.getDato().toString() + " => "
                                        + inicio.getDato().toString());

                        this.arcoA = this.grafo.getArco(inicio, fin);
                        this.arcoB = this.grafo.getArco(fin, inicio);

                        if (arcoA.isHabilitado())
                            this.mitemInhabilitarA.setText("Inhabilitar");
                        else
                            this.mitemInhabilitarA.setText("Habilitar");

                        if (arcoB.isHabilitado())
                            this.mitemInhabilitarB.setText("Inhabilitar");
                        else
                            this.mitemInhabilitarB.setText("Habilitar");

                    } catch (GrafoArcoInexistenteException | GrafoVerticeInexistenteException e1)
                    {
                        e1.printStackTrace();
                    }

                    this.menuArco.show(e.getComponent(), e.getX(), e.getY());
                }
                else if(this.verticeSeleccionado != null)
                {
                    this.menuVertice.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            /*********************************/

        } else if (e.getButton() == MouseEvent.BUTTON2)
        {
            // Posiblemente estemos queriendo hacer un paneo de la pantalla. Almacenamos la posición
            this.puntoInicioPaneo = e.getPoint();
            this.puntoFinalPaneo = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) // menúes contextuales
    {}

    @Override
    public void mouseEntered(MouseEvent e)
    {}

    @Override
    public void mouseExited(MouseEvent e)
    {}

    @Override
    public void mouseClicked(MouseEvent e)
    {}

    /* MOUSE MOTION LISTENER ******************************************************************** */

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // Si estamos arrastrando algún vértice con el botón izquierdo del mouse lo reposicionamos.
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK
                        && this.verticeSeleccionado != null)
        {
            this.verticeSeleccionado.setPosX(e.getX() - this.traslacionX);
            this.verticeSeleccionado.setPosY(e.getY() - this.traslacionY);

        } else if ((e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == MouseEvent.BUTTON2_DOWN_MASK)
        { // Si estamos arrastrando el mouse con el botón del medio presionado, hacemos el paneo
          // de la pantalla.
            this.puntoFinalPaneo = e.getPoint();

            double deltaX = this.puntoFinalPaneo.getX() - this.puntoInicioPaneo.getX();
            double deltaY = this.puntoFinalPaneo.getY() - this.puntoInicioPaneo.getY();

            this.traslacionX += deltaX;
            this.traslacionY += deltaY;

            this.transformadorCoordenadas.translate(deltaX, deltaY);

            this.puntoInicioPaneo = this.puntoFinalPaneo;
            this.puntoFinalPaneo = null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {}

    /* KEY LISTENER ***************************************************************************** */

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.traslacionX -= (int) VELOCIDAD_SCROLL;
            this.transformadorCoordenadas.translate(-VELOCIDAD_SCROLL, 0);
            this.puntoInicioPaneo.translate(-(int) VELOCIDAD_SCROLL, 0);
            this.puntoFinalPaneo = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            this.traslacionX += (int) VELOCIDAD_SCROLL;
            this.transformadorCoordenadas.translate(VELOCIDAD_SCROLL, 0);
            this.puntoInicioPaneo.translate((int) VELOCIDAD_SCROLL, 0);
            this.puntoFinalPaneo = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            this.traslacionY += (int) VELOCIDAD_SCROLL;
            this.transformadorCoordenadas.translate(0, VELOCIDAD_SCROLL);
            this.puntoInicioPaneo.translate(0, (int) VELOCIDAD_SCROLL);
            this.puntoFinalPaneo = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            this.traslacionY -= (int) VELOCIDAD_SCROLL;
            this.transformadorCoordenadas.translate(0, -VELOCIDAD_SCROLL);
            this.puntoInicioPaneo.translate(0, -(int) VELOCIDAD_SCROLL);
            this.puntoFinalPaneo = null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {}

    @Override
    public void keyReleased(KeyEvent e)
    {}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.mitemInhabilitarA)
        {
            this.arcoA.setHabilitado(!this.arcoA.isHabilitado());
            this.caminoSeleccionado.clear();
        } else if (e.getSource() == this.mitemInhabilitarB)
        {
            this.arcoB.setHabilitado(!this.arcoB.isHabilitado());
            this.caminoSeleccionado.clear();
        } else if(e.getSource() == this.mitemDesde)
        {
            for(PanelGrafoListener l : listeners)
                l.verticeOrigenSeleccionado(new EventObject(this));
        }else if(e.getSource() == this.mitemHasta)
        {
            for(PanelGrafoListener l : listeners)
                l.verticeDestinoSeleccionado(new EventObject(this));
        }else if(e.getSource() == this.mitemVerFicha)
        {
            for(PanelGrafoListener l : listeners)
                l.verFichaEstacion(new EventObject(this));
        }

    }

    /* ***************************************************************************** */

}
