/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa
 * y Milton Pividori.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tp.inteligencia.artificial;

import ar.edu.utn.frsf.isi.died2015.metro.control.GestorMetro;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelGrafo;
import frsf.cidisi.faia.exceptions.PrologConnectorException;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;
import javax.swing.SwingWorker;

public class AndroideC19Main extends Thread{

    private SearchBasedAgentSimulator simulator;
    public AndroideC19Main(PanelGrafo panel){

        AC19Environment environment = new AC19Environment(GestorMetro.getInstancia().getNodos(), panel);

            AndroideC19 agent = new AndroideC19(environment.getEnvironmentState().getPositions(),
                environment.getEnvironmentState().getMap(),environment.getEnvironmentState().getPosicionesEnfermos(),environment.getEnvironmentState().getCuadrasCortadas());

            simulator =
                new SearchBasedAgentSimulator(environment, agent);
        
            simulator.start();
    }


}
