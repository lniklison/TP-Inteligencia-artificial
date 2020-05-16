package ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones;

public class GrafoVerticeInexistenteException extends Exception
{
    private static final long serialVersionUID = 8099510933867597874L;

    public GrafoVerticeInexistenteException()
    {
        super();
    }

    public GrafoVerticeInexistenteException(String message)
    {
        super(message);
    }

    public GrafoVerticeInexistenteException(Throwable cause)
    {
        super(cause);
    }

    public GrafoVerticeInexistenteException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GrafoVerticeInexistenteException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
