package Behaviours;

import java.util.ArrayList;

import Agents.TrafficLightAgent;
import jade.core.behaviours.Behaviour;

public class IntersectionBehaviour extends Behaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> states;
	TrafficLightAgent agent;
	ArrayList<Integer> times;
	
	public IntersectionBehaviour(TrafficLightAgent ag, ArrayList<Integer> timeStates,ArrayList<String> st) {
		this.states = st;
		this.agent = ag;
		this.times = timeStates;
	}
	
	@Override
	public void action() {
	
		
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
