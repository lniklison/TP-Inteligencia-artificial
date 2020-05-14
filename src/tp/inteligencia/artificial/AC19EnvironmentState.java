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
    private HashMap<Integer, Collection<Integer>> map;

    public static final Integer[][] POSITIONS = new Integer[][]{
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
    };

    private ArrayList<Integer> posicionesEnfermos;
    public AC19EnvironmentState() {
        map = new HashMap<Integer, Collection<Integer>>();
        posicionesEnfermos = new ArrayList<Integer>();
        this.initState();
    }

    @Override
    public Object clone() {
        AC19EnvironmentState newState = new AC19EnvironmentState();
        newState.setMap((HashMap<Integer, Collection<Integer>>)map.clone());
        newState.setPocicionesEnfermos((ArrayList<Integer>)posicionesEnfermos.clone());
        return newState;
    }

    @Override
    public void initState() {
        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
        map = new HashMap<Integer, Collection<Integer>>();
        
        for (int i = 0; i < POSITIONS.length; i++) {
            ArrayList<Integer> successors = new ArrayList<Integer>();
            for (int j = 1; j < POSITIONS[i].length; j++) {
                successors.add(POSITIONS[i][j]);
            }
            map.put(POSITIONS[i][0], successors);

        }
        
        posicionesEnfermos = new ArrayList<Integer>(){
            {
                add(N7);
                add(N14);
            }
        };
    }

    @Override
    public String toString() {
        String str = "";

        str = str + "[ \n";
        for (Integer point : map.keySet()) {
            str = str + "[ " + point + " --> ";
            Collection<Integer> successors = map.get(point);
            if (successors != null) {
                for (Integer successor : successors) {
                    str = str + successor + " ";
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
    
    public void setMap(HashMap<Integer, Collection<Integer>> map){
        this.map = map;
    }
    
    public void setPocicionesEnfermos(ArrayList<Integer> pociciones){
        this.posicionesEnfermos = pociciones;
    }
    
    public void removePositionEnfermo(Integer position){
        if(this.posicionesEnfermos.contains(position)){
            this.posicionesEnfermos.remove(position);
        }
    }
}
