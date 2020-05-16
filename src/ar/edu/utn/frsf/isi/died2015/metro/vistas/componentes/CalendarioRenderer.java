package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CalendarioRenderer extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = -3399201707763097248L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(column == 0 || column == 6)
            setBackground(new Color(255, 213, 229));
        else
            setBackground(new Color(255, 246, 213));

        if(value == null)
            setBackground(new Color(244, 244, 244));
        else
        {
            if(isSelected)
                setBackground(new Color(200, 220, 255));
        }

        setForeground(Color.black);

        return this;
    }

}
