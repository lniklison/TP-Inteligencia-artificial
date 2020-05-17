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
import tp.inteligencia.artificial.model.AC19NodoAlcanzable;

public class AndroideC19State extends SearchBasedAgentState {

    public static final Integer N1 = 1;
    public static final Integer N2 = 2;
    public static final Integer N3 = 3;
    public static final Integer N4 = 4;
    public static final Integer N5 = 5;
    public static final Integer N6 = 6;
    public static final Integer N7 = 7;
    public static final Integer N8 = 8;
    public static final Integer N9 = 9;
    public static final Integer N10 = 10;
    public static final Integer N11 = 11;
    public static final Integer N12 = 12;
    public static final Integer N13 = 13;
    public static final Integer N14 = 14;
    public static final Integer N15 = 15;
    public static final Integer N16 = 16;
    public static final Integer N17 = 17;
    
    private ArrayList<Integer> posicionesEnfermos;
    private ArrayList<Integer> posicionesEnfermos2;
    private ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> positions2 = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> p0 = new ArrayList<Integer>(); 
    private ArrayList<Integer> p1 = new ArrayList<Integer>();
    private ArrayList<Integer> p2 = new ArrayList<Integer>();
    private ArrayList<Integer> p3 = new ArrayList<Integer>();
    private ArrayList<Integer> p4 = new ArrayList<Integer>();
    private ArrayList<Integer> p5 = new ArrayList<Integer>();
    private ArrayList<Integer> p6 = new ArrayList<Integer>();
    private ArrayList<Integer> p7 = new ArrayList<Integer>();
    private ArrayList<Integer> p8 = new ArrayList<Integer>();
    private ArrayList<Integer> p9 = new ArrayList<Integer>();
    private ArrayList<Integer> p10 = new ArrayList<Integer>();
    private ArrayList<Integer> p11 = new ArrayList<Integer>();
    private ArrayList<Integer> p12 = new ArrayList<Integer>();
    private ArrayList<Integer> p13 = new ArrayList<Integer>();
    private ArrayList<Integer> p14 = new ArrayList<Integer>();
    private ArrayList<Integer> p15 = new ArrayList<Integer>();
    private ArrayList<Integer> p16 = new ArrayList<Integer>();
    /**
     * Actual agent position
     */
    Integer position;
    Integer position2;

    /**
     * This map has a point of the world (A, B, C, ...) as key, and a collection
     * of successors of that point.
     */
    private HashMap<Integer, Collection<AC19NodoAlcanzable>> knownMap;
    private HashMap<Integer, Collection<AC19NodoAlcanzable>> knownMap2;
    private ArrayList<Integer> visitedPositions;

    public AndroideC19State() {
        this.initState();
    }
    
    AndroideC19State(ArrayList<ArrayList<Integer>> positions, HashMap<Integer, Collection<AC19NodoAlcanzable>> map,ArrayList<Integer> posicionesEnfermosA) {
        positions2 = positions;
        position2 = N1;
        knownMap2 = map;
        posicionesEnfermos2 = posicionesEnfermosA;
        
        this.initState();
    }

    @Override
    public AndroideC19State clone() {
        AndroideC19State newState = new AndroideC19State();
        newState.setPosition(position);
        ArrayList<Integer> visitedPosition = (ArrayList<Integer>) visitedPositions.clone();
        newState.setVisitedPositions(visitedPosition);
        ArrayList<Integer> positions = (ArrayList<Integer>) posicionesEnfermos.clone();
        newState.setSuccessors((HashMap<Integer, Collection<AC19NodoAlcanzable>>) knownMap.clone());
        newState.setPosicionesEnfermos(positions);
        return newState;
    }

    @Override
    public void initState() {
        position = N1;

        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
//        p0.add(N1);p0.add(N3);p0.add(N7);
//        p1.add(N2);p1.add(N10);p1.add(N11);p1.add(N15);
//        p2.add(N3);p2.add(N4);p2.add(N7);
//        p3.add(N4);p3.add(N3);p3.add(N5);
//        p4.add(N5);p4.add(N6);p4.add(N8);p4.add(N9);p4.add(N4);
//        p5.add(N6);p5.add(N5);p5.add(N8);p5.add(N7);p5.add(N17);
//        p6.add(N7);p6.add(N3);p6.add(N6);p6.add(N17);
//        p7.add(N8);p7.add(N5);p7.add(N6);p7.add(N9);p7.add(N10);
//        p8.add(N9);p8.add(N5);p8.add(N8);p8.add(N10);p8.add(N12);
//        p9.add(N10);p9.add(N2);p9.add(N8);p9.add(N9);p9.add(N11);
//        p10.add(N11);p10.add(N10);p10.add(N14);p10.add(N12);
//        p11.add(N12);p11.add(N9);p11.add(N11);p11.add(N13);
//        p12.add(N13);p12.add(N12);p12.add(N14);
//        p13.add(N14);p13.add(N11);p13.add(N13);
//        p14.add(N15);p14.add(N2);p14.add(N16);
//        p15.add(N16);p15.add(N15);p15.add(N17);
//        p16.add(N17);p16.add(N2);p16.add(N6);p16.add(N7);p16.add(N16);
//        
//        positions.add(p0);
//        positions.add(p1);
//        positions.add(p2);
//        positions.add(p3);
//        positions.add(p4);
//        positions.add(p5);
//        positions.add(p6);
//        positions.add(p7);
//        positions.add(p8);
//        positions.add(p9);
//        positions.add(p10);
//        positions.add(p11);
//        positions.add(p12);
//        positions.add(p13);
//        positions.add(p14);
//        positions.add(p15);
//        positions.add(p16);
        
        positions = positions2;
        knownMap = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
        knownMap = knownMap2;
        
//        for (int i = 0; i < positions.size(); i++) {
//            ArrayList<AC19NodoAlcanzable> successors = new ArrayList<AC19NodoAlcanzable>();
//            for (int j = 1; j < positions.get(i).size(); j++) {
//                int nodo = positions.get(i).get(j);
//                successors.add(new AC19NodoAlcanzable(nodo, "CalleHacia"+nodo));
//            }
//            knownMap.put(positions.get(i).get(0), successors);
//
//        }

        visitedPositions = new ArrayList<Integer>();
        posicionesEnfermos = new ArrayList<Integer>();
        posicionesEnfermos = posicionesEnfermos2;

    }

    @Override
    public void updateState(Perception p) {
        AC19Perception rp = (AC19Perception) p;
        visitedPositions.add(position);
        this.posicionesEnfermos = rp.getPosicionesEnfermos();
        System.out.printf("UPDATE ANDROIDE STATE: [");
        System.out.printf("Posicion actual: "+position);
        System.out.printf(" - Posiciones por visitar: "+posicionesEnfermos.toString());
        System.out.printf(" - Posiciones visitadas: "+visitedPositions.toString()+"]");
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

    public Collection<AC19NodoAlcanzable> getSuccessors() {
        return knownMap.get(position);
    }
    public void setSuccessors( HashMap<Integer, Collection<AC19NodoAlcanzable>> map) {
        this.knownMap = map;
    }

    public ArrayList<Integer> getVisitedPositions() {
        return visitedPositions;
    }

    public void setVisitedPositions(ArrayList<Integer> visitedPositions) {
        this.visitedPositions = visitedPositions;
    }
    
    public ArrayList<Integer> getPosicionesEnfermos(){
        return this.posicionesEnfermos;
    }
    
    public void setPosicionesEnfermos(ArrayList<Integer> positions){
        this.posicionesEnfermos = positions;
    }
    
    public void removePositionEnfermo(Integer position){
        if(this.posicionesEnfermos.contains(position)){
//            System.out.println("AGENTE STATE - REMOVIENDO "+position);
            this.posicionesEnfermos.remove(position);
        }
    }
}
