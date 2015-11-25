package Agents;

import java.util.ArrayList;
import java.util.HashSet;

import Behaviours.TimedBehaviour;
import jade.core.AID;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import trasmapi.sumo.Sumo;
import trasmapi.sumo.SumoTrafficLight;

public class AgentsManager {
	ArrayList<TrafficLightAgent> agents = new ArrayList<TrafficLightAgent>();
	private Sumo sumo;
	ArrayList<TrafficLightAgentInfo> tfai;
	
	public AgentsManager(Sumo sumo, ContainerController mainController, ArrayList<TrafficLightAgentInfo> tfai) {
		//gets all traffic lights in the sumo simulation
		ArrayList<String> trafficLightIds = SumoTrafficLight.getIdList();
		TrafficLightAgent agent;
		
		this.tfai = tfai;
		
		for (String id : trafficLightIds) {
			SumoTrafficLight temp = new SumoTrafficLight(id);
			
			HashSet<String> lanes;
			lanes = new HashSet<String>(temp.getControlledLanes());
			
			ArrayList<String> neighbours = new ArrayList<String>();
			for (String l : lanes) {
				neighbours.add(getLaneSrcFromId(l));
			}
			
			// TODO
			neighbours = organizeNeighbours(id, neighbours);
			
			try {
				agent = new TrafficLightAgent(sumo, id, neighbours);
				agents.add(agent);
				AgentController t = mainController.acceptNewAgent("TrafficLight-" + id, agent);
				mainController.getAgent(agent.getLocalName(),AID.ISLOCALNAME);
				System.out.println(t.toString());
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private ArrayList<String> organizeNeighbours(String id, ArrayList<String> neighbours) {
		return neighbours;
	}

	
	public void setBehaviour() {
		
		ArrayList<Integer> timeStates = new ArrayList<Integer>();
		ArrayList<String> states = new ArrayList<String>();
		TimedBehaviour tb;
		for ( TrafficLightAgent tl : agents){
			for ( TrafficLightAgentInfo info : tfai){
				if ( info.getIdentifier().equals(tl.getId())){
					System.out.println("WHAT IS THE ID " + tl.getId());
					timeStates = info.getTimeStates();
					states = info.getStates();
					tb = new TimedBehaviour(tl,timeStates, states);
					tl.addBehaviour(tb);
				}
				
			}
			
		}
		
	}
	
	public void startupAgents(ContainerController mainController) {
		try {
			for ( TrafficLightAgent ag : agents) {
				
				String temp = ag.getLocalName().split("-")[1];
				System.out.println("HELLO " + temp);
				mainController.getAgent(ag.getLocalName(),AID.ISLOCALNAME).start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	//returns the src of a lane
	//srcToDest
	private static String getLaneSrcFromId(String l) {
		 return l.split("to")[0];
	}
}
