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

import tp.inteligencia.artificial.actions.GoX;
import java.util.Vector;

import frsf.cidisi.faia.agent.search.Problem;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgent;
import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.solver.search.BreathFirstSearch;
import frsf.cidisi.faia.solver.search.DepthFirstSearch;
import frsf.cidisi.faia.solver.search.Search;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp.inteligencia.artificial.model.AC19NodoAlcanzable;

public class AndroideC19 extends SearchBasedAgent {

    public AndroideC19(ArrayList<ArrayList<Integer>> positions, HashMap<Integer, Collection<AC19NodoAlcanzable>> map) {
        // Robot agent goal
        AC19Goal goal = new AC19Goal();

        // Robot agent actions
        Vector<SearchAction> actions = new Vector<SearchAction>();
        
        for(int i = 0; i<positions.size(); i++){
            actions.addElement(new GoX(positions.get(i).get(0)));
        }
        System.out.println("Acciones: "+actions.toString());

        // Robot agent state
        AndroideC19State agentState = new AndroideC19State(positions, map);
        this.setAgentState(agentState);
        
        // Robot agent problem
        Problem problem = new Problem(goal, agentState, actions);
        this.setProblem(problem);
    }


    @Override
    public Action selectAction() {
        // Breath first strategy
        BreathFirstSearch searchStrategy = new BreathFirstSearch();
//        DepthFirstSearch searchStrategy = new DepthFirstSearch();

        Search searchSolver = new Search(searchStrategy);

        // Set the search tree to be written in an XML file
        searchSolver.setVisibleTree(Search.GRAPHVIZ_TREE);

        // Set the search solver
        this.setSolver(searchSolver);

        // Run the actions selection process
        Action selectedAction = null;
        try {
            selectedAction = this.getSolver().solve(new Object[]{this.getProblem()});
        } catch (Exception ex) {
            Logger.getLogger(AndroideC19.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Return the selected action
        return selectedAction;
    }

    @Override
    public void see(Perception perception) {
        System.out.println("AGENTE - VIENDO PERCEPCION: "+perception.toString());
        this.getAgentState().updateState(perception);
    }
}
