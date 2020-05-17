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
package tp.inteligencia.artificial.actions;

import tp.inteligencia.artificial.AndroideC19State;
import java.util.ArrayList;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import tp.inteligencia.artificial.AC19EnvironmentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import java.util.Arrays;
import java.util.Iterator;
import tp.inteligencia.artificial.model.AC19NodoAlcanzable;

public class GoX extends SearchAction {
    private Integer nodo = 0;
    
    public GoX(Integer x){
        this.nodo = x;
    }

    /**
     * This method updates a tree node state when the search process is running.
     * It does not updates the real world state.
     */
    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        AndroideC19State agentState = (AndroideC19State) s;
//        System.out.println("Ejecutando arbol... en Nodo: "+this.nodo);
        if (agentState.getVisitedPositions().contains(this.nodo)) {
            return null;
        }
        ArrayList<AC19NodoAlcanzable> successors = new ArrayList<AC19NodoAlcanzable>();
        successors.addAll(agentState.getSuccessors());
//        System.out.println("Sucesores de "+this.nodo+" "+successors.toString());
        if (successors != null) {
            int index = -1;
            for (Iterator<AC19NodoAlcanzable> it = successors.iterator(); it.hasNext();) {
                AC19NodoAlcanzable n = it.next();
                if(n.getNodo()==this.nodo){
                    index = 1;
                }
            }
//            int index = successors.indexOf(this.nodo);
            if (index >= 0) {
//                System.out.printf("Nodo "+ agentState.getPosition() +" => [ ");
//                    System.out.printf("["+this.nodo+"]");
//                System.out.printf("]\n");
                agentState.setPosition(this.nodo);
                agentState.removePositionEnfermo(this.nodo);
                return agentState;
            }

        }

        return null;
    }

    /**
     * This method updates the agent state and the real world state.
     */
    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        System.out.println("SE EJECUTA ESTO EN ALGUN MOMENTO??????");
        AC19EnvironmentState res = (AC19EnvironmentState) est;
                
        res.removePositionEnfermo(this.nodo);
        
        this.execute((SearchBasedAgentState) ast);
        
        return res;
    }

    @Override
    public Double getCost() {
        return new Double(0);
    }

    @Override
    public String toString() {
        return "Go"+this.nodo;
    }
}
