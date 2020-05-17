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

import frsf.cidisi.faia.state.EnvironmentState;
import java.util.Map;
import tp.inteligencia.artificial.model.AC19NodoAlcanzable;

    public class AC19EnvironmentState extends EnvironmentState {

    public static final Integer N1  = 1;
    public static final Integer N2  = 2;
    public static final Integer N3  = 3;
    public static final Integer N4  = 4;
    public static final Integer N5  = 5;
    public static final Integer N6  = 6;
    public static final Integer N7  = 7;
    public static final Integer N8  = 8;
    public static final Integer N9  = 9;
    public static final Integer N10 = 10;
    public static final Integer N11 = 11;
    public static final Integer N12 = 12;
    public static final Integer N13 = 13;
    public static final Integer N14 = 14;
    public static final Integer N15 = 15;
    public static final Integer N16 = 16;
    public static final Integer N17 = 17;

    /**
     * This map has a point of the world (A, B, C, ...) as key, and a collection
     * of successors of that point.
     */
    //private HashMap<Integer, Collection<Integer>> map;
    private HashMap<Integer, Collection<AC19NodoAlcanzable>> otroMap;
    private HashMap<Integer, Collection<AC19NodoAlcanzable>> otroMap2;

    private ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> positions2= new ArrayList<ArrayList<Integer>>();
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
    
    private ArrayList<Integer> posicionesEnfermos;
    private ArrayList<Integer> posicionesEnfermos2;
    public AC19EnvironmentState() {
//        map = new HashMap<Integer, Collection<Integer>>();
//        otroMap = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
//        posicionesEnfermos = new ArrayList<Integer>();
        this.initState();
    }

    AC19EnvironmentState(HashMap<Integer, Collection<AC19NodoAlcanzable>> map, ArrayList<ArrayList<Integer>> positions,ArrayList<Integer> posicionesEnfermosA) {
        this.otroMap2 = map;
        this.positions2=positions;
//        this.posicionesEnfermos2 = new ArrayList<Integer>(){
//            {
//                add(N7);
//                add(N8);
//            }
//        };
        this.posicionesEnfermos2 = posicionesEnfermosA;
        this.initState();
    }

    @Override
    public Object clone() {
        AC19EnvironmentState newState = new AC19EnvironmentState();
        newState.setMap((HashMap<Integer, Collection<AC19NodoAlcanzable>>)otroMap.clone());
        newState.setPosicionesEnfermos((ArrayList<Integer>)posicionesEnfermos.clone());
        return newState;
    }

    @Override
    public void initState() {
        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
        /*
        {N1, N3, N7},
        {N2, N10, N11, N15},
        {N3, N4, N7},
        {N4, N3, N5},
        {N5, N6, N8, N9, N4},
        {N6, N5, N8, N7, N17},
        {N7, N3, N6, N17},
        {N8, N5, N6, N9, N10},
        {N9, N5, N8, N10, N12},
        {N10, N2, N8, N9, N11},
        {N11, N10, N14, N12},
        {N12, N9, N11, N13},
        {N13, N12, N14},
        {N14, N11, N13},
        {N15, N2, N16},
        {N16, N15, N17},
        {N17, N2, N6, N7, N16}
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
        
        //map = new HashMap<Integer, Collection<Integer>>();
//        otroMap = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
        otroMap = otroMap2;
        
//        for (int i = 0; i < positions.size(); i++) {
////            ArrayList<Integer> successors = new ArrayList<Integer>();
//            ArrayList<AC19NodoAlcanzable> otrosSuccessors = new ArrayList<AC19NodoAlcanzable>();
//            
//            for (int j = 1; j < positions.get(i).size(); j++) {
//                int nodo = positions.get(i).get(j);
////                successors.add(nodo);
//                otrosSuccessors.add(new AC19NodoAlcanzable(nodo, "CalleHacia"+nodo));
//            }
//            //map.put(positions[i][0], successors);
//            otroMap.put(positions.get(i).get(0), otrosSuccessors);
//
//        }
        
//        posicionesEnfermos = new ArrayList<Integer>(){
//            {
//                add(N7);
//                add(N14);
//            }
//        };
        posicionesEnfermos = posicionesEnfermos2;
    }

    @Override
    public String toString() {
        String str = "";

        str = str + "[ \n";
        for (Integer point : otroMap.keySet()) {
            str = str + "[ " + point + " --> ";
            Collection<AC19NodoAlcanzable> successors = otroMap.get(point);
            if (successors != null) {
                for (AC19NodoAlcanzable successor : successors) {
                    str = str + successor.toString() + " ";
                }
            }
            str = str + " ]\n";
        }
        str = str + " ]";

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        // Returns always true. This method is not used.
        return true;
    }
    
    public ArrayList<Integer> getPosicionesEnfermos(){
        return this.posicionesEnfermos;
    }
    
    public void setMap(HashMap<Integer, Collection<AC19NodoAlcanzable>> map){
        this.otroMap = map;
    }
    public HashMap<Integer, Collection<AC19NodoAlcanzable>> getMap(){
        return this.otroMap;
    }
    
    public void setPosicionesEnfermos(ArrayList<Integer> posiciones){
        this.posicionesEnfermos = posiciones;
    }
    
    public void removePositionEnfermo(Integer position){
        if(this.posicionesEnfermos.contains(position)){
            this.posicionesEnfermos.remove(position);
        }
    }

    public ArrayList<ArrayList<Integer>> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<ArrayList<Integer>> positions) {
        this.positions = positions;
    }
}
