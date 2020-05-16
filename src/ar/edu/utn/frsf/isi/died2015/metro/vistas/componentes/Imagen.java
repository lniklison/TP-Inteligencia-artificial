package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Clase que crea una imagen a partir de un archivo de imagen externo.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class Imagen extends Component
{
    private static final long serialVersionUID = 8349329649911698096L;

    private static final int ANCHO = 50, ALTO = 50;
    private BufferedImage imagen;

    /**
     * Constructor de la imagen. Si la url es incorrecta o si se produce un
     * error al tratar de abrir la imagen, se creará una por defecto de 50px x
     * 50px.
     * 
     * @param url
     *            Ulr del archivo de la imagen.
     */
    public Imagen(String url)
    {
        super();
        // Tratamos abrir la url de la imagen.
        try
        {
            URL imgUrl = getClass().getResource(url);
            if (imgUrl != null) this.imagen = ImageIO.read(imgUrl);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        if (this.imagen != null)
            g.drawImage(this.imagen, 0, 0, null);
        else
        {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, ANCHO, ALTO);

            g2.setColor(Color.BLACK);
            g2.drawRect(0, 0, ANCHO, ALTO);

            g2.setColor(Color.RED);

            g2.setStroke(new BasicStroke(4));
            g2.drawLine(10, 10, ANCHO - 10, ALTO - 10);
            g2.drawLine(10, ALTO - 10, ANCHO - 10, 10);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        if (this.imagen == null)
        {
            return new Dimension(ANCHO, ALTO);
        } else
        {
            return new Dimension(this.imagen.getWidth(null), this.imagen.getHeight(null));
        }
    }
    
    @Override
    public Dimension getMinimumSize()
    {
        return super.getPreferredSize();
    }
}
