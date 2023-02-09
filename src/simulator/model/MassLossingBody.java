package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{
    
	protected double _lossingFrequency;
	protected double _lossingFactor;
	protected double _accumulatedTime;
    
	public MassLossingBody(String id, Vector2D velocidad, Vector2D posicion, double mass, double lossingFrequency, double lossingFactor) {
   	 super (id, velocidad, posicion, mass);
   	 _lossingFrequency = lossingFrequency;
   	 _lossingFactor = lossingFactor;
   	 _accumulatedTime = 0.0;

	}
    
	void move(double t) {
   	 super.move(t);
  	 
   	 _accumulatedTime += t;
   	 if(_accumulatedTime >= _lossingFrequency) {
   		 _accumulatedTime = 0.0;
   		 m = m * (1 - _lossingFactor);
   	 }
	}
    
	public JSONObject getState() {
	   	 JSONObject jo1 = super.getState();
	   	 jo1.put("_lossingFrequency", _lossingFrequency);
	   	 jo1.put("_lossingFactor", _lossingFactor);
	   	 jo1.put("_accumulatedTime", _accumulatedTime);
	   	 return jo1;    
		}
}



