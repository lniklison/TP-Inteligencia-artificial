/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa,
 * Luis Ignacio Larrateguy y Milton Pividori.
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

import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import java.util.ArrayList;

public class RobotPerception extends Perception {

    public static int UNKNOWN_PERCEPTION = -1;
    public static int EMPTY_PERCEPTION = 0;
    public static int ENEMY_PERCEPTION = 1;
    public static int FOOD_PERCEPTION = 2;

    private int leftSensor;
    private int topSensor;
    private int rightSensor;
    private int bottomSensor;
    private int energy;
    
    private ArrayList<Integer> posicionesEnfermos;

    public RobotPerception() {
        energy = 50;
        posicionesEnfermos = new ArrayList<Integer>();
    }

    public RobotPerception(Agent agent, Environment environment) {
        super(agent, environment);
    }

    /**
     * This method is used to setup the perception.
     */
    @Override
    public void initPerception(Agent agent, Environment environment) {
        RobotAgent RobotAgent = (RobotAgent) agent;
        RobotEnvironment robotEnvironment = (RobotEnvironment) environment;
        RobotEnvironmentState environmentState =
                robotEnvironment.getEnvironmentState();

//        int row = environmentState.getAgentPosition()[0];
//        int col = environmentState.getAgentPosition()[1];
//
//        this.setTopSensor(robotEnvironment.getTopCell(row, col));
//        this.setLeftSensor(robotEnvironment.getLeftCell(row, col));
//        this.setRightSensor(robotEnvironment.getRightCell(row, col));
//        this.setBottomSensor(robotEnvironment.getBottomCell(row, col));
    }

    // The following methods are Robot-specific:

    public int getLeftSensor() {
        return leftSensor;
    }

    public void setLeftSensor(int leftSensor) {
        this.leftSensor = leftSensor;
    }

    public int getTopSensor() {
        return topSensor;
    }

    public void setTopSensor(int topSensor) {
        this.topSensor = topSensor;
    }

    public int getRightSensor() {
        return rightSensor;
    }

    public void setRightSensor(int rightSensor) {
        this.rightSensor = rightSensor;
    }

    public int getBottomSensor() {
        return bottomSensor;
    }

    public void setBottomSensor(int bottomSensor) {
        this.bottomSensor = bottomSensor;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        str.append("Energy: " + this.energy);
        str.append("; ");
        str.append("Enfermos: " + this.posicionesEnfermos.toString());

        return str.toString();
    }

    public void setPocicionesEnfermos(ArrayList<Integer> posicionesEnfermos) {
        this.posicionesEnfermos = posicionesEnfermos;
    }
    
    public ArrayList<Integer> getPocicionesEnfermos() {
        return this.posicionesEnfermos;
    }
}
