package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.DetalleCamino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.TipoDetalleCamino;

public class DetalleCaminoRenderer extends JPanel implements ListCellRenderer<DetalleCamino>
{
    private static final long serialVersionUID = 9175373118764799705L;
    private static final String HTML_OPEN = "<html><body style='width: 100px'> ";
    private static final String HTML_CLOSE = "</html>";
    private static final Color colorSeleccion = new Color(230,230,230);
    
    private JLabel lblIcono, lblDescripcion, lblTiempo;
    private JPanel panelIcono, panelDescipcion;

    public DetalleCaminoRenderer()
    {
        super();
        this.setLayout(new BorderLayout(3, 3));
        
        this.lblIcono = new JLabel("");
        this.lblDescripcion = new JLabel("");
        this.lblTiempo = new JLabel("");

        this.panelIcono = new JPanel();
        this.panelIcono.setBorder(new EmptyBorder(3, 3, 3, 3));
        this.panelIcono.add(this.lblIcono);

        this.panelDescipcion = new JPanel(new GridLayout(0, 1));
        this.panelDescipcion.add(this.lblDescripcion);
        this.panelDescipcion.add(this.lblTiempo);

        this.add(this.panelIcono, BorderLayout.WEST);
        this.add(this.panelDescipcion, BorderLayout.CENTER);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends DetalleCamino> list,
                    DetalleCamino value, int index, boolean isSelected, boolean cellHasFocus)
    {

        String nombreImagen = "../imagenes/";
        String tiempo = " min.";
        TipoDetalleCamino tipo = value.getTipoDetalle();

        if (tipo == TipoDetalleCamino.ESTACION_ORIGEN)
        {
            nombreImagen += "pin.png";
            tiempo = "";
        } else if (tipo == TipoDetalleCamino.ESTACION_INTERMEDIA)
        {
            nombreImagen += "estacion.png";
            tiempo = "+" + String.valueOf(value.getTiempo()) + tiempo;
        } else if (tipo == TipoDetalleCamino.TRANSBORDO)
        {
            nombreImagen += "trasbordo.png";
            tiempo = "+" + String.valueOf(value.getTiempo()) + tiempo;
        } else if (tipo == TipoDetalleCamino.ESTACION_DESTINO)
        {
            nombreImagen += "pin.png";
            tiempo = "Total: " + String.valueOf(value.getTiempo()) + tiempo;
        }

        this.lblIcono.setIcon(new ImageIcon(getClass().getResource(nombreImagen)));
        this.lblDescripcion.setText(HTML_OPEN + value.getDescripcion() + HTML_CLOSE);
        this.lblTiempo.setText(tiempo);

        this.lblIcono.setOpaque(true);
        this.lblDescripcion.setOpaque(true);
        this.lblTiempo.setOpaque(true);

        if (isSelected)
        {
            
            this.lblIcono.setBackground(colorSeleccion);
            this.lblDescripcion.setBackground(colorSeleccion);
            this.lblTiempo.setBackground(colorSeleccion);
            this.setBackground(colorSeleccion);
            this.panelIcono.setBackground(colorSeleccion);
            this.panelDescipcion.setBackground(colorSeleccion);
        } else
        {
            this.lblIcono.setBackground(list.getBackground());
            this.lblDescripcion.setBackground(list.getBackground());
            this.lblTiempo.setBackground(list.getBackground());
            this.setBackground(list.getBackground());
            this.panelIcono.setBackground(list.getBackground());
            this.panelDescipcion.setBackground(list.getBackground());
        }

        return this;
    }
}
