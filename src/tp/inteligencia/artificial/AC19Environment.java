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

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import java.util.ArrayList;

public class AC19Environment extends Environment {

    public AC19Environment() {
        // Create the environment state
        this.environmentState = new AC19EnvironmentState();
    }
   
    @Override
    public Perception getPercept() {
        
        AC19Perception robotPerception = new AC19Perception();
        
        ArrayList<Integer> posicionesEnfermos = this.getEnvironmentState().getPosicionesEnfermos();
        
        robotPerception.setPocicionesEnfermos(posicionesEnfermos);
        
        return robotPerception;
    }

    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public AC19EnvironmentState getEnvironmentState() {
        return (AC19EnvironmentState) super.getEnvironmentState();
    }
}
