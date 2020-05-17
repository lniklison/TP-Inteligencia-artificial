package ar.edu.utn.frsf.isi.died2015.metro.modelo;

public class Vertice
{
    private int id;
    private boolean habilitado;
    private Object dato;
    
    // posición (x,y)
    private double x;
    private double y;
    // desplazamiento en x e y
    private double dx;
    private double dy;
    
    public Vertice()
    {
        super();
        this.id = 0;
        this.habilitado = true;
        this.dato = new Object();
    }
    
    public Vertice(int id, boolean habilitado, Object dato)
    {
        super();
        this.id = id;
        this.habilitado = habilitado;
        this.dato = dato;
    }

    public int getId()
    {
        return this.id;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public boolean isHabilitado()
    {
        return this.habilitado;
    }


    public void setHabilitado(boolean habilitado)
    {
        this.habilitado = habilitado;
    }
    
    public double getPosX()
    {
        return this.x;
    }


    public void setPosX(double x)
    {
        this.x = x;
    }


    public double getPosY()
    {
        return this.y;
    }


    public void setPosY(double y)
    {
        this.y = y;
    }

    public void setPos(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getDespX()
    {
        return this.dx;
    }


    public void setDespX(double dx)
    {
        this.dx = dx;
    }


    public double getDespY()
    {
        return this.dy;
    }


    public void setDespY(double dy)
    {
        this.dy = dy;
    }
    
    public void setDesp(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public Object getDato()
    {
        return this.dato;
    }

    public void setDato(Object dato)
    {
        this.dato = dato;
    }
    

    @Override
    public String toString()
    {
        return "Vertice [id=" + id 
                + ", habilitado=" + habilitado 
                + ", PosX: "+this.getPosX()
                + ", PosY: "+this.getDespY()
                + "]";
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Vertice)
        {
            Vertice otroVertice = (Vertice) obj;
            return this.id == otroVertice.getId();
        }
        return false;
    }
    
}

