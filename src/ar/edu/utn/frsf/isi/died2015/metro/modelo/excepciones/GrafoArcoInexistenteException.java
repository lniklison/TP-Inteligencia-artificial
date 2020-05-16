package ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones;

public class GrafoArcoInexistenteException extends Exception
{
    private static final long serialVersionUID = -3263874438111355331L;

    public GrafoArcoInexistenteException()
    {
        super();
    }

    public GrafoArcoInexistenteException(String message)
    {
        super(message);
    }

    public GrafoArcoInexistenteException(Throwable cause)
    {
        super(cause);
    }

    public GrafoArcoInexistenteException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GrafoArcoInexistenteException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
