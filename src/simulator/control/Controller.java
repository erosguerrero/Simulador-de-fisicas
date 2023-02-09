package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory; //Mirar si sobran
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	PhysicsSimulator Simulator;
	Factory<Body> BodyCreator;
	Factory<ForceLaws> LawsCreator;
	public Controller(PhysicsSimulator PS, Factory<Body> BFactory,Factory<ForceLaws> LFactory) {
	Simulator = PS;
	BodyCreator = BFactory;
	LawsCreator = LFactory;
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException{
		JSONObject expOutJO = null;
		
		if(expOut != null) //Si quiere un fichero para comparar salidas
			expOutJO = new JSONObject(new JSONTokener(expOut)); //con herencia no habria que preguntar
		
		if(out == null) { //si no tiene fichero de salida, la salida por consola
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException{}
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		JSONObject currState = null;
		JSONObject expState = null;
		//Cmp estados iniciales
		
		currState = Simulator.getState();
		p.println(currState);
		if(expOutJO != null) {
			expState = expOutJO.getJSONArray("states").getJSONObject(0);
			if(!cmp.equal(expState, currState)) {
				throw new NotEqualStatesException(expState, currState, 0); //discrepacia en el estado 0
			}

		}
		Simulator.advance();
		
		for (int i = 1; i <= n; i++) {
			currState = Simulator.getState();
			p.print(",");
			p.println(currState);

			if(expOutJO != null) {
				expState = expOutJO.getJSONArray("states").getJSONObject(i);
				if(!cmp.equal(expState, currState)) {
					throw new NotEqualStatesException(expState, currState, i); //discrepacia en el estado 0
				}
			}
			Simulator.advance();
			
		}
		
		
		p.println("]");
		p.println("}");
		
		
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bds = jsonInput.getJSONArray("bodies");
		//List Bodies
		for (int i = 0; i < bds.length(); i++) {
			 Simulator.addBody(BodyCreator.createInstance(bds.getJSONObject(i)));
			   
		}

	}
	public void reset() {
		Simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		Simulator.setDeltaTime(dt);
	}
	public void addObserver(SimulatorObserver o) {
		Simulator.addObserver(o);
	}
	public List<JSONObject> getForceLawsInfo(){
		return LawsCreator.getInfo();
	}
	public void setForceLaws(JSONObject info) {
		Simulator.setForceLawsLaws(LawsCreator.createInstance(info));
	}
	
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			Simulator.advance();
		}
	}
}
