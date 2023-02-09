package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

	public EpsilonEqualStatesBuilder() {
		_typeTag = "epseq";
		_desc = "Epsilon-equal states comparator";
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject data) {
		double eps = 0.0;
		if(data.has("eps")) {
			eps = data.getDouble("eps");
		}
		return new EpsilonEqualStates(eps);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject result = new JSONObject();
		result.put("eps","the allowed error");
		return result;
	}

}
