package Agents;

import java.util.ArrayList;

import jade.core.Agent;
import trasmapi.genAPI.TrafficLight;
import trasmapi.genAPI.exceptions.UnimplementedMethod;
import trasmapi.sumo.Sumo;
import trasmapi.sumo.SumoTrafficLight;
import trasmapi.sumo.protocol.Constants;

public class TrafficLightAgent extends Agent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private Sumo sumo;
	private ArrayList<String> neighbours;
	private SumoTrafficLight stf;
	public TrafficLightAgent(Sumo sumo, String id, ArrayList<String> neighbour) {
		this.identifier = id;
		this.sumo = sumo;
		this.neighbours = new ArrayList<String>(neighbour);
		stf = new SumoTrafficLight(identifier);
	}
	
	public String getId() {
		return identifier;
	}
	
	public void changeState(String state) {
		stf.setState(state);
	}
	
	public String getStateS(){
		return stf.getState();
	}
	
	public int getState(){
		String s = stf.getState();
		if ( s.equals("r"))
			return Constants.TLPHASE_RED;
		else
			if ( s.equals("g"))
				return Constants.TLPHASE_GREEN;
			else
				if ( s.equals("y"))
					return Constants.TLPHASE_YELLOW;
				else
					return -1;
	}
}
