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
package frsf.cidisi.faia.examples.search.robot;

import frsf.cidisi.faia.agent.search.GoalTest;
import frsf.cidisi.faia.state.AgentState;
import java.util.ArrayList;
import javax.swing.text.Position;

public class RobotGoal extends GoalTest {

    @Override
    public boolean isGoalState(AgentState agentState) {
        // Agent reach the goal if it is in B
        Integer posicionActual = ((RobotAgentState) agentState).getPosition();
//        ((RobotAgentState) agentState).removePositionToVisit(posicionActual);
        
        ArrayList<Integer> visitedPositions = ((RobotAgentState) agentState).getVisitedPositions();
        ArrayList<Integer> positionsToVisit = ((RobotAgentState) agentState).getPocicionesEnfermos();
        
//        System.out.println("#######################################");
//        System.out.println("Posicion actual: "+posicionActual);
//        System.out.println("Posiciones visitadas: "+visitedPositions.toString());
//        System.out.println("Posiciones por visitar: "+positionsToVisit.toString());
        
        if (posicionActual.equals(RobotAgentState.B) && positionsToVisit.size()==0) {
//            System.out.println("Posicion actual: "+posicionActual);
//            System.out.println("Posiciones visitadas: "+visitedPositions.toString());
            return true;
        }
        return false;
        
//        return visitedPositions.containsAll(positionsToVisit);
        
    }
}
