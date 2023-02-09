package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder(){
		_typeTag = "mtfp";
		_desc = "Moving towards a fixed point";
	}
	

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double g=9.81;
		Vector2D c = new Vector2D();
		if(data.has("g")) {
			g = data.getDouble("g");
		}
		if(data.has("c")) {
			JSONArray aux = data.getJSONArray("c");
			c = new Vector2D(aux.getDouble(0), aux.getDouble(1));
		}
		return new MovingTowardsFixedPoint(c, g);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject result = new JSONObject();
		result.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
		result.put("g","the length of the acceleration vector (a number)");
		return result;
	}
}
