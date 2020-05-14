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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;

public class AndroideC19State extends SearchBasedAgentState {

    public static final Integer A = 1;
    public static final Integer B = 2;
    public static final Integer C = 3;
    public static final Integer D = 4;
    public static final Integer E = 5;
    public static final Integer F = 6;
    public static final Integer G = 7;
    public static final Integer H = 8;
    public static final Integer I = 9;
    public static final Integer J = 10;
    public static final Integer K = 11;
    public static final Integer L = 12;
    public static final Integer M = 13;
    public static final Integer N = 14;
    public static final Integer O = 15;
    public static final Integer P = 16;
    public static final Integer Q = 17;
    
    private ArrayList<Integer> pocicionesEnfermos;

    /**
     * Actual agent position
     */
    Integer position;

    /**
     * This map has a point of the world (A, B, C, ...) as key, and a collection
     * of successors of that point.
     */
    private HashMap<Integer, Collection<Integer>> knownMap;
    private ArrayList<Integer> visitedPositions;

    public AndroideC19State() {
        this.initState();
    }

    @Override
    public AndroideC19State clone() {
        AndroideC19State newState = new AndroideC19State();
        newState.setPosition(position);
        ArrayList<Integer> visitedPosition = (ArrayList<Integer>) visitedPositions.clone();
        newState.setVisitedPositions(visitedPosition);
        ArrayList<Integer> positions = (ArrayList<Integer>) pocicionesEnfermos.clone();
        newState.setPocicionesEnfermos(positions);
        return newState;
    }

    @Override
    public void initState() {
        position = A;

        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
        Integer[][] positions = new Integer[][]{
            {A, C, G},
            {B, J, K, O},
            {C, D, G},
            {D, C, E},
            {E, F, H, I, D},
            {F, E, H, G, Q},
            {G, C, F, Q},
            {H, E, F, I, J},
            {I, E, H, J, L},
            {J, B, H, I, K},
            {K, J, N, L},
            {L, I, K, M},
            {M, L, N},
            {N, K, M},
            {O, B, P},
            {P, O, Q},
            {Q, B, F, G, P}
        };

        knownMap = new HashMap<Integer, Collection<Integer>>();
        for (int i = 0; i < positions.length; i++) {
            ArrayList<Integer> successors = new ArrayList<Integer>();
            for (int j = 1; j < positions[i].length; j++) {
                successors.add(positions[i][j]);
            }
            knownMap.put(positions[i][0], successors);

        }

        visitedPositions = new ArrayList<Integer>();
        pocicionesEnfermos = new ArrayList<Integer>();

    }

    @Override
    public void updateState(Perception p) {
        AC19Perception rp = (AC19Perception) p;
        visitedPositions.add(position);
        this.pocicionesEnfermos = rp.getPocicionesEnfermos();
        System.out.println("Posicion actual: "+position);
        System.out.println("Posiciones por visitar: "+pocicionesEnfermos.toString());
        System.out.println("Posiciones visitadas: "+visitedPositions.toString());
//        this.removePositionToVisit(position);
    }

    @Override
    public String toString() {
        String str = "Posicion: " + position;

        return str;

    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof AndroideC19State)) {
            return false;
        }
        return position.equals(((AndroideC19State) obj).getPosition());
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Collection<Integer> getSuccessors() {
        return knownMap.get(position);
    }

    public ArrayList<Integer> getVisitedPositions() {
        return visitedPositions;
    }

    public void setVisitedPositions(ArrayList<Integer> visitedPositions) {
        this.visitedPositions = visitedPositions;
    }
    
    public ArrayList<Integer> getPocicionesEnfermos(){
        return this.pocicionesEnfermos;
    }
    
    public void setPocicionesEnfermos(ArrayList<Integer> positions){
        this.pocicionesEnfermos = positions;
    }
    
    public void removePositionEnfermo(Integer position){
        if(this.pocicionesEnfermos.contains(position)){
            this.pocicionesEnfermos.remove(position);
        }
    }
}
