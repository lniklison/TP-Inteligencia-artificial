package tp.inteligencia.artificial;

import java.awt.EventQueue;

import javax.swing.UIManager;

import ar.edu.utn.frsf.isi.died2015.metro.vistas.VentanaPrincipal;
import java.lang.reflect.InvocationTargetException;

/**
 * Clase principal de la aplicación.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 */
public class Main
{

    public Main()
    {
        super();
    }

    /**
     * Lanza la aplicación.
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
        EventQueue.invokeAndWait(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    VentanaPrincipal window = new VentanaPrincipal();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
