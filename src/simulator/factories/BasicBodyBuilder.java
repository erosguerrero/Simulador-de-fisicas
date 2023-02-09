package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		_typeTag = "basic";
		_desc = "Default Body";
	}
	
	@Override
	protected Body createTheInstance(JSONObject datos) {
		Body body = null;
		//Devuelve un objeto body con la data
		if(datos != null) {
		JSONArray p = datos.getJSONArray("p"); //POS
		JSONArray v = datos.getJSONArray("v"); //SPEED
		String id = datos.getString("id"); //ID
		double m = datos.getDouble("m"); //MASS
		body = new Body(id,new Vector2D(v.getDouble(0),v.getDouble(1)),new Vector2D(p.getDouble(0),p.getDouble(1)), m);
		}
		return body;
	}
	
	@Override
	protected JSONObject createData() { //Las clases lo sobreescriben
		JSONObject result = new JSONObject();
		result.put("id","the identifier");
		result.put("m", "mass");
		result.put("p","pos");
		result.put("v", "speed");
		return result;
	}
	
}
