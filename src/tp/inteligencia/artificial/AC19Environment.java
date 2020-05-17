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

import ar.edu.utn.frsf.isi.died2015.metro.modelo.Calle;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Cuadra;
import ar.edu.utn.frsf.isi.died2015.metro.modelo.Nodo;
import ar.edu.utn.frsf.isi.died2015.metro.vistas.paneles.PanelGrafo;
import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import tp.inteligencia.artificial.model.AC19NodoAlcanzable;

public class AC19Environment extends Environment {
    
    private HashMap<Integer, Collection<AC19NodoAlcanzable>> map;
    private ArrayList<ArrayList<Integer>> positions;
    private PanelGrafo panelGrafo;
    private ArrayList<Integer> posicionesEnfermos;
    
    public AC19Environment() {
        // Create the environment state
        this.environmentState = new AC19EnvironmentState();
    }

    public AC19Environment(Iterable<Nodo> nodos, PanelGrafo panelGrafo) {
        
        this.map = new HashMap<Integer, Collection<AC19NodoAlcanzable>>();
        this.positions = new ArrayList<ArrayList<Integer>>();
        this.panelGrafo = panelGrafo;
        this.posicionesEnfermos = new ArrayList<Integer>();
        
        for(Nodo n : nodos){
            ArrayList<AC19NodoAlcanzable> successors = new ArrayList<AC19NodoAlcanzable>();
            ArrayList<Integer> po = new ArrayList<Integer>();
            int origen = Integer.valueOf(n.getId());
            po.add(origen);
            for(Cuadra c : n.getCuadras()){
                int nodo = Integer.valueOf(c.getDestino().getId());
                successors.add(new AC19NodoAlcanzable(nodo, "CalleHacia"+nodo));
                po.add(nodo);
            }
            map.put(origen, successors);
            if(posicionesEnfermos.size()<3){
                
                Double num = (Math.random()*11);
                if(num%5==0){
                    posicionesEnfermos.add(Integer.parseInt(n.getId()));
                }
            }
            positions.add(po);
            
        }
        
        this.environmentState = new AC19EnvironmentState(map, positions,posicionesEnfermos);
    }

   
    @Override
    public Perception getPercept() {
        
        AC19Perception robotPerception = new AC19Perception();
        
        ArrayList<Integer> posicionesEnfermos = this.getEnvironmentState().getPosicionesEnfermos();
        
        robotPerception.setPosicionesEnfermos(posicionesEnfermos);
        
        this.panelGrafo.dibujarAgente(this.getEnvironmentState().getPosition());
        
        
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

    public ArrayList<Integer> getPosicionesEnfermos() {
        return posicionesEnfermos;
    }
}
