package ar.edu.utn.frsf.isi.died2015.metro.vistas.dialogos;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.Imagen;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes.PasswordFieldConLimite;

/**
 * Panel para el ingreso de una contraseña cuando se requiere una
 * funcionalidad que necesita privilegios de administrador.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class PanelPassword extends JPanel
{
    private static final long serialVersionUID = -7379645292274052738L;


    private JLabel lblMensaje;
    private PasswordFieldConLimite password;
    private Imagen imagen;

    public PanelPassword()
    {
        super();

        // Creamos los componentes.
        String html = "<html><body style='width: 200px'>";
        String msg = "La utilidad a la cual está queriendo acceder necesita privilegios de "
                        + "administrador.<br><br>Por favor ingrese la contraseña:";

        this.lblMensaje = new JLabel(html + msg);
        this.password = new PasswordFieldConLimite(30, false);
        this.imagen = new Imagen("../imagenes/llaves.png");

        // Creamos el layout y distribuimos los componentes.
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0 };
        gbl.rowHeights = new int[] { 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        this.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.imagen, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.lblMensaje, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.password, gbc);


        // Agregamos la tecla ENTER para que haga focus al componente siguiente.
        Set<KeyStroke> teclas = new HashSet<KeyStroke>();
        teclas.add(KeyStroke.getKeyStroke("TAB"));
        teclas.add(KeyStroke.getKeyStroke("ENTER"));
        password.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclas);

    }

    /**
     * Retorna el password ingresado.
     */
    public String getPassword()
    {
        return String.valueOf(this.password.getPassword());
    }

    /**
     * Limpia el campo del password.
     */
    public void limpiar()
    {
        this.password.setText("");
    }
}
