package trasmapi.genAPI;

import trasmapi.genAPI.exceptions.UnimplementedMethod;

public class Lane implements Comparable<Lane> {
	protected String id;
	
	public String getId() {
		return id;
	}

	public int compareTo(Lane o) {
		return id.compareTo(o.getId());
	}
	
	public void setMaxSpeed(float val) throws UnimplementedMethod {
		throw new UnimplementedMethod();
	}
	
	public int getNumVehiclesStopped(Double minVel){
		return this.getNumVehiclesStopped(0.2);
	}
	
	public int getNumVehicles() throws UnimplementedMethod {
		throw new UnimplementedMethod();
	}
	
	public void loadLength() throws UnimplementedMethod {
		throw new UnimplementedMethod();
	}
	
	public Vehicle[] vehiclesList() throws UnimplementedMethod {
		throw new UnimplementedMethod();
	}

	public int getNumVehicles(String type) {
		// TODO Auto-generated method stub
		return 0;
	}
}
