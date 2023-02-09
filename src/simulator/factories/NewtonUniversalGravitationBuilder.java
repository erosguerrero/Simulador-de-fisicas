package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	public NewtonUniversalGravitationBuilder() {
		_typeTag = "nlug";         
		_desc = "Newton's law of universal gravitation";  
	}
	
	protected JSONObject createData() {
		//creamos un nuevo JSON con las variables asociadas a la clase
		JSONObject result = new JSONObject();
		result.put("_G","the gravitational constant (a number)");
		return result;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double _G = 6.67E-11;
		if(data.has("_G")) {
			_G = data.getDouble("_G");
		}
		return new NewtonUniversalGravitation(_G);
	}
}
