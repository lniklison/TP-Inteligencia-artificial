package ar.edu.utn.frsf.isi.died2015.metro.vistas.componentes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Clase que crea un {@link javax.swing.text.DocumentFilter DocumentFilter} que limita la cantidad
 * de caracteres que se pueden ingresar en un componente de texto y posibilita el uso, o no, de sólo
 * mayúsculas.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 * @see {@link javax.swing.text.DocumentFilter}
 */
public class DocumentFilterConLimite extends DocumentFilter
{
    private int limiteCaracteres;
    private Boolean mayusculas;

    /**
     * Constructor por defecto. Inicializa el límite en 20 caracteres y el forzado de mayúsculas
     * desactivado.
     */
    public DocumentFilterConLimite()
    {
        super();
        this.limiteCaracteres = 20;
        this.mayusculas = false;
    }

    /**
     * Constructor parametrizado. Si el límite es un número menor o igual que cero se lanza una
     * excepción de tipo IllegalArgumentException.
     * 
     * @param limiteCaracteres
     *            El máximo de caracteres que se pueden ingresar.
     * @param mayusculas
     *            true para forzar el uso de mayúsculas.
     * @throws IllegalArgumentException
     *             Si el argumento es menor o igual que cero.
     * @see {@link java.lang.IllegalArgumentException}
     */
    public DocumentFilterConLimite(int limiteCaracteres, Boolean mayusculas)
            throws IllegalArgumentException
    {
        super();

        if(limiteCaracteres <= 0)
            throw new IllegalArgumentException(
                    "El límite de caracteres debe ser un número entero mayor que cero.");

        this.limiteCaracteres = limiteCaracteres;
        this.mayusculas = mayusculas;
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
            throws BadLocationException
    {
        if(mayusculas)
        {
            if((fb.getDocument().getLength() + str.length()) <= limiteCaracteres)
                super.insertString(fb, offs, str.toUpperCase(), a);
        } else
        {
            if((fb.getDocument().getLength() + str.length()) <= limiteCaracteres)
                super.insertString(fb, offs, str, a);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
            throws BadLocationException
    {
        if(mayusculas)
        {
            if((fb.getDocument().getLength() + str.length() - length) <= limiteCaracteres)
                super.replace(fb, offs, length, str.toUpperCase(), a);
        } else
        {
            if((fb.getDocument().getLength() + str.length() - length) <= limiteCaracteres)
                super.replace(fb, offs, length, str, a);
        }
    }

    /**
     * Retorna la cantidad máxima de caracteres que se pueden ingresar.
     */
    public int getLimiteCaracteres()
    {
        return this.limiteCaracteres;
    }

    /**
     * Define la cantidad máxima de caracteres que se pueden ingresar.
     * 
     * @param limiteCaracteres
     *            Cantidad máxima de caracteres que se pueden ingresar.
     * @throws IllegalArgumentException
     *             Si el argumento es menor o igual que cero.
     */
    public void setLimiteCaracteres(int limiteCaracteres) throws IllegalArgumentException
    {
        if(limiteCaracteres <= 0)
            throw new IllegalArgumentException(
                    "El límite de caracteres debe ser un número entero mayor que cero.");
        this.limiteCaracteres = limiteCaracteres;
    }

    /**
     * Retorna si está habilidado el forzado de mayúsculas.
     */
    public Boolean getMayusculas()
    {
        return this.mayusculas;
    }

    /**
     * Establece el forzado de mayúsculas.
     * 
     * @param mayusculas
     *            true si se quiere forzar el uso de mayúsculas, de lo contrario false.
     */
    public void setMayusculas(Boolean mayusculas)
    {
        this.mayusculas = mayusculas;
    }

}
