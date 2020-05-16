package ar.edu.utn.frsf.isi.died2015.metro.control.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que parsea un archivo csv.
 * 
 * @author Guimar Nicolás Javier, Madoery Federico y Raimondi Gino.
 * @version 1.0
 */
public class ParseadorCSV
{
    private ArrayList<String[]> datos;

    public ParseadorCSV(File archivo)
    {
        if(archivo == null)
            throw new NullPointerException("El archivo no puede ser nulo.");
        
        if(!archivo.getName().toLowerCase().endsWith(".csv"))
            throw new IllegalArgumentException("El archivo debe tener extensión csv.");

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            this.datos = new ArrayList<String[]>();
            
            String linea;
            
            while((linea = reader.readLine()) != null)
                this.datos.add(linea.split(","));
            
            reader.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getDatos()
    {
        return datos;
    }

}
