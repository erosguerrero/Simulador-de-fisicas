package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
    
    private double dt;
    private ForceLaws leyf; //fuerza a aplicar
    private List<Body> b;
    private List<SimulatorObserver> observers;
    private double at; //time

    public PhysicsSimulator(ForceLaws leyf, double dt) {
    	
   	 if(dt < 0 || leyf == null) throw new IllegalArgumentException();
   	 this.dt = dt;
   	 b = new ArrayList<Body>(); 
   	 this.leyf = leyf;
   	 at = 0.0;
   	 observers = new ArrayList<SimulatorObserver>();
    }
    
    public void addBody(Body newo) { //no se puede aniadir dos cuerpos de misma id
   	 //comprueba que no existe ningun otro cuerpo en el simulador con el mismo identificador
    	
   	if (!b.contains(newo)) { 
   		b.add(newo);
   		for (SimulatorObserver o : observers) {
			o.onBodyAdded(b, newo);
		}
   	}
   	else throw new IllegalArgumentException("Body already exists");
   }
    
    public void advance() {
   	 for(Body o: b){
   		 o.resetForce();
   	 }
   	 leyf.apply(b);
   	 for(Body o: b){
   		 o.move(dt);
   	 }
   	 //incrementar tiempo actual en dt segundos
   	 at += dt;
		for (SimulatorObserver o : observers) {
			o.onAdvance(b, at);
		}
    }
    
    public JSONObject getState() {
   	 JSONObject jo1 = new JSONObject();
   	 JSONArray bodies = new JSONArray();  
   	 jo1.put("time", at); //{ "time": T,
   	 
   	 for(Body o: b){ // "bodies": [json1, json2, . . .] }
   		 bodies.put(o.getState());
   	 }
   	 jo1.put("bodies", bodies);	 
   	 return jo1;
    }

    public String toString() {
   	 
   	 return getState().toString();
    }
    
   	 public void reset() {
   		 b.clear();
   		at = 0.0;
   		for (SimulatorObserver o : observers) {
			o.onReset(b, at, dt, leyf.toString());
		}
   	 }
   	 
   	public void setDeltaTime(double dt) {
   		if (dt < 0) throw new IllegalArgumentException();
   		this.dt = dt;
   		for (SimulatorObserver o : observers) {
			o.onDeltaTimeChanged(dt);
		}
   	}
   	
   	public void setForceLawsLaws(ForceLaws forceLaws) {
   		if (forceLaws == null) throw new IllegalArgumentException();
   		leyf = forceLaws;
   		for (SimulatorObserver o : observers) {
			o.onForceLawsChanged(leyf.toString());
		}
   	}
   	
   	public void addObserver(SimulatorObserver o) {
   		if(observers.contains(o)) {
   			throw new IllegalArgumentException("Observer already exists");
   		}else{
   			observers.add(o);
   			o.onRegister(b, at, dt, leyf.toString());
   		}
   	}
   
}


