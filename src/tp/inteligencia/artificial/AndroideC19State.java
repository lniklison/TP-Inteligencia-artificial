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
import java.util.List;
import java.util.Collection;
import java.util.HashMap;

import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import java.util.List;
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
    
    private HashMap<Integer, Integer> cuadrasCortadas = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> cuadrasCortadas2 = new HashMap<Integer, Integer>();
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
    private double metrosRecorridos;
    private double metrosUltimaCuadra;

    public AndroideC19State() {
        this.initState();
    }
    
    AndroideC19State(ArrayList<ArrayList<Integer>> positions, HashMap<Integer, Collection<AC19NodoAlcanzable>> map,ArrayList<Integer> posicionesEnfermosA,HashMap<Integer,Integer> cuadrasCortadas) {
        positions2 = positions;
        position = N1;
        knownMap2 = map;
        posicionesEnfermos2 = posicionesEnfermosA;
        cuadrasCortadas2 = cuadrasCortadas;
        
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
        newState.setCuadrasCortadas((HashMap<Integer,Integer>) cuadrasCortadas.clone());
        newState.setMetros(metrosRecorridos);
        newState.setMetrosUltimaCuadra(metrosUltimaCuadra);
        return newState;
    }

    @Override
    public void initState() {
//        position = N1;

        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
//        p0.add(N1);p0.add(N3);p0.add(N7);
//        p1.add(N2);p1.add(N10);p1.add(N11);p1.add(N15);
//        ....
//        positions.add(p0);
//        positions.add(p1);
//        ...
        
        positions = positions2;
        knownMap = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
        knownMap = knownMap2;
        
        metrosRecorridos = 0;
        metrosUltimaCuadra = 0;
        
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
        cuadrasCortadas = new HashMap<Integer, Integer>();
        cuadrasCortadas = cuadrasCortadas2;

    }

    @Override
    public void updateState(Perception p) {
        AC19Perception rp = (AC19Perception) p;
        visitedPositions.add(position);
        this.posicionesEnfermos = rp.getPosicionesEnfermos();
        System.out.printf("UPDATE ANDROIDE STATE: [");
        System.out.printf("Posicion actual: "+position);
        System.out.printf(" - Posiciones visitadas: "+visitedPositions.toString()+"]\n");
//        this.removePositionToVisit(position);
    }

    @Override
    public String toString() {
        String str = "Posicion:" + position 
                +"\n Enfermos:"+posicionesEnfermos.size()
                +"\n KM:"+metrosRecorridos/1000;
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
    
    public void sumarMetros(double m){
        this.metrosRecorridos += m;
        this.metrosUltimaCuadra = m;
    }
    public void setMetros(double m){
        this.metrosRecorridos = m;
    }
    public double getMetrosRecorridos(){
        return this.metrosRecorridos;
    }
    public void setMetrosUltimaCuadra(double m){
        this.metrosUltimaCuadra = m;
    }
    public double getMetrosUltimaCuadra(){
        return this.metrosUltimaCuadra;
    }
    
    public Collection<AC19NodoAlcanzable> getSuccessors() {
        
        if(cuadrasCortadas.containsKey(position)){
            Integer sgte = cuadrasCortadas.get(position);
            Collection<AC19NodoAlcanzable> coleccionNodos = knownMap.get(position);
            ArrayList<AC19NodoAlcanzable> coleccionNodosADevolver = new ArrayList<AC19NodoAlcanzable>();
//            System.out.printf("posicion: "+ position +"-");
//            System.out.printf("NODOS ALCANZABLES :[");
            for(AC19NodoAlcanzable n : coleccionNodos ){
                if(!n.getNodo().equals(sgte)){
//                    System.out.printf("Nodo{ nodo:" + n.getNodo() + ", calle:"+ n.getCalle() +"}");
                    coleccionNodosADevolver.add(n);
                }
//                else
//                    System.out.printf("NodoNoAgregado{ nodo:" + n.getNodo() + ", calle:"+ n.getCalle() +"}");
            }
//            System.out.printf("]/n");
            return coleccionNodosADevolver;


        }
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
            this.posicionesEnfermos.remove(position);
        }
    }

    public HashMap<Integer,Integer> getCuadrasCortadas() {
        return cuadrasCortadas;
    }

    public void setCuadrasCortadas(HashMap<Integer, Integer> cuadrasCortadas) {
        this.cuadrasCortadas = cuadrasCortadas;
    }
    
}
