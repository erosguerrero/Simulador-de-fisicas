package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder(){
		_typeTag = "mlb";
		_desc = "Mass losing body";
	}
	
	@Override
	protected Body createTheInstance(JSONObject datos) {
		MassLossingBody body = null;
		//Devuelve un objeto body con la data
		if(datos != null) {
		String id = datos.getString("id"); //ID
		double freq = datos.getDouble("freq"); // LOSS FREQUENCY
		double factor = datos.getDouble("factor"); //FACTOR
		JSONArray p = datos.getJSONArray("p");  //POS
		JSONArray v = datos.getJSONArray("v");  //SPEED
		
		double m = datos.getDouble("m");
		body = new MassLossingBody(id,new Vector2D(v.getDouble(0),v.getDouble(1)),new Vector2D(p.getDouble(0),p.getDouble(1)), m,freq,factor);
		}
		return body;
	}
	
	protected JSONObject createData() {
		JSONObject result = new JSONObject();
		result.put("id","the identifier");
		result.put("m", "mass");
		result.put("p","pos");
		result.put("v", "speed");
		result.put("freq", "freq");
		result.put("factor", "factor");
		return result;
	}
	
}
