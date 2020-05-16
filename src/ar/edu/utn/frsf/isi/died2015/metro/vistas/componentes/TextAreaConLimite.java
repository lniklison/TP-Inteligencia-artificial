package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import javax.swing.JTextArea;
import javax.swing.text.PlainDocument;

/**
 * Clase que crea un JTextArea con la restricción de la cantidad de caracteres que se pueden
 * ingresar y si se fuerza el uso de sólo mayúsculas.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 * @see {@link javax.swing.JTextArea}, {@link javax.swing.text.PlainDocument}
 *      {@link DocumentFilterConLimite.vistas.componentes.DocumentSizeFilter}
 */
public class TextAreaConLimite extends JTextArea
{
    private static final long serialVersionUID = 1L;
    private PlainDocument doc;
    private DocumentFilterConLimite filter;

    /**
     * Constructor por defecto. Limita la entrada a 20 caracteres y con el forzado de mayúsculas
     * desactivado.
     */
    public TextAreaConLimite()
    {
        super();
        doc = new PlainDocument();
        filter = new DocumentFilterConLimite();
        doc.setDocumentFilter(filter);
        super.setDocument(doc);
    }

    /**
     * Constructor parametrizado. Si el argumentos <i>limiteCaracteres</i> es un número menor o
     * igual que cero, se creará un componente con un límite de 20 caracteres.
     * 
     * @param limiteCaracteres
     *            Cantidad máxima de caracteres que se pueden ingresar.
     * @param mayusculas
     *            true para forzar el uso de mayúsculas.
     */
    public TextAreaConLimite(int limiteCaracteres, Boolean mayusculas)
    {
        super();
        doc = new PlainDocument();

        try
        {
            filter = new DocumentFilterConLimite(limiteCaracteres, mayusculas);
        }
        catch(IllegalArgumentException e)
        {
            filter = new DocumentFilterConLimite(20, mayusculas);
        }

        doc.setDocumentFilter(filter);
        super.setDocument(doc);
    }

    /**
     * Constructor parametrizado. Establece el número de filas y columnas, la cantidad máxima de
     * caracteres que se pueden ingresar y si se debe usar sólo mayúsculas. Si el argumento
     * <i>limiteCaracteres</i> es un número menor o igual que cero se creará un componente con un
     * límite de 20 caracteres.
     * 
     * @param filas
     *            Número de filas.
     * @param columnas
     *            Número de columnas.
     * @param limiteCaracteres
     *            Número máximo de caracteres que se pueden ingresar.
     * @param mayusculas
     *            true para forzar el uso de mayúsculas.
     * @see {@link javax.swing.JTextArea.JTextArea(int rows, int columns)}
     */
    public TextAreaConLimite(int columnas, int filas, int limiteCaracteres, Boolean mayusculas)
    {
        super(filas, columnas);
        doc = new PlainDocument();

        try
        {
            filter = new DocumentFilterConLimite(limiteCaracteres, mayusculas);
        }
        catch(IllegalArgumentException e)
        {
            filter = new DocumentFilterConLimite(20, mayusculas);
        }

        doc.setDocumentFilter(filter);
        super.setDocument(doc);
    }

    /**
     * Establece la cantidad máxima de caracteres que se pueden ingresar. Si el argumento es un
     * número menor o igual que cero se ignora la llamada.
     * 
     * @param limiteCaracteres
     *            El máximo de caracteres que se pueden ingresar.
     */
    public void setLimiteCaracteres(int limiteCaracteres)
    {
        try
        {
            filter.setLimiteCaracteres(limiteCaracteres);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Retorna la cantidad máxima de caracteres que se pueden ingresar.
     */
    public int getLimiteCaracteres()
    {
        return filter.getLimiteCaracteres();
    }

    /**
     * Retorna si está habilidado el forzado de mayúsculas.
     */
    public Boolean getMayusculas()
    {
        return filter.getMayusculas();
    }

    /**
     * Establece el forzado de mayúsculas.
     * 
     * @param mayusculas
     *            true para forzar el uso de mayúsculas.
     */
    public void setMayusculas(Boolean mayusculas)
    {
        this.filter.setMayusculas(mayusculas);
    }
}
