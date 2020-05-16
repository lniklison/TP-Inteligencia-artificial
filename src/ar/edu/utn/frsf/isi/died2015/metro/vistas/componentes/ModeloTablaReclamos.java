package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Reclamo;

public class ModeloTablaReclamos extends AbstractTableModel
{
    private static final long serialVersionUID = 6419943509746368708L;
    private static final SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
    
    private Vector<Reclamo> datos;
    private String[] nombresColumnas = {"Fecha", "Estación", "Motivo"};

    public ModeloTablaReclamos()
    {
        super();
        this.datos = new Vector<Reclamo>();
    }

    public ModeloTablaReclamos(Vector<Reclamo> datos, String[] nombresColumnas)
    {
        super();
        this.datos = datos;
        this.nombresColumnas = nombresColumnas;
    }

    public void add(Reclamo reclamo)
    {
        this.datos.add(reclamo);
    }

    public Vector<Reclamo> getDatos()
    {
        return this.datos;
    }

    public void setDatos(Vector<Reclamo> datos)
    {
        this.datos = datos;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public int getRowCount()
    {
        return this.datos.size();
    }

    @Override
    public int getColumnCount()
    {
        return this.nombresColumnas.length;
    }

    @Override
    public String getColumnName(int column)
    {
        return this.nombresColumnas[column];
    }

    @Override
    public Object getValueAt(int fila, int col)
    {
        try
        {
            Reclamo reclamo = datos.elementAt(fila);
            
            switch(col)
            {
                case 0:
                    return formateadorFecha.format(reclamo.getFecha());
                case 1:
                    return reclamo.getEstacion().toString();
                case 2:
                    return reclamo.getMotivo();
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
        
        return null;
    }

}
