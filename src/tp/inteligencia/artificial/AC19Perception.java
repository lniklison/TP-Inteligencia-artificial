/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa,
 * Luis Ignacio Larrateguy y Milton Pividori.
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

import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import java.util.ArrayList;

public class AC19Perception extends Perception {
    
    private ArrayList<Integer> posicionesEnfermos;

    public AC19Perception() {
        posicionesEnfermos = new ArrayList<Integer>();
    }

    public AC19Perception(Agent agent, Environment environment) {
        super(agent, environment);
    }

    /**
     * This method is used to setup the perception.
     */
    @Override
    public void initPerception(Agent agent, Environment environment) {
        AndroideC19 robotAgent = (AndroideC19) agent;
        AC19Environment robotEnvironment = (AC19Environment) environment;
        AC19EnvironmentState environmentState =
                robotEnvironment.getEnvironmentState();
        System.out.println("Percibiendo algo");
        environmentState.setAgentPosition(((AndroideC19State)robotAgent.getAgentState()).getPosition());
        this.setPosicionesEnfermos(environmentState.getPosicionesEnfermos());
        
        //ver si agregar calles cortadas aca
    }

    // The following methods are Robot-specific:

   

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        str.append("Enfermos: " + this.posicionesEnfermos.toString());

        return str.toString();
    }

    public void setPosicionesEnfermos(ArrayList<Integer> posicionesEnfermos) {
        this.posicionesEnfermos = posicionesEnfermos;
    }
    
    public ArrayList<Integer> getPosicionesEnfermos() {
        return this.posicionesEnfermos;
    }
}
