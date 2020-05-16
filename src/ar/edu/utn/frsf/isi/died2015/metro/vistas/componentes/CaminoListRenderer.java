package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Camino;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Calle;

public class CaminoListRenderer extends JPanel implements ListCellRenderer<Camino>
{

    private static final long serialVersionUID = 9095459024499906629L;
    private static final String HTML_OPEN = "<html><body style='width: 100px'><b>Líneas:</b> ";
    private static final String HTML_CLOSE = "</html>";
    
    private static final Font fuenteTiempo = new Font("Dialog", Font.BOLD, 20);
    private static final Font fuenteNegrita = new Font("Dialog", Font.BOLD, 11);
    private static final Color colorSeleccion = new Color(230,230,230);
    
    private JLabel lblTiempo, lblMinutos, lblLineas;
    
    public CaminoListRenderer()
    {
        super();
        this.setBorder(new EmptyBorder(new Insets(3, 3, 3, 3)));
        
        this.lblTiempo = new JLabel("");
        this.lblTiempo.setFont(fuenteTiempo);

        this.lblMinutos = new JLabel("minutos.");
        this.lblMinutos.setFont(fuenteNegrita);

        this.lblLineas = new JLabel("");

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 50, 0, 0};
        gbl.rowHeights = new int[] { 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        this.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        
        gbc.gridy = 0;
        this.add(this.lblTiempo, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridy = 1;
        this.add(this.lblMinutos, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridheight = 2;
        this.add(this.lblLineas, gbc);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Camino> list, Camino value,
                    int index, boolean isSelected, boolean cellHasFocus)
    {
        
        this.lblLineas.setOpaque(true);
        this.lblMinutos.setOpaque(true);
        this.lblTiempo.setOpaque(true);
        
        this.lblTiempo.setText(String.valueOf(value.getTiempo()));
        String s = ""; int cl = value.getNumeroLineasTrenes();
        for(Calle l : value.getLineasTrenes())
        {
            if(--cl != 0)
                s += l.getNombre() + ", ";
            else 
                s += l.getNombre();
        }
        this.lblLineas.setText(HTML_OPEN + s + HTML_CLOSE);
        
        if(index == 0)
        {
            this.lblTiempo.setIcon(new ImageIcon(getClass().getResource("../imagenes/estrella.png")));
        }else
        {
            this.lblTiempo.setIcon(new ImageIcon(getClass().getResource("../imagenes/tiempo.png")));
        }
        
        if (isSelected) {
            this.lblLineas.setBackground(colorSeleccion);
            this.lblMinutos.setBackground(colorSeleccion);
            this.lblTiempo.setBackground(colorSeleccion);
            this.setBackground(colorSeleccion);
        } else { 
            lblLineas.setBackground(list.getBackground());
            lblMinutos.setBackground(list.getBackground());
            lblTiempo.setBackground(list.getBackground());
            setBackground(list.getBackground());
        }
        
        return this;
    }
}
