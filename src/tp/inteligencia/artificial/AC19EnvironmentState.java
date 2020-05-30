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

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import frsf.cidisi.faia.state.EnvironmentState;
import java.util.Map;
import java.util.Random;
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
    
    private ArrayList<AC19EnvironmentState> estados = new ArrayList<>();
    
    private double metrosRecorridos;

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
    private Integer position;
    private HashMap<Integer,Integer> cuadrasCortadas;
    private HashMap<Integer,Integer> cuadrasCortadas2;
//    private HashMap<Integer,AC19NodoAlcanzable> cuadrasCortadas;
//    private HashMap<Integer,AC19NodoAlcanzable> cuadrasCortadas2;
    
    public AC19EnvironmentState() {
//        map = new HashMap<Integer, Collection<Integer>>();
//        otroMap = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
//        posicionesEnfermos = new ArrayList<Integer>();
        this.initState();
    }

    AC19EnvironmentState(HashMap<Integer, Collection<AC19NodoAlcanzable>> map, ArrayList<ArrayList<Integer>> positions,ArrayList<Integer> posicionesEnfermosA, HashMap<Integer,Integer> cuadrasCortadas) {
        this.otroMap2 = map;
        this.positions2=positions;
//        this.posicionesEnfermos2 = new ArrayList<Integer>(){
//            {
//                add(N7);
//                add(N8);
//            }
//        };
        this.posicionesEnfermos2 = posicionesEnfermosA;
        this.cuadrasCortadas2 = cuadrasCortadas;
        this.initState();
    }

    @Override
    public Object clone() {
        AC19EnvironmentState newState = new AC19EnvironmentState();
        newState.setMap((HashMap<Integer, Collection<AC19NodoAlcanzable>>)otroMap.clone());
        newState.setPosicionesEnfermos((ArrayList<Integer>)posicionesEnfermos.clone());
        newState.setCuadrasCortadas((HashMap<Integer,Integer>) cuadrasCortadas.clone());
        newState.setMetrosRecorridos(metrosRecorridos);
        System.out.printf("CLONADO AMBIENTE STATE: [");
        System.out.printf("Posicion: "+position);
        System.out.printf("Metros recorridos: "+metrosRecorridos);
        System.out.printf("Posiciones: "+posicionesEnfermos.toString()+"]");
        newState.setAgentPosition(position);        
        return newState;
    }

    @Override
    public void initState() {
        /**
         * In this matrix the first element of each row represents a position
         * in the map and the seccessors of that position.
         */
        metrosRecorridos = 0;
        positions = positions2;
        
        otroMap = otroMap2;
//  
        posicionesEnfermos = posicionesEnfermos2;
        cuadrasCortadas = cuadrasCortadas2;
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
    
    public void actualizarAccionesAgente(Integer position, ArrayList<Integer> posicionesVisitadas, double metrosRecorridos){
        //SETEAR POSICION AGENTE
        this.setAgentPosition(position);
//            System.out.println("AMBIENTE STATE - Posiciones enfermos "+posicionesEnfermos.toString());
        
        //SETEAR METROS RECORRIDOS POR EL AGENTE
        this.setMetrosRecorridos(metrosRecorridos);
        
        //MULTAR ENFERMO SI EL AGENTE LLEGÓ A SU POSICION 
        if(this.posicionesEnfermos.contains(position)){
            System.out.println("AMBIENTE STATE - REMOVIENDO "+position);
            this.posicionesEnfermos.remove(position);
        }
        Random rn = new Random();
        //MOVER ALEATORIAMENTE UN ENFERMO
        for(int i=0; i< posicionesEnfermos.size(); i++){
            Double num = (rn.nextDouble()*100);
            if(num>90){
                Integer nuevaPosicion = this.posicionCercana(posicionesEnfermos.get(i));
                posicionesEnfermos.set(i,nuevaPosicion);
            }
        }
        //GENERAR ALEATORIAMENTE NUEVOS ENFERMOS
        Integer tamanioMapa = otroMap.size();
        
        Double num2 = (rn.nextDouble() *100);
        Double num3 = (rn.nextDouble() *tamanioMapa);
        int nuevaPosicion = num3.intValue();
        if(num2>90 && !posicionesEnfermos.contains(nuevaPosicion) && nuevaPosicion!=1 && posicionesEnfermos.size()<2){
            posicionesEnfermos.add(nuevaPosicion);
        }
        
        //GENERAR ALEATORIAMENTE CORTES DE CALLES
        Double num4 = (rn.nextDouble() *100);
        Double num5 = (rn.nextDouble() *tamanioMapa);
        int nuevaPosicionCuadraCortada = num5.intValue();
        if(num4>90 && !cuadrasCortadas.containsKey(nuevaPosicionCuadraCortada) && cuadrasCortadas.size()<3){
            cuadrasCortadas.put(nuevaPosicionCuadraCortada, calleCercana(nuevaPosicionCuadraCortada));
        }
        
        //aqui se quitan las calles cortadas     
//        Double num6 = (Math.random() *100);
//        Double num7 = (Math.random()*tamanioMapa);
//        int removerPosicionCuadraCortada = num7.intValue();       
//        
//        cuadrasCortadas.remove(removerPosicionCuadraCortada);

        
        
        //--------------------------------------------------------------
    }

    public ArrayList<ArrayList<Integer>> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<ArrayList<Integer>> positions) {
        this.positions = positions;
    }

    void setAgentPosition(Integer position) {
        this.position=position;
        System.out.println("Agente en la posicion: "+position);
    }
    
    public Integer getPosition(){
        return this.position;
    }
    
    private Integer posicionCercana(Integer posicion){
        
        Integer cantidadDeAdyacentes = otroMap.get(posicion).size();
        Random rn = new Random();
        Collection<AC19NodoAlcanzable> coleccion = otroMap.get(posicion);
        Double indice = (rn.nextDouble() *cantidadDeAdyacentes);
        int i =0;
        for(AC19NodoAlcanzable nodo : coleccion){
            if(i==indice.intValue()){
                if((nodo.getNodo()!=position)&&(!posicionesEnfermos.contains(nodo.getNodo()))){
                     return nodo.getNodo();
                 } 
            }
            i++;
        }
        return posicion;
        
    }
    
    private Integer calleCercana(Integer posicion){
        
        Integer cantidadDeAdyacentes = otroMap.get(posicion).size();
        Random rn = new Random();
        Collection<AC19NodoAlcanzable> coleccion = otroMap.get(posicion);
        Double indice = (rn.nextDouble()*cantidadDeAdyacentes);
        int i =0;
        for(AC19NodoAlcanzable nodo : coleccion){
            if(i==indice.intValue()){
                if((nodo.getNodo()!=position)&&(!cuadrasCortadas.containsKey(nodo.getNodo()))){
                     return nodo.getNodo();
                 } 
            }
            i++;
        }
        return posicion;
        
    }
    
    public ArrayList<AC19EnvironmentState> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<AC19EnvironmentState> estados) {
        this.estados = estados;
    }

    public HashMap<Integer, Integer> getCuadrasCortadas() {
        return cuadrasCortadas;
    }

    public void setCuadrasCortadas(HashMap<Integer, Integer> cuadrasCortadas) {
        this.cuadrasCortadas = cuadrasCortadas;
    }
    
    public void setMetrosRecorridos(double m){
        this.metrosRecorridos = m;
    }
    
    public double getMetrosRecorridos(){
        return this.metrosRecorridos;
    }
}
