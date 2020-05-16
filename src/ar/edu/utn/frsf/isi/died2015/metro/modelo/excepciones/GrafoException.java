package ar.edu.utn.frsf.isi.died2015.metro.modelo.excepciones;

public class GrafoException extends Exception
{

    private static final long serialVersionUID = 8099510933867597874L;

    public GrafoException()
    {
        super();
    }

    public GrafoException(String message)
    {
        super(message);
    }

    public GrafoException(Throwable cause)
    {
        super(cause);
    }

    public GrafoException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GrafoException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
